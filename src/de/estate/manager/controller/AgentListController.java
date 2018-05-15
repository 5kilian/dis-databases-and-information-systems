package de.estate.manager.controller;

import de.estate.manager.model.Agent;
import de.estate.manager.service.AgentService;
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

public class AgentListController {

    private AgentService agentService;
    @FXML
    private TableView<Agent> tableView;
    @FXML
    public TableColumn editColumn;

    public AgentListController() {
        agentService = new AgentService();
    }

    @FXML
    private void initialize() {
        editColumn.setCellFactory(tableColumn -> new TableCell<String, Agent>() {
            @Override
            protected void updateItem(Agent agent, boolean empty) {
                super.updateItem(agent, empty);

                if (agent != null) {
                    HBox hBox = new HBox();
                    ImageView editView = new ImageView(new Image(getClass().getResource("../../resources/images/16/pencil.png").toString()));
                    ImageView deleteView = new ImageView(new Image(getClass().getResource("../../resources/images/16/trash-o.png").toString()));
                    Pane separator = new Pane();
                    separator.setMinWidth(8);
                    hBox.getChildren().addAll(editView, separator, deleteView);

                    editView.setOnMouseClicked(event -> {
                        try {
                            Dialog<Agent> dialog = new Dialog<>();
                            dialog.setDialogPane(FXMLLoader.load(getClass().getResource("../../resources/view/agent-dialog.fxml")));
                            dialog.setTitle("Edit agent");

                            DialogPane pane = dialog.getDialogPane();
                            TextField nameField = (TextField) pane.lookup("#nameField");
                            TextField loginField = (TextField) pane.lookup("#loginField");
                            TextField passwordField = (TextField) pane.lookup("#passwordField");
                            TextField addressField = (TextField) pane.lookup("#addressField");

                            nameField.setText(agent.getName());
                            loginField.setText(agent.getLogin());
                            passwordField.setText(agent.getPassword());
                            addressField.setText(agent.getAddress());

                            dialog.setResultConverter(buttonType -> {
                                agent.setName(nameField.getText());
                                agent.setLogin(loginField.getText());
                                agent.setPassword(passwordField.getText());
                                agent.setAddress(addressField.getText());
                                return agent;
                            });
                            Optional<Agent> result = dialog.showAndWait();
                            result.ifPresent(res -> {
                                tableView.refresh();
                                agentService.addAgent(agent);
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    deleteView.setOnMouseClicked(event -> {
                        tableView.getItems().remove(agent);
                        agentService.delete(agent);
                    });

                    setGraphic(hBox);
                } else {
                    setGraphic(null);
                }
            }
        });
        editColumn.setCellValueFactory(cellData -> {
            TableColumn.CellDataFeatures data = (TableColumn.CellDataFeatures) cellData;
            Agent item = (Agent) data.getValue();
            return new ReadOnlyObjectWrapper<>(item);
        });

        try {
            tableView.getItems().addAll(agentService.getAll());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection failed");
            alert.setHeaderText("Connection failed");
            alert.setContentText("Ooops, there was an error!\n" + e.toString());

            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    public void addAgent(MouseEvent actionEvent) {
        try {
            Dialog<Agent> dialog = new Dialog<>();
            dialog.setDialogPane(FXMLLoader.load(getClass().getResource("../../resources/view/agent-dialog.fxml")));
            dialog.setTitle("Add new agent");
            dialog.getDialogPane().setHeaderText("Add new agent");
            dialog.getDialogPane().lookupButton(
                    dialog.getDialogPane().getButtonTypes().filtered(buttonType -> buttonType.getText().equals("Save")).get(0)
            ).setDisable(true);
            dialog.setResultConverter(buttonType -> {
                DialogPane pane = dialog.getDialogPane();
                TextField nameField = (TextField) pane.lookup("#nameField");
                TextField loginField = (TextField) pane.lookup("#loginField");
                TextField passwordField = (TextField) pane.lookup("#passwordField");
                TextField addressField = (TextField) pane.lookup("#addressField");
                Agent agent = new Agent();
                agent.setName(nameField.getText());
                agent.setLogin(loginField.getText());
                agent.setPassword(passwordField.getText());
                agent.setAddress(addressField.getText());
                return agent;
            });
            Optional<Agent> result = dialog.showAndWait();
            result.ifPresent(agent -> {
                tableView.getItems().add(agent);
                agentService.addAgent(agent);
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
