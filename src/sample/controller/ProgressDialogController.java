package sample.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import sample.file_operation.CopyOperation;
import sample.file_operation.Operation;
import sample.file_operation.OperationUtil;

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
    }

    public void startOperation(Operation operation) {
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                operation.progressProperty().addListener((observable, oldValue, newValue) -> {
                    long size = OperationUtil.getFileSize(operation.getWorkingFilePath());
                    System.out.println(size);
                    updateProgress(operation.getProgress(), size);
                });
                operation.execute();
                return null;
            }
        };
        progressBar.progressProperty().bind(operation.progressProperty());
        new Thread(task).start();
    }
}
