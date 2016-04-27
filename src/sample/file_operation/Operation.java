package sample.file_operation;

import javafx.beans.property.*;

import java.io.IOException;

/**
 * Created by Szymon on 25.04.2016.
 */
public abstract class Operation {
    protected LongProperty progress;
    protected BooleanProperty operationClosed;

    public Operation() {
        this.progress = new SimpleLongProperty(0);
        this.operationClosed = new SimpleBooleanProperty(false);
    }

    public abstract void execute() throws IOException;

    public long getProgress() {
        return progress.get();
    }

    public LongProperty progressProperty() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress.set(progress);
    }

    public void incrementProgress(long progress) { this.progress.set(getProgress() + progress);}

    public abstract String getWorkingFilePath ();

    public boolean getOperationClosed() {
        return operationClosed.get();
    }

    public BooleanProperty operationClosedProperty() {
        return operationClosed;
    }

    public void setOperationClosed(boolean operationClosed) {
        this.operationClosed.set(operationClosed);
    }
}
