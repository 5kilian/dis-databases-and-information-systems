package de.estate.manager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class ContractController {

    @FXML
    public DialogPane addDialog;
    @FXML
    public ButtonType addButton;
    @FXML
    public TextField dateField;
    @FXML
    public TextField placeField;
    @FXML
    public TextField noInstallmentField;
    @FXML
    public TextField instrestRateField;
    @FXML
    public TextField startDateField;
    @FXML
    public TextField durationField;
    @FXML
    public TextField additionalCostField;
    @FXML
    public TextField firstNameField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField addressField;
    @FXML
    public ChoiceBox choiceBox;


    public void validateData(KeyEvent keyEvent) {
        addDialog.lookupButton(addButton).setDisable(false);
    }
}

