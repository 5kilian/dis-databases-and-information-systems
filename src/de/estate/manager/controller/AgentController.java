package de.estate.manager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class AgentController {

    @FXML
    private DialogPane addDialog;
    @FXML
    private TextField nameField;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField addressField;
    @FXML
    private ButtonType addButton;

    public void validateData(KeyEvent inputMethodEvent) {
        addDialog.lookupButton(addButton).setDisable(isValid());
    }

    public boolean isValid() {
        return nameField.getText().trim().isEmpty()
                || loginField.getText().trim().isEmpty()
                || passwordField.getText().trim().isEmpty()
                || addressField.getText().trim().isEmpty();
    }

}
