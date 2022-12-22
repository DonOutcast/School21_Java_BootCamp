import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Utils {
    public static long directorySize(Path tmpPath) {
        long size = 0;

        try (DirectoryStream<Path> files = Files.newDirectoryStream(tmpPath)) {
            for (Path tmp : files) {
                if (Files.isDirectory(tmp)) {
                    size += directorySize(tmp);
                } else {
                    size += Files.size(tmp);
                }
            }
        } catch (IOException e) {
            System.out.println("Error: directorySize");
            System.out.println(e.getMessage());
        }
        return size;
    }

    public static String getSize(long size) {
        return "" + (size / 1000) + " KB";
    }

    public static void checkPath(Path path) {
        if (!path.isAbsolute()) {
            System.err.println("The path is not absolute");
            System.exit(-1);
        }

        if (!Files.isDirectory(path)) {
            System.err.println("The path is not a directory");
            System.exit(-1);
        }
    }

    public static void checkArgs(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--current-folder=")) {
            System.err.println("Wrong argument");
            System.exit(-1);
        }
    }
}
