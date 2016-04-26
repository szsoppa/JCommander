package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import sample.file_operation.CopyOperation;
import sample.file_operation.Operation;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Szymon on 25.04.2016.
 */
public class ProgressDialogController implements Initializable {

    @FXML
    ProgressBar progressBar;

    @FXML
    Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progressBar.setProgress(1.0);
    }

    public ProgressDialogController() {
    }

    public void startOperation(Operation operation) {
        new Thread(operation).start();
    }
}