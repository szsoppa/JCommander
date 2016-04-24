package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.Table;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class MainController implements Initializable{

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

    private static String osType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        osType = System.getProperty("os.name").toLowerCase();
        leftColumnName.setCellValueFactory(new PropertyValueFactory<Table, String>("rName"));
        leftColumnSize.setCellValueFactory(new PropertyValueFactory<Table, String>("rSize"));
        leftColumnTime.setCellValueFactory(new PropertyValueFactory<Table, String>("rTime"));
        rightColumnName.setCellValueFactory(new PropertyValueFactory<Table, String>("rName"));
        rightColumnSize.setCellValueFactory(new PropertyValueFactory<Table, String>("rSize"));
        rightColumnTime.setCellValueFactory(new PropertyValueFactory<Table, String>("rTime"));
        initializeTable(leftTable);
        initializeTable(rightTable);
    }

    private void initializeTable(TableView<Table> table) {
        ObservableList<Table> data = FXCollections.observableArrayList();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        File directory = new File(getRootDirectory());
        File[] fList = directory.listFiles();
        for (File file: fList) {
            Table row = null;
            if (file.isHidden())
                continue;
            else if (file.isFile())
                row = new Table(file.getName(), Long.toString(file.length()), dateFormatter.format(file.lastModified()));
            else if (file.isDirectory())
                row = new Table(file.getName(), "<<DIR>>", dateFormatter.format(file.lastModified()));
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

    private static boolean isMac() {
        return (osType.contains("mac"));
    }

    private static boolean isUnix() {
        return (osType.contains("nix") || osType.contains("nux") || osType.contains("aix"));
    }

}
