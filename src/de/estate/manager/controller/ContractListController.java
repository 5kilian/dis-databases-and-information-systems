package de.estate.manager.controller;



import de.estate.manager.model.*;
import de.estate.manager.service.ContractService;
import de.estate.manager.service.EstateService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
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
import java.sql.Date;
import java.util.Optional;

import static javafx.collections.FXCollections.*;

public class ContractListController {
    private ContractService contractService;
    private EstateService estateService;

    @FXML
    public TableColumn typeColumn;
    @FXML
    public TableColumn editColumn;
    @FXML
    public TableView tableView;

    public void ContractListController() {
        contractService = new ContractService();
        estateService = new EstateService();
    }



    @FXML
    private void initialize() {
        typeColumn.setCellFactory(tableColumn -> new TableCell<String, Contract>() {
            @Override
            protected void updateItem(Contract contract, boolean empty) {
                super.updateItem(contract, empty);
                if (contract != null) {
                    ImageView type;
                    if (contract instanceof Tenancy) {
                        type = new ImageView(new Image(getClass().getResource("../../resources/images/16/building-o.png").toString()));
                    } else if (contract instanceof Purchase) {
                        type = new ImageView(new Image(getClass().getResource("../../resources/images/16/home.png").toString()));
                    } else {
                        type = new ImageView(new Image(getClass().getResource("../../resources/images/16/question.png").toString()));
                    }
                    setGraphic(type);
                } else {
                    setGraphic(null);
                }
            }
        });

        typeColumn.setCellValueFactory(cellData -> {
            TableColumn.CellDataFeatures data = (TableColumn.CellDataFeatures) cellData;
            Contract item = (Contract) data.getValue();
            return new ReadOnlyObjectWrapper<>(item);
        });
        editColumn.setCellFactory(tableColumn -> new TableCell<String, Contract>() {
            @Override
            protected void updateItem(Contract contract, boolean empty) {
                super.updateItem(contract, empty);

                if (contract != null) {
                    HBox hBox = new HBox();
                    ImageView editView = new ImageView(new Image(getClass().getResource("../../resources/images/16/pencil.png").toString()));
                    ImageView deleteView = new ImageView(new Image(getClass().getResource("../../resources/images/16/trash-o.png").toString()));
                    Pane separator = new Pane();
                    separator.setMinWidth(8);
                    hBox.getChildren().addAll(editView, separator, deleteView);

                    editView.setOnMouseClicked(event -> {
                        try {
                            Dialog<Contract> dialog = new Dialog<>();
                            dialog.setDialogPane(FXMLLoader.load(getClass().getResource("../../resources/view/contract-dialog.fxml")));
                            dialog.setTitle("Edit Contract");

                            DialogPane pane = dialog.getDialogPane();

                            TextField dateField = (TextField) pane.lookup("#dateField");
                            TextField placeField = (TextField) pane.lookup("#placeField");

                            TextField firstNameField = (TextField) pane.lookup("#firstNameField");
                            TextField nameField = (TextField) pane.lookup("#nameField");
                            TextField addressField = (TextField) pane.lookup("#addressField");



                            dateField.setText(contract.getDate());
                            placeField.setText(String.valueOf(contract.getPlace()));





                            ChoiceBox choiceBox =  (ChoiceBox) pane.lookup("#choiceBox");


                            TabPane typePane = (TabPane) pane.lookup("#typePane");

                            if (contract instanceof Purchase) {
                                TextField noInstallmentField = (TextField) pane.lookup("#noInstallmentField");
                                TextField instrestRateField = (TextField) pane.lookup("#instrestRateField");

                                Purchase purchase = (Purchase) contract;
                                noInstallmentField.setText(String.valueOf(purchase.getInstallments()));
                                instrestRateField.setText(String.valueOf(purchase.getRate()));

                                typePane.getSelectionModel().select(1);

                                ObservableList<Estate> houseList = FXCollections.observableArrayList(estateService.getHouses());
                                choiceBox.setItems(houseList);

                            } else if (contract instanceof Tenancy) {
                                TextField startDateField = (TextField) pane.lookup("#startDateField");
                                TextField durationField = (TextField) pane.lookup("#durationField");
                                TextField additionalCostField = (TextField) pane.lookup("#additionalCostField");

                                Tenancy tenancy = (Tenancy) contract;
                                startDateField.setText(String.valueOf(tenancy.getStart()));
                                durationField.setText(String.valueOf(tenancy.getDuration()));
                                additionalCostField.setText(String.valueOf(tenancy.getCost()));
                                typePane.getSelectionModel().select(0);

                                ObservableList<Estate> apartmentList = FXCollections.observableArrayList(estateService.getApartments());
                                choiceBox.setItems(apartmentList);
                            }

                            Optional<Contract> result = dialog.showAndWait();
                            result.ifPresent(res -> {
                                tableView.refresh();
                                if (contract instanceof Purchase) {
                                    Purchase purchase = (Purchase) contract;
                                    purchase.save();
                                } else if (contract instanceof Tenancy) {
                                    Tenancy tenancy = (Tenancy) contract;
                                    tenancy.save();
                                } else {
                                    contract.save();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    deleteView.setOnMouseClicked(event -> {
                        tableView.getItems().remove(contract);
                        contractService.delete(contract);
                    });

                    setGraphic(hBox);
                } else {
                    setGraphic(null);
                }
            }
        });
        editColumn.setCellValueFactory(cellData -> {
            TableColumn.CellDataFeatures data = (TableColumn.CellDataFeatures) cellData;
            Contract item = (Contract) data.getValue();
            return new ReadOnlyObjectWrapper<>(item);
        });

        try {
            tableView.getItems().addAll(contractService.getAll());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection failed");
            alert.setHeaderText("Connection failed");
            alert.setContentText("Ooops, there was an error!\n" + e.toString());

            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void addContract(MouseEvent mouseEvent) {
        try {
            Dialog<Contract> dialog = new Dialog<>();
            dialog.setDialogPane(FXMLLoader.load(getClass().getResource("../../resources/view/contract-dialog.fxml")));
            dialog.setTitle("Add new contract");
            dialog.getDialogPane().setHeaderText("Add new contract");
            dialog.getDialogPane().lookupButton(
                    dialog.getDialogPane().getButtonTypes().filtered(buttonType -> buttonType.getText().equals("Save")).get(0)
            ).setDisable(true);
            dialog.setResultConverter(buttonType -> {
                DialogPane pane = dialog.getDialogPane();

                TextField dateField = (TextField) pane.lookup("#dateField");
                TextField placeField = (TextField) pane.lookup("#placeField");

                Contract contract = new Contract();
                contract.setDate(dateField.getText());
                contract.setPlace(placeField.getText());


                ChoiceBox choiceBox =  (ChoiceBox) pane.lookup("#choiceBox");

                TabPane typePane = (TabPane) pane.lookup("#typePane");

                Tab selectedItem = typePane.getSelectionModel().getSelectedItem();
                switch (selectedItem.getText()) {
                    case "Purchase":
                        TextField noInstallmentField = (TextField) pane.lookup("#noInstallmentField");
                        TextField instrestRateField = (TextField) pane.lookup("#instrestRateField");

                        Purchase purchase = new Purchase(contract);
                        purchase.setInstallments(Integer.valueOf(noInstallmentField.getText()));
                        purchase.setRate(Integer.valueOf(instrestRateField.getText()));

                        ObservableList<Estate> houseList = FXCollections.observableArrayList(estateService.getHouses());
                        choiceBox.setItems(houseList);

                        return purchase;
                    case "Tenancy":
                        TextField startDateField = (TextField) pane.lookup("#startDateField");
                        TextField durationField = (TextField) pane.lookup("#durationField");
                        TextField additionalCostField = (TextField) pane.lookup("#additionalCostField");

                        Tenancy tenancy = new Tenancy(contract);
                        tenancy.setStart(startDateField.getText());
                        tenancy.setDuration(Integer.valueOf(durationField.getText()));
                        tenancy.setCost(Double.valueOf(additionalCostField.getText()));

                        ObservableList<Estate> apartmentList = FXCollections.observableArrayList(estateService.getApartments());
                        choiceBox.setItems(apartmentList);

                        return tenancy;
                    default:
                        return contract;
                }
            });
            Optional<Contract> result = dialog.showAndWait();
            result.ifPresent(contract -> {
                tableView.getItems().add(contract);
                if (contract instanceof Purchase) {
                    Purchase purchase = (Purchase) contract;
                    purchase.save();
                } else if (contract instanceof Tenancy) {
                    Tenancy tenancy = (Tenancy) contract;
                    tenancy.save();
                } else {
                    contract.save();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void home(MouseEvent mouseEvent) {
        try {
            Stage stage = (Stage) tableView.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/view/home.fxml"));
            stage.setScene(new Scene(root, 800, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

