package de.estate.manager.controller;

import de.estate.manager.model.Agent;
import de.estate.manager.model.Apartment;
import de.estate.manager.model.Estate;
import de.estate.manager.model.House;
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
    public TableColumn typeColumn;
    @FXML
    public TableColumn editColumn;
    @FXML
    public TableView tableView;

    public EstateListController() {
        estateService = new EstateService();
    }

    @FXML
    private void initialize() {
        typeColumn.setCellFactory(tableColumn -> new TableCell<String, Estate>() {
            @Override
            protected void updateItem(Estate estate, boolean empty) {
                super.updateItem(estate, empty);
                if (estate != null) {
                    ImageView type;
                    if (estate instanceof Apartment) {
                        type = new ImageView(new Image(getClass().getResource("../../resources/images/16/building-o.png").toString()));
                    } else if (estate instanceof House) {
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
            Estate item = (Estate) data.getValue();
            return new ReadOnlyObjectWrapper<>(item);
        });
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

                            DialogPane pane = dialog.getDialogPane();
                            TextField streetField = (TextField) pane.lookup("#streetField");
                            TextField numberField = (TextField) pane.lookup("#numberField");
                            TextField cityField = (TextField) pane.lookup("#cityField");
                            TextField areaField = (TextField) pane.lookup("#areaField");
                            TextField zipField = (TextField) pane.lookup("#zipField");

                            streetField.setText(estate.getStreet());
                            numberField.setText(String.valueOf(estate.getNumber()));
                            cityField.setText(estate.getCity());
                            areaField.setText(String.valueOf(estate.getArea()));
                            zipField.setText(String.valueOf(estate.getZip()));

                            TabPane typePane = (TabPane) pane.lookup("#typePane");

                            if (estate instanceof Apartment) {
                                TextField floorField = (TextField) pane.lookup("#floorField");
                                TextField rentField = (TextField) pane.lookup("#rentField");
                                TextField roomsField = (TextField) pane.lookup("#roomsField");
                                CheckBox kitchenField = (CheckBox) pane.lookup("#kitchenField");

                                Apartment apartment = (Apartment) estate;
                                floorField.setText(String.valueOf(apartment.getFloor()));
                                rentField.setText(String.valueOf(apartment.getRent()));
                                roomsField.setText(String.valueOf(apartment.getFloor()));
                                kitchenField.setSelected(apartment.isKitchen());

                                typePane.getSelectionModel().select(1);
                            } else if (estate instanceof House) {
                                TextField floorsField = (TextField) pane.lookup("#floorsField");
                                TextField priceField = (TextField) pane.lookup("#priceField");
                                CheckBox gardenField = (CheckBox) pane.lookup("#gardenField");

                                House house = (House) estate;
                                floorsField.setText(String.valueOf(house.getFloors()));
                                priceField.setText(String.valueOf(house.getPrice()));
                                gardenField.setSelected(house.isGarden());
                                typePane.getSelectionModel().select(0);
                            }

                            Optional<Estate> result = dialog.showAndWait();
                            result.ifPresent(res -> {
                                tableView.refresh();
                                if (estate instanceof House) {
                                    House house = (House) estate;
                                    house.save();
                                } else if (estate instanceof Apartment) {
                                    Apartment apartment = (Apartment) estate;
                                    apartment.save();
                                } else {
                                    estate.save();
                                }
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

                TextField streetField = (TextField) pane.lookup("#streetField");
                TextField numberField = (TextField) pane.lookup("#numberField");
                TextField cityField = (TextField) pane.lookup("#cityField");
                TextField areaField = (TextField) pane.lookup("#areaField");
                TextField zipField = (TextField) pane.lookup("#zipField");

                Estate estate = new Estate();
                estate.setStreet(streetField.getText());
                estate.setNumber(Integer.valueOf(numberField.getText()));
                estate.setCity(cityField.getText());
                estate.setArea(Integer.valueOf(areaField.getText()));
                estate.setZip(Integer.valueOf(zipField.getText()));
                estate.setAgent(Agent.load(1));

                TabPane typePane = (TabPane) pane.lookup("#typePane");

                Tab selectedItem = typePane.getSelectionModel().getSelectedItem();
                switch (selectedItem.getText()) {
                    case "Apartment":
                        TextField floorField = (TextField) pane.lookup("#floorField");
                        TextField rentField = (TextField) pane.lookup("#rentField");
                        TextField roomsField = (TextField) pane.lookup("#roomsField");
                        CheckBox kitchenField = (CheckBox) pane.lookup("#kitchenField");

                        Apartment apartment = new Apartment(estate);
                        apartment.setFloor(Integer.valueOf(floorField.getText()));
                        apartment.setRent(Double.valueOf(rentField.getText()));
                        apartment.setRooms(Double.valueOf(roomsField.getText()));
                        apartment.setKitchen(kitchenField.isSelected());

                        return apartment;
                    case "House":
                        TextField floorsField = (TextField) pane.lookup("#floorsField");
                        TextField priceField = (TextField) pane.lookup("#priceField");
                        CheckBox gardenField = (CheckBox) pane.lookup("#gardenField");

                        House house = new House(estate);
                        house.setFloors(Integer.valueOf(floorsField.getText()));
                        house.setPrice(Integer.valueOf(priceField.getText()));
                        house.setGarden(gardenField.isSelected());
                        return house;
                    default:
                        return estate;
                }
            });
            Optional<Estate> result = dialog.showAndWait();
            result.ifPresent(estate -> {
                tableView.getItems().add(estate);
                if (estate instanceof House) {
                    House house = (House) estate;
                    house.save();
                } else if (estate instanceof Apartment) {
                    Apartment apartment = (Apartment) estate;
                    apartment.save();
                } else {
                    estate.save();
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
