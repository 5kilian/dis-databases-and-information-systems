package de.estate.manager.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    public VBox agentButton;
    @FXML
    public VBox estateButton;
    @FXML
    public VBox contractButton;

    public void openAgentManager(MouseEvent actionEvent) {
        try {
            Stage stage = (Stage) agentButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/view/agent-list.fxml"));
            stage.setScene(new Scene(root, 800, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void openEstateManager(MouseEvent actionEvent) {
        try {
            Stage stage = (Stage) agentButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/view/estate-list.fxml"));
            stage.setScene(new Scene(root, 800, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openContractManager(MouseEvent actionEvent) {
        try {
            Stage stage = (Stage) contractButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/view/contract-list.fxml"));
            stage.setScene(new Scene(root, 800, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void open(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/view/home.fxml"));
            stage.setScene(new Scene(root, 800, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
