package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Szymon on 24.04.2016.
 */
public class Table {

    private SimpleStringProperty rName;
    private SimpleIntegerProperty rSize;
    private SimpleStringProperty rDate;

    public Table (String rName, int rSize, String rDate) {
        this.rName = new SimpleStringProperty(rName);
        this.rSize = new SimpleIntegerProperty(rSize);
        this.rDate = new SimpleStringProperty(rDate);
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

    public int getrSize() {
        return rSize.get();
    }

    public SimpleIntegerProperty rSizeProperty() {
        return rSize;
    }

    public void setrSize(int rSize) {
        this.rSize.set(rSize);
    }

    public String getrDate() {
        return rDate.get();
    }

    public SimpleStringProperty rDateProperty() {
        return rDate;
    }

    public void setrDate(String rDate) {
        this.rDate.set(rDate);
    }
}
