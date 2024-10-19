package com.example.sudoku_game.controller;

import com.example.sudoku_game.model.Sudoku;
import com.example.sudoku_game.view.alert.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;


/**
 * @author Laura Celeste Berrio Parra - 2322101
 * The SudokuController class manages the user interaction,
 * the game logic, and updates the view for the Sudoku game.
 */
public class SudokuController {
    @FXML
    public Button instructionsButton; //Button that shows game instructions
    @FXML
    public Pane infoColors; //Pane of labels with colors information

    @FXML
    private GridPane sudokuGrid;  // The grid that displays the Sudoku board

    @FXML
    private Button helpButton;  // Button to provide hints to the user

    private Sudoku sudoku;  // The model representing the Sudoku board

    private ArrayList<ArrayList<TextField>> cells;  // List of TextFields representing the Sudoku cells

    private int sudokuSize;  // The size of the Sudoku board (6x6)

    private boolean firstGame = true;  // Flag to track if it's the first game session

    private final Random random = new Random();  // Random number generator for hints

    /**
     * Event handler for the "Play" button. Starts a new game and initializes the board.
     * @param event The event triggered by clicking the "Play" button.
     * @throws IOException If an input/output error occurs during game initialization.
     */
    @FXML
    void onHandlePlayButton(ActionEvent event) throws IOException {
        // Start a new game if it's the first time or if confirmed by the user
        if(firstGame || confirmNewGame()) {
            firstGame = false;
            sudoku = new Sudoku();  // Create a new Sudoku instance
            sudokuSize = sudoku.getSudokuSize();  // Get the size of the Sudoku grid

            cells = new ArrayList<>();  // Initialize the cells list
            // Populate the list with empty TextFields
            for (int i = 0; i < sudokuSize; i++) {
                cells.add(new ArrayList<>(Collections.nCopies(sudokuSize, new TextField())));
            }

            // Set up the TextFields in the grid and clear previous entries
            for(int row = 0; row < sudokuSize; row++) {
                for(int col = 0 ; col < sudokuSize; col++) {
                    cells.get(row).set(col, (TextField) getElementByRowColumn(row, col, sudokuGrid));
                    cells.get(row).get(col).clear();
                    cells.get(row).get(col).setEditable(true);

                    cells.get(row).get(col).setStyle(cells.get(row).get(col).getStyle() + "-fx-background-color: white;");
                    addTextFieldListener(cells.get(row).get(col), row, col);  // Add listener for cell input validation
                }
            }

            initializeBoard();  // Initialize the board by filling two cells per block
            helpButton.setDisable(false);// Enable the help button
            infoColors.setVisible(true); // Visible Pane of Labels
        }
    }

    /**
     * Event handler for the "Help" button. Fills one empty cell with the correct number.
     * @param event The event triggered by clicking the "Help" button.
     * @throws IOException If an input/output error occurs.
     */
    @FXML
    void onHandleHelpButton(ActionEvent event) throws IOException {
        // Generate a hint by filling an empty cell with the correct value
        int helps = sudoku.getHelps(); // Retrieve the current number of hints used
        if (helps != 5) { // Check if the maximum number of hints (5) has not been reached
            int row, col;
            do {
                row = random.nextInt(6);
                col = random.nextInt(6);
            } while(sudoku.getCellValue(row, col) != 0);

            int numero = sudoku.getSudokuSolved().get(row).get(col);  // Get the correct value from the solved board
            sudoku.setCellValue(row, col, numero);  // Update the board with the correct value
            TextField cell = cells.get(row).get(col);
            cell.setText(String.valueOf(numero));  // Display the correct value in the grid
            cell.setStyle(cell.getStyle()+"-fx-background-color: #C3F6C7;");  // Highlight the cell
            helps++; // Increment the count of hints used
            sudoku.setHelps(helps);
            // Update the Sudoku model with the new number of hints used
        } else {
            // Show an error alert if the player has used all available hints
            new AlertBox().showAlert("Error", "¡Sin ayudas!", "Lo siento, se te acabaron las ayudas :(", AlertType.ERROR);
            helpButton.setDisable(true);
        }
    }

    /**
     * Retrieves the TextField located at the specified row and column in the GridPane.
     * @param row The row index.
     * @param col The column index.
     * @param gridPane The GridPane representing the Sudoku grid.
     * @return The TextField located at the specified row and column.
     */
    private TextField getElementByRowColumn(int row, int col, GridPane gridPane) {
        for (var element : gridPane.getChildren()) {
            if (GridPane.getRowIndex(element) == row && GridPane.getColumnIndex(element) == col) {
                return (TextField) element;
            }
        }
        return null;
    }

