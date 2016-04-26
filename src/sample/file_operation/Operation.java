package sample.file_operation;

import javafx.beans.property.*;

/**
 * Created by Szymon on 25.04.2016.
 */
public abstract class Operation implements Runnable {
    protected DoubleProperty progress;

    public Operation() {
        this.progress = new SimpleDoubleProperty(0.0);
    }

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
