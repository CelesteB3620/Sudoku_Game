package com.example.sudoku_game;

import com.example.sudoku_game.view.SudokuView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class of the Sudoku game application.
 * This class extends the JavaFX Application class to launch the application.
 */
public class Main extends Application {

    /**
     * The main entry point of the application.
     *
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args); // Launches the JavaFX application
    }

    /**
     * The start method is called after the application is launched.
     * It initializes the main stage of the application.
     *
     * @param primaryStage The primary stage for this application, onto which the application scene can be set.
     * @throws IOException If an error occurs while loading the Sudoku view.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        SudokuView.getInstance(); // Initializes the Sudoku view
    }
}