    /**
     * Initializes the Sudoku board by filling two cells in each 2x3 block with numbers.
     * Calls the method to update the grid display.
     */
    private void initializeBoard() {
        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 2; blockCol++) {
                sudoku.fillTwoCellsInBlock(blockRow, blockCol);  // Fill two random cells in each block
            }
        }
        updateSudokuGrid();  // Update the displayed Sudoku grid with initial values
    }

    /**
     * Adds a listener to each TextField to validate user input.
     * Ensures that the entered number is valid for Sudoku and updates the board accordingly.
     * @param cell The TextField to which the listener is added.
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     */
    private void addTextFieldListener(TextField cell, int row, int col) {
        cell.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^([1-6]|)$")) {  // Validate input (only digits 1-6 allowed)
                cell.clear();
                new AlertBox().showAlert("Validaciones", "¡Error!", "Debes ingresar un numero valido [1-6]", AlertType.ERROR);
            } else if (!newValue.isEmpty()) {
                int value = Integer.parseInt(newValue);
                // Check if the entered value is valid for the current Sudoku state
                if (sudoku.checkValidValue(row, col, value, sudoku.getSudoku())) {
                    sudoku.setCellValue(row, col, value);  // Set the value in the model
                    cell.setStyle(cell.getStyle()+"-fx-background-color: white;");
                     // Reset the cell style
                    if(sudoku.isSudokuSolved()) {
                        new AlertBox().showAlert("Ganaste", "¡Felicidades!", "Has resuelto el Sudoku correctamente :)", AlertType.INFORMATION);
                        cells.forEach(textFieldRow -> textFieldRow.forEach(textField -> textField.setEditable(false)));
                    }
                } else {
                    cell.setStyle(cell.getStyle()+"-fx-background-color: #ffe7f5;");  // Highlight invalid input
                }
            } else {
                sudoku.setCellValue(row, col, 0);
            }
        });
    }

    /**
     * Updates the Sudoku grid based on the current state of the model.
     * Fills cells with values and marks pre-filled cells as non-editable.
     */
    private void updateSudokuGrid() {
        for (int row = 0; row < sudokuSize; row++) {
            for (int col = 0; col < sudokuSize; col++) {
                int cellValue = sudoku.getCellValue(row, col);

                if (cellValue != 0) {
                    TextField cell = cells.get(row).get(col);
                    cell.setText(String.valueOf(cellValue));  // Set the cell value in the grid
                    cell.setStyle(cell.getStyle()+"-fx-background-color: #B3D3C2;");  // Highlight pre-filled cells
                    cell.setEditable(false);  // Disable editing for pre-filled cells
                }
            }
        }
    }

    /**
     * Displays a confirmation dialog to confirm if the user wants to start a new game.
     * @return true if the user confirms, false otherwise.
     */
    public boolean confirmNewGame() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("NUEVO JUEGO");
        alert.setHeaderText("¿Estás seguro?");
        alert.setContentText("¿Deseas iniciar un nuevo juego?");

        ButtonType confirmButtonType = new ButtonType("Confirmar");
        ButtonType cancelButtonType = new ButtonType("Cancelar");
        alert.getButtonTypes().setAll(confirmButtonType, cancelButtonType);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == confirmButtonType;
    }
    /**
     * Displays an informational alert box with the rules and instructions of the 6x6 Sudoku game.
     *
     * @param actionEvent The event triggered when the instructions button is pressed.
     */
    public void onHandleInstructionsButton(ActionEvent actionEvent) {
        new AlertBox().showAlert("INFORMACIÓN", "Instrucciones del juego", "El objetivo del Sudoku 6x6 es llenar todas las celdas de la cuadrícula sólo con números del 1 al 6, siguiendo estas reglas:\n" +
                "\n" +
                "- Cada fila y cada columna debe contener los números del 1 al 6 sin repetir.\n\n" +
                "- Cada bloque de 2 filas por 3 columnas debe contener los números del 1 al 6 sin repetir.\n\n" +
                "Al iniciar el juego, algunas celdas ya estarán llenas. Usa estas pistas para deducir los números que faltan, asegurándote de cumplir con las reglas mencionadas.\n"+
                "\nNota: Tendrás un botón de ayuda, el cuál te mostrará una sugerencia en alguna de las celdas... Mucha suerte! :)", AlertType.INFORMATION);
    }
}
