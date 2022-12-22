import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class Program {

    private static Path path, newPath;

    public static void main(String[] args) {
        Utils.checkArgs(args);

        path = Paths.get(args[0].substring("--current-folder=".length()));
        Utils.checkPath(path);
        System.out.println(path);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            execute(scanner);
        }
    }

    private static void execute(Scanner scanner) {
        String string = scanner.nextLine();
        String[] strings = string.split("\\s+");

        if (strings.length == 1 && "exit".equals(strings[0])) {
            scanner.close();
            System.exit(0);
        } else if (strings.length == 1 && "ls".equals(strings[0])) {
            executeLS();
        } else if (strings.length == 2 && "cd".equals(strings[0])) {
            executeCD(strings[1]);
        } else if (strings.length == 3 && "mv".equals(strings[0])) {
            executeMV(strings[1], strings[2]);
        } else {
            System.out.println("UNKNOWN COMMAND");
        }
    }

    private static void executeMV(String what, String where) {
        Path source = null;

        try (DirectoryStream<Path> files = Files.newDirectoryStream(path)) {
            for (Path tmp : files) {
                if (tmp.getFileName().toString().equals(what) && Files.isRegularFile(tmp)) {
                    source = tmp;
                    break;
                }
            }

            if (source == null) {
                System.err.println("mv: no such file: " + what);
                files.close();
                return;
            }

            if (isDirectory(where)) {
                Files.move(source, newPath.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.move(source, source.resolveSibling(Paths.get(where)));
            }
        } catch(IOException e) {
            System.err.println("Error: command mv");
            System.err.println(e.getMessage());
        }

    }

    private static void executeLS() {
        try (DirectoryStream<Path> files = Files.newDirectoryStream(path)) {
            for (Path tmp : files) {
                long size;

                if (Files.isDirectory(tmp)) {
                    size = Utils.directorySize(tmp);
                } else {
                    size = Files.size(tmp);
                }
                System.out.println(tmp.getFileName() + " " + Utils.getSize(size));
            }
        } catch(IOException e) {
            System.out.println("Error: command ls");
            System.out.println(e.getMessage());
        }
    }

    private static void executeCD(String dir) {
        if (isDirectory(dir)) {
            path = newPath;
        } else {
            System.err.println("cd: no such directory: " + dir);
        }
        System.out.println(path);
    }

    private static boolean isDirectory(String strPath) {
        newPath = Paths.get(strPath);
        newPath = path.resolve(newPath).normalize();

        if (Files.isDirectory(newPath)) {
            return true;
        }
        return false;
    }
}

