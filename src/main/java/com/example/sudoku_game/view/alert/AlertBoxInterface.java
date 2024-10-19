package com.example.sudoku_game.view.alert;

import javafx.scene.control.Alert;

/**
 * Interface for creating alert boxes with customizable messages, headers, and titles.
 * Implementations of this interface should handle displaying alerts to the user in the UI.
 */
public interface AlertBoxInterface {

    /**
     * Displays an alert with the specified title, header, message, and alert type.
     *
     * @param title The title of the alert window.
     * @param header The header text for the alert.
     * @param message The detailed message to be displayed in the alert.
     * @param alertType The type of alert (e.g., INFORMATION, ERROR, etc.).
     */
    public void showAlert(String title, String header, String message, Alert.AlertType alertType);
}

