package de.estate.manager.controller;

import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ContractListController {
    public TableView tableView;

    public void home(MouseEvent mouseEvent) {
        Stage stage = (Stage) tableView.getScene().getWindow();
        (new HomeController()).open(stage);
    }
}
