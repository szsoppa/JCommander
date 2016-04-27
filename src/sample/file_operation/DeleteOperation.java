package sample.file_operation;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Szymon on 24.04.2016.
 */
public class DeleteOperation extends Operation {

    private String path;

    public DeleteOperation(String path) {
        super();
        this.path = path;
    }

    @Override
    public void execute() {
        Path dirToDel = Paths.get(path);
        try {
            Files.walkFileTree(dirToDel, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (getOperationClosed()) return FileVisitResult.TERMINATE;
                    long fileSize = (new File(file.toString())).length();
                    Files.delete(file);
                    incrementProgress(fileSize);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    if (getOperationClosed()) return FileVisitResult.TERMINATE;
                    if (exc == null) {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    } else {
                        throw exc;
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getWorkingFilePath() {
        return this.path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
