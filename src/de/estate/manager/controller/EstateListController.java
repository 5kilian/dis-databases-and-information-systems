package de.estate.manager.controller;

import de.estate.manager.model.Agent;
import de.estate.manager.model.Estate;
import de.estate.manager.service.EstateService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class EstateListController {

    private EstateService estateService;

    @FXML
    public TableColumn editColumn;
    @FXML
    public TableView tableView;

    public EstateListController() {
        estateService = new EstateService();
    }

    @FXML
    private void initialize() {
        editColumn.setCellFactory(tableColumn -> new TableCell<String, Estate>() {
            @Override
            protected void updateItem(Estate estate, boolean empty) {
                super.updateItem(estate, empty);

                if (estate != null) {
                    HBox hBox = new HBox();
                    ImageView editView = new ImageView(new Image(getClass().getResource("../../resources/images/16/pencil.png").toString()));
                    ImageView deleteView = new ImageView(new Image(getClass().getResource("../../resources/images/16/trash-o.png").toString()));
                    Pane separator = new Pane();
                    separator.setMinWidth(8);
                    hBox.getChildren().addAll(editView, separator, deleteView);

                    editView.setOnMouseClicked(event -> {
                        try {
                            Dialog<Estate> dialog = new Dialog<>();
                            dialog.setDialogPane(FXMLLoader.load(getClass().getResource("../../resources/view/estate-dialog.fxml")));
                            dialog.setTitle("Edit estate");

                            /*
                            DialogPane pane = dialog.getDialogPane();
                            TextField nameField = (TextField) pane.lookup("#nameField");
                            TextField loginField = (TextField) pane.lookup("#loginField");
                            TextField passwordField = (TextField) pane.lookup("#passwordField");
                            TextField addressField = (TextField) pane.lookup("#addressField");

                            nameField.setText(estate.getName());
                            loginField.setText(estate.getLogin());
                            passwordField.setText(estate.getPassword());
                            addressField.setText(estate.getAddress());

                            dialog.setResultConverter(buttonType -> {
                                estate.setName(nameField.getText());
                                estate.setLogin(loginField.getText());
                                estate.setPassword(passwordField.getText());
                                estate.setAddress(addressField.getText());
                                return estate;
                            });
                            */
                            Optional<Estate> result = dialog.showAndWait();
                            result.ifPresent(res -> {
                                tableView.refresh();
                                estate.save();
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    deleteView.setOnMouseClicked(event -> {
                        tableView.getItems().remove(estate);
                        estateService.delete(estate);
                    });

                    setGraphic(hBox);
                } else {
                    setGraphic(null);
                }
            }
        });
        editColumn.setCellValueFactory(cellData -> {
            TableColumn.CellDataFeatures data = (TableColumn.CellDataFeatures) cellData;
            Estate item = (Estate) data.getValue();
            return new ReadOnlyObjectWrapper<>(item);
        });

        try {
            tableView.getItems().addAll(estateService.getAll());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection failed");
            alert.setHeaderText("Connection failed");
            alert.setContentText("Ooops, there was an error!\n" + e.toString());

            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void addEstate(MouseEvent mouseEvent) {
        try {
            Dialog<Estate> dialog = new Dialog<>();
            dialog.setDialogPane(FXMLLoader.load(getClass().getResource("../../resources/view/estate-dialog.fxml")));
            dialog.setTitle("Add new estate");
            dialog.getDialogPane().setHeaderText("Add new estate");
            dialog.getDialogPane().lookupButton(
                    dialog.getDialogPane().getButtonTypes().filtered(buttonType -> buttonType.getText().equals("Save")).get(0)
            ).setDisable(true);
            dialog.setResultConverter(buttonType -> {
                DialogPane pane = dialog.getDialogPane();
                Estate estate = new Estate();
                estate.setStreet("");
                estate.setNumber(0);
                estate.setCity("");
                estate.setArea(0);
                estate.setZip(0);
                estate.setAgent(Agent.load(1));
                return estate;
                /*
                TextField nameField = (TextField) pane.lookup("#nameField");
                TextField loginField = (TextField) pane.lookup("#loginField");
                TextField passwordField = (TextField) pane.lookup("#passwordField");
                TextField addressField = (TextField) pane.lookup("#addressField");

                Estate estate = new Estate();
                estate.setName(nameField.getText());
                estate.setLogin(loginField.getText());
                estate.setPassword(passwordField.getText());
                estate.setAddress(addressField.getText());
                return estate;
                */
            });
            Optional<Estate> result = dialog.showAndWait();
            result.ifPresent(estate -> {
                tableView.getItems().add(estate);
                estate.save();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void home(MouseEvent mouseEvent) {
        try {
            Stage stage = (Stage) tableView.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/view/home.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
