package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Szymon on 24.04.2016.
 */
public class FileItem {

    private SimpleStringProperty rName;
    private SimpleStringProperty rSize;
    private SimpleStringProperty rTime;
    private SimpleStringProperty rPath;
    private boolean isDirectory;

    public FileItem(String rName, String rSize, String rDate, String rPath, boolean isDirectory) {
        this.rName = new SimpleStringProperty(rName);
        this.rSize = new SimpleStringProperty(rSize);
        this.rTime = new SimpleStringProperty(rDate);
        this.rPath = new SimpleStringProperty(rPath);
        this.isDirectory = isDirectory;
    }

    public String getrName() {
        return rName.get();
    }

    public SimpleStringProperty rNameProperty() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName.set(rName);
    }

    public String getrSize() {
        return rSize.get();
    }

    public SimpleStringProperty rSizeProperty() {
        return rSize;
    }

    public void setrSize(String rSize) {
        this.rSize.set(rSize);
    }


    public String getrTime() {
        return rTime.get();
    }

    public SimpleStringProperty rTimeProperty() {
        return rTime;
    }

    public void setrTime(String rTime) {
        this.rTime.set(rTime);
    }

    public String getrPath() {
        return rPath.get();
    }

    public SimpleStringProperty rPathProperty() {
        return rPath;
    }

    public void setrPath(String rPath) {
        this.rPath.set(rPath);
    }

    public boolean isDirectory() {
        return isDirectory;
    }
}
