package sample.dialog;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.controller.ProgressDialogController;
import sample.file_operation.Operation;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Szymon on 25.04.2016.
 */

public class ProgressDialog {

    private Stage stage;
    ProgressDialogController progressDialogController;

    public ProgressDialog() throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("strings", Locale.getDefault());

        stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../progressDialog.fxml"));
        loader.setResources(bundle);
        Parent root = loader.load();

        progressDialogController = loader.getController();
        stage.setScene(new Scene(root));
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }

    public void runOperation(Operation operation) {
        progressDialogController.startOperation(operation);
    }
}
