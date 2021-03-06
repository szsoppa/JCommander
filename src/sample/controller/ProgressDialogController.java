package sample.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
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

    Task task;
    BooleanProperty taskEnded;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taskEnded = new SimpleBooleanProperty(false);
    }

    public void startOperation(Operation operation) {
        long size = OperationUtil.getFileSize(operation.getWorkingFilePath());
        task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                operation.progressProperty().addListener((observable, oldValue, newValue) -> {
                    updateProgress(operation.getProgress(), size);
                });
                operation.execute();
                return Boolean.TRUE;
            }

        };
        operation.setOperationClosed(taskEndedProperty());
        progressBar.progressProperty().bind(operation.progressProperty());

        task.setOnSucceeded(e -> setTaskEnded(true));
        new Thread(task).start();
        cancelButton.setOnMouseClicked(e -> {
            task.cancel();
            setTaskEnded(true);
        });
    }

    public boolean getTaskEnded() {
        return taskEnded.get();
    }

    public BooleanProperty taskEndedProperty() {
        return taskEnded;
    }

    public void setTaskEnded(boolean taskEnded) {
        this.taskEnded.set(taskEnded);
    }
}
