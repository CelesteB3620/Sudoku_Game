package com.example.sudoku_game.view;

import com.example.sudoku_game.controller.SudokuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Laura Celeste Berrio Parra - 2322101
 * The SudokuView class is responsible for setting up and displaying the Sudoku game's window.
 * It extends the JavaFX Stage class and loads the FXML view associated with the game.
 */
public class SudokuView extends Stage {

    private final SudokuController sudokuController;  // The controller managing the game logic and user interactions

    /**
     * Constructor for SudokuView.
     * Loads the FXML file, sets the title of the window, and shows the stage.
     * @throws IOException If there is an issue loading the FXML resource.
     */
    public SudokuView() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/sudoku_game/sudoku_view.fxml")
        );
        Parent root = loader.load();  // Load the FXML file and initialize the root node
        this.sudokuController = loader.getController();  // Get the controller associated with the FXML view
        this.setTitle("GAME - SUDOKU");  // Set the title of the window
        Scene scene = new Scene(root);  // Create a new scene with the loaded root node
        this.setScene(scene);  // Set the scene for this stage
        this.show();  // Display the stage
    }

    /**
     * Getter method to retrieve the SudokuController instance.
     * @return The instance of SudokuController that manages the game.
     */
    public SudokuController getSudokuController() {
        return this.sudokuController;
    }

    /**
     * Static method to obtain the singleton instance of SudokuView.
     * Ensures that only one instance of the view is created.
     * @return The single instance of SudokuView.
     * @throws IOException If there is an issue creating the view.
     */
    public static SudokuView getInstance() throws IOException {
        return SudokuViewHolder.INSTANCE = new SudokuView();
    }

    /**
     * Static inner class that holds the singleton instance of SudokuView.
     */
    private static class SudokuViewHolder {
        private static SudokuView INSTANCE;  // The single instance of SudokuView
    }
}
