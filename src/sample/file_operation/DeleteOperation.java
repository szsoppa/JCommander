package sample.file_operation;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Szymon on 24.04.2016.
 */
public class DeleteOperation extends Operation{

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
                public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
                    if (e == null) {
                        progress.set(getProgress() + Files.size(dir));
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    } else {
                        System.out.println("Exception while iterating directory.");
                        throw e;
                    }
                }
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
