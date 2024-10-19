package com.example.sudoku_game.view.alert;

import javafx.scene.control.Alert;
/**
 * Implementation of the AlertBoxInterface that handles the creation and display of alert boxes in a JavaFX application.
 * This class creates a customizable alert with a title, header, message, and specific alert type.
 */
public class AlertBox implements AlertBoxInterface {

    /**
     * Displays an alert box with the specified parameters.
     *
     * @param title The title of the alert window.
     * @param header The header text for the alert box.
     * @param message The main content message displayed in the alert.
     * @param alertType The type of alert to be displayed (e.g., INFORMATION, ERROR).
     */
    @Override
    public void showAlert(String title, String header, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType); // Creates an alert of the specified type
        alert.setTitle(title);              // Sets the title of the alert
        alert.setHeaderText(header);        // Sets the header text of the alert
        alert.setContentText(message);      // Sets the content message of the alert
        alert.showAndWait();                // Displays the alert and waits for the user to close it
    }
}
