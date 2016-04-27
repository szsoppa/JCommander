package sample.file_operation;

import java.io.File;

/**
 * Created by Szymon on 27.04.2016.
 */
public class OperationUtil {
    public static long getFileSize(String path) {
        long size = 0;
        File root = new File(path);

        if (root.isFile()) return root.length();

        File[] list = root.listFiles();

        if (list == null) return size;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                size += getFileSize(f.getAbsolutePath());
            }
            else {
                size += f.length();
            }
        }
        return size;
    }
}
