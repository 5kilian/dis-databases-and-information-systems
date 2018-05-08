package de.estate.manager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

public class ContractController {

    @FXML
    public DialogPane addDialog;
    @FXML
    public ButtonType addButton;
    @FXML
    public DatePicker dateField;
    @FXML
    public TextField placeField;
    @FXML
    public TextField noInstallmentField;
    @FXML
    public TextField instrestRateField;
    @FXML
    public DatePicker startDateField;
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

