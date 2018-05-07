package de.estate.manager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
            Dialog dialog = new Dialog<>();
            dialog.setDialogPane(FXMLLoader.load(getClass().getResource("../../resources/view/secret-dialog.fxml")));
            dialog.setTitle("Secret");
            Button button = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
            button.addEventFilter(ActionEvent.ACTION, event -> {
                DialogPane pane = dialog.getDialogPane();

                TextField loginField = (TextField) pane.lookup("#secretField");

                if (!loginField.getText().equals("123456")) {
                    Label errorLabel = (Label) pane.lookup("#errorLabel");
                    errorLabel.setText("Invalid secret");
                    event.consume();
                }
            });
            dialog.setResultConverter(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    try {
                        Stage stage = (Stage) agentButton.getScene().getWindow();
                        Parent root = FXMLLoader.load(getClass().getResource("../../resources/view/agent-list.fxml"));
                        stage.setScene(new Scene(root, 800, 600));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            });

            dialog.show();
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
