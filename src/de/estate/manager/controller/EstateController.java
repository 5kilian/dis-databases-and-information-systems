package de.estate.manager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class EstateController {

    @FXML
    public DialogPane addDialog;
    @FXML
    public ButtonType addButton;
    @FXML
    public TextField streetField;
    @FXML
    public TextField numberField;
    @FXML
    public TextField cityField;
    @FXML
    public TextField areaField;
    @FXML
    public TextField zipField;
    @FXML
    public TextField floorsField;
    @FXML
    public TextField priceField;
    @FXML
    public TextField gardenField;


    public void validateData(KeyEvent keyEvent) {

    }
}
