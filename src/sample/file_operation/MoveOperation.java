package sample.file_operation;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

/**
 * Created by Szymon on 26.04.2016.
 */
public class MoveOperation extends Operation {

    private String pathFrom;
    private String pathTo;

    public MoveOperation(String pathFrom, String pathTo) {
        super();
        this.pathFrom = pathFrom;
        this.pathTo = pathTo;
    }

    @Override
    public void execute() {
        final Path sourceDir = Paths.get(pathFrom);
        final Path targetDir = Paths.get(pathTo);
        try {
            Files.walkFileTree(sourceDir, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
                    new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                            if (getOperationClosed()) return FileVisitResult.TERMINATE;
                            Path target = targetDir.resolve(sourceDir.relativize(dir));
                            try {
                                Files.copy(dir, target);
                            } catch (FileAlreadyExistsException e) {
                                if (!Files.isDirectory(target))
                                    throw e;
                            }
                            return FileVisitResult.CONTINUE;
                        }
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            if (getOperationClosed()) return FileVisitResult.TERMINATE;
                            long fileSize = (new File(file.toString())).length();
                            Files.move(file, targetDir.resolve(sourceDir.relativize(file)));
                            incrementProgress(fileSize);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            if (getOperationClosed()) return FileVisitResult.TERMINATE;
                            Files.delete(dir);
                            return FileVisitResult.CONTINUE;
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getWorkingFilePath() {
        return this.pathFrom;
    }

    public String getPathFrom() {
        return pathFrom;
    }

    public void setPathFrom(String pathFrom) {
        this.pathFrom = pathFrom;
    }

    public String getPathTo() {
        return pathTo;
    }

    public void setPathTo(String pathTo) {
        this.pathTo = pathTo;
    }
}
