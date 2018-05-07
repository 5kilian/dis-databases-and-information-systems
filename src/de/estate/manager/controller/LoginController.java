package de.estate.manager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class LoginController {

    @FXML
    public DialogPane loginDialog;
    @FXML
    public ButtonType signInButton;
    @FXML
    public TextField loginField;
    @FXML
    public TextField passwordField;
    @FXML
    public Label errorLabel;


    public void validateData(KeyEvent keyEvent) {

    }
}
