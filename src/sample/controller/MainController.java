package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.model.FileItem;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable{

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


    private static String osType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        osType = System.getProperty("os.name").toLowerCase();
        initializeColumns();
        initializeTable(leftTable, null);
        initializeTable(rightTable, null);
        initializeEvents();
    }

    private void initializeEvents() {
        leftTable.setOnMousePressed(event -> {
            if(event.getClickCount() == 2) {
                FileItem fileItem = leftTable.getSelectionModel().getSelectedItem();
                if (fileItem.isDirectory())
                    initializeTable(leftTable, fileItem.getrPath());
                else {
                    try {
                        Desktop.getDesktop().open(new File(fileItem.getrPath()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        rightTable.setOnMousePressed(event -> {
            if(event.getClickCount() == 2) {
                FileItem fileItem = rightTable.getSelectionModel().getSelectedItem();
                if (fileItem.isDirectory())
                    initializeTable(rightTable, fileItem.getrPath());
                else {
                    try {
                        Desktop.getDesktop().open(new File(fileItem.getrPath()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        leftSideRoot.setOnMouseClicked(event -> initializeTable(leftTable, null));
        rightSideRoot.setOnMouseClicked(event -> initializeTable(rightTable, null));
        leftSideUpper.setOnMouseClicked(event -> {
            File file = new File(leftTable.getItems().get(0).getrPath());
            File parentFile = file.getParentFile().getParentFile();
            if (parentFile != null)
                initializeTable(leftTable, parentFile.getPath());
        });
        rightSideUpper.setOnMouseClicked(event -> {
            File file = new File(rightTable.getItems().get(0).getrPath());
            File parentFile = file.getParentFile().getParentFile();
            if (parentFile != null)
                initializeTable(rightTable, parentFile.getPath());
        });
        changeLanguageEnglish.setOnAction(e -> changeLanguage("en"));
        changeLanguagePolish.setOnAction(e -> changeLanguage("pl"));
        menuItemClose.setOnAction(e -> {
            Stage stage = (Stage) leftTable.getScene().getWindow();
            stage.close();
        });
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

    private void initializeTable(TableView<FileItem> table, String path) {
        ObservableList<FileItem> data = FXCollections.observableArrayList();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        if (path == null)
            path = getRootDirectory();
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

    private String getRootDirectory() {
        if (isWindows())
            return "C:\\\\";
        else
            return FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
    }

    private static boolean isWindows() {
        return (osType.contains("win"));
    }

}
