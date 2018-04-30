package de.estate.manager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    public Button agentButton;
    @FXML
    public Button estateButton;
    @FXML
    public Button contractButton;

    public void openAgentManager(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) agentButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/view/agent-list.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void openEstateManager(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) agentButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/view/estate-list.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openContractManager(ActionEvent actionEvent) {

    }
}
