package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Szymon on 24.04.2016.
 */
public class Table {

    private SimpleStringProperty rName;
    private SimpleStringProperty rSize;
    private SimpleStringProperty rTime;

    public Table (String rName, String rSize, String rDate) {
        this.rName = new SimpleStringProperty(rName);
        this.rSize = new SimpleStringProperty(rSize);
        this.rTime = new SimpleStringProperty(rDate);
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
}
