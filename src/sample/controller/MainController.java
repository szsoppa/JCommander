package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.dialog.ProgressDialog;
import sample.file_operation.CopyOperation;
import sample.file_operation.DeleteOperation;
import sample.file_operation.MoveOperation;
import sample.model.FileItem;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    TableView<FileItem> activeTable;
    @FXML
    TableView<FileItem> leftTable;
    @FXML
    TableView<FileItem> rightTable;

    @FXML
    TableColumn<FileItem, String> leftColumnName;
    @FXML
    TableColumn<FileItem, String> leftColumnSize;
    @FXML
    TableColumn<FileItem, String> leftColumnTime;

    @FXML
    TableColumn<FileItem, String> rightColumnName;
    @FXML
    TableColumn<FileItem, String> rightColumnSize;
    @FXML
    TableColumn<FileItem, String> rightColumnTime;

    @FXML
    Button leftSideRoot;
    @FXML
    Button leftSideUpper;
    @FXML
    Button rightSideRoot;
    @FXML
    Button rightSideUpper;
    @FXML
    Button buttonCopy;
    @FXML
    Button buttonMove;
    @FXML
    Button buttonDelete;
    @FXML
    Button buttonRefresh;

    @FXML
    MenuItem changeLanguageEnglish;
    @FXML
    MenuItem changeLanguagePolish;
    @FXML
    MenuItem menuFile;
    @FXML
    MenuItem menuHelp;
    @FXML
    MenuItem menuItemClose;
    @FXML
    MenuItem menuItemSwitchLanguage;
    @FXML
    MenuItem menuItemAbout;

    @FXML
    TextField leftPathInput;
    @FXML
    TextField rightPathInput;

    @FXML
    ComboBox leftComboBox;
    @FXML
    ComboBox rightComboBox;


    private static String osType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        osType = System.getProperty("os.name").toLowerCase();
        initializeColumns();
        initializeTable(leftTable, null);
        initializeTable(rightTable, null);
        initializeRoot(leftComboBox, leftTable, leftPathInput);
        initializeRoot(rightComboBox, rightTable, rightPathInput);
        initializeEvents();
    }

    private void initializeEvents() {
        initializeOpenFileEvent(leftTable);
        initializeOpenFileEvent(rightTable);
        leftSideRoot.setOnMouseClicked(event -> initializeTable(leftTable, leftComboBox.getValue().toString()));
        rightSideRoot.setOnMouseClicked(event -> initializeTable(rightTable, rightComboBox.getValue().toString()));
        leftSideUpper.setOnMouseClicked(event -> {
            File file = new File(leftPathInput.getText());
            File parentFile = file.getParentFile();
            if (parentFile != null)
                initializeTable(leftTable, parentFile.getPath());
        });
        rightSideUpper.setOnMouseClicked(event -> {
            File file = new File(rightPathInput.getText());
            File parentFile = file.getParentFile();
            if (parentFile != null)
                initializeTable(rightTable, parentFile.getPath());
        });
        changeLanguageEnglish.setOnAction(e -> changeLanguage("en"));
        changeLanguagePolish.setOnAction(e -> changeLanguage("pl"));
        menuItemClose.setOnAction(e -> {
            Stage stage = (Stage) leftTable.getScene().getWindow();
            stage.close();
        });
        buttonDelete.setOnMouseClicked(e -> {
            ProgressDialog progressDialog = null;
            try {
                progressDialog = new ProgressDialog();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (progressDialog != null) {
                FileItem fileItem = activeTable.getSelectionModel().getSelectedItem();
                progressDialog.show();
                progressDialog.dialogActiveProperty().addListener(e2 -> {
                    initializeTable(leftTable, leftPathInput.getText());
                    initializeTable(rightTable, rightPathInput.getText());
                });
                progressDialog.runOperation(new DeleteOperation(fileItem.getrPath()));
            }
        });
        buttonCopy.setOnMouseClicked(e -> {
            ProgressDialog progressDialog = null;
            try {
                progressDialog = new ProgressDialog();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (progressDialog != null) {
                FileItem fileItem = leftTable.getSelectionModel().getSelectedItem();
                Path targetPath = Paths.get(rightPathInput.getText() + "/" + fileItem.getrName());
                progressDialog.show();
                progressDialog.dialogActiveProperty().addListener(e2 -> {
                    initializeTable(leftTable, leftPathInput.getText());
                    initializeTable(rightTable, rightPathInput.getText());
                });
                progressDialog.runOperation(new CopyOperation(fileItem.getrPath(), targetPath.toString()));
            }
        });
        buttonMove.setOnMouseClicked(e -> {
            ProgressDialog progressDialog = null;
            try {
                progressDialog = new ProgressDialog();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (progressDialog != null) {
                FileItem fileItem = leftTable.getSelectionModel().getSelectedItem();
                Path targetPath = Paths.get(rightPathInput.getText() + "/" + fileItem.getrName());
                progressDialog.show();
                progressDialog.dialogActiveProperty().addListener(e2 -> {
                    initializeTable(leftTable, leftPathInput.getText());
                    initializeTable(rightTable, rightPathInput.getText());
                });
                progressDialog.runOperation(new MoveOperation(fileItem.getrPath(), targetPath.toString()));
            }
        });
        buttonRefresh.setOnMouseClicked(e -> {
            initializeTable(leftTable, leftPathInput.getText());
            initializeTable(rightTable, rightPathInput.getText());
        });
        leftPathInput.setOnKeyPressed(e -> {
            if(e.getCode().toString().equals("ENTER")) initializeTable(leftTable, leftPathInput.getText());
        });
        rightPathInput.setOnKeyPressed(e -> {
            if(e.getCode().toString().equals("ENTER")) initializeTable(rightTable, rightPathInput.getText());
        });
        leftTable.setOnMouseClicked(e -> activeTable = leftTable);
        rightTable.setOnMouseClicked(e -> activeTable = rightTable);
    }

    private void changeLanguage(String locale) {
        Locale.setDefault(new Locale(locale));
        ResourceBundle bundle = ResourceBundle.getBundle("strings", Locale.getDefault());
        leftColumnName.setText(bundle.getString("table.name"));
        leftColumnSize.setText(bundle.getString("table.size"));
        leftColumnTime.setText(bundle.getString("table.time"));
        rightColumnName.setText(bundle.getString("table.name"));
        rightColumnSize.setText(bundle.getString("table.size"));
        rightColumnTime.setText(bundle.getString("table.time"));
        menuFile.setText(bundle.getString("menu.file"));
        menuHelp.setText(bundle.getString("menu.help"));
        menuItemClose.setText(bundle.getString("menu.file.close"));
        menuItemAbout.setText(bundle.getString("menu.item.about"));
        menuItemSwitchLanguage.setText(bundle.getString("menu.file.language"));
        changeLanguageEnglish.setText(bundle.getString("menu.file.language.english"));
        changeLanguagePolish.setText(bundle.getString("menu.file.language.polish"));
        buttonCopy.setText(bundle.getString("button.copy"));
        buttonMove.setText(bundle.getString("button.move"));
        buttonDelete.setText(bundle.getString("button.delete"));
    }

    private void initializeColumns() {
        leftColumnName.setCellValueFactory(new PropertyValueFactory<FileItem, String>("rName"));
        leftColumnSize.setCellValueFactory(new PropertyValueFactory<FileItem, String>("rSize"));
        leftColumnTime.setCellValueFactory(new PropertyValueFactory<FileItem, String>("rTime"));
        rightColumnName.setCellValueFactory(new PropertyValueFactory<FileItem, String>("rName"));
        rightColumnSize.setCellValueFactory(new PropertyValueFactory<FileItem, String>("rSize"));
        rightColumnTime.setCellValueFactory(new PropertyValueFactory<FileItem, String>("rTime"));
    }

    private void initializeRoot(ComboBox comboBox, TableView tableView, TextField textField) {
        ObservableList<String> rootsList = FXCollections.observableArrayList();
        Arrays.stream(File.listRoots()).forEach(file -> rootsList.add(file.toString()));
        comboBox.setItems(rootsList);
        comboBox.setValue(rootsList.get(0));

        comboBox.setOnAction(event -> {
            String root = comboBox.getValue().toString();
            textField.setText(root);
            initializeTable(tableView, root);
        });
    }

    public void initializeOpenFileEvent(TableView<FileItem> table) {
        table.setOnMousePressed(event -> {
            if(event.getClickCount() == 2) {
                FileItem fileItem = table.getSelectionModel().getSelectedItem();
                if (fileItem.isDirectory())
                    initializeTable(table, fileItem.getrPath());
                else {
                    try {
                        Desktop.getDesktop().open(new File(fileItem.getrPath()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initializeTable(TableView<FileItem> table, String path) {
        ObservableList<FileItem> data = FXCollections.observableArrayList();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        if (path == null)
            path = getRootDirectory();
        if (table.getId().contains("left"))
            setPathInput(leftPathInput, path);
        else
            setPathInput(rightPathInput, path);

        File directory = new File(path);
        File[] fList = directory.listFiles();
        for (File file: fList) {
            FileItem row = null;
            if (file.isHidden())
                continue;
            else if (file.isFile())
                row = new FileItem(file.getName(), Long.toString(file.length()), dateFormatter.format(file.lastModified()), file.getAbsolutePath(), false);
            else if (file.isDirectory())
                row = new FileItem(file.getName(), "<DIR>", dateFormatter.format(file.lastModified()), file.getAbsolutePath(), true);
            else
                continue;
            data.add(row);
        }
        table.setItems(data);
    }

    private void setPathInput(TextField leftPathInput, String path) {
        leftPathInput.setText(path);
    }

    private String getRootDirectory() {
        if (isWindows())
            return "C:\\";
        else
            return FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
    }

    private static boolean isWindows() {
        return (osType.contains("win"));
    }

}
