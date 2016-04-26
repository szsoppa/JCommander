package sample.file_operation;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Szymon on 25.04.2016.
 */
public abstract class Operation implements Runnable {
    protected SimpleLongProperty progress;

    public Operation() {
        this.progress = new SimpleLongProperty(0);
    }

    public long getProgress() {
        return progress.get();
    }

    public SimpleLongProperty progressProperty() {
        return progress;
    }

}
