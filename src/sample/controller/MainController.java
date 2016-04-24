package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.model.Table;

public class MainController {

    @FXML
    TableView<Table> leftTable;
    @FXML
    TableView<Table> rightTable;

    @FXML
    TableColumn<Table, String> leftColumnName;
    @FXML
    TableColumn<Table, String> leftColumnSize;
    @FXML
    TableColumn<Table, String> leftColumnTime;

    @FXML
    TableColumn<Table, String> rightColumnName;
    @FXML
    TableColumn<Table, String> rightColumnSize;
    @FXML
    TableColumn<Table, String> rightColumnTime;

    public MainController() {

    }

    public void showSomething() {
        System.out.println("Something");
    }
}
