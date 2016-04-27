package sample.file_operation;

import javafx.beans.property.*;

import java.io.IOException;

/**
 * Created by Szymon on 25.04.2016.
 */
public abstract class Operation {
    protected DoubleProperty progress;

    public Operation() {
        this.progress = new SimpleDoubleProperty(0.0);
    }

    public abstract void execute() throws IOException;

    public double getProgress() {
        return progress.get();
    }

    public DoubleProperty progressProperty() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress.set(progress);
    }
}
