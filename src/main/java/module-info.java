module com.example.sudoku_game {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sudoku_game to javafx.fxml;
    exports com.example.sudoku_game;
    exports com.example.sudoku_game.controller;
    opens com.example.sudoku_game.controller to javafx.fxml;
}