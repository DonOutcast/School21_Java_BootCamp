import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Map<String, String> signatures = Signature.fileReader();

        Scanner scanner = new Scanner(System.in);
        checkScanner(scanner);
        String pathToFile = scanner.nextLine();
        while (!pathToFile.equals("42")) {
            Signature.readSignature(pathToFile, signatures);
            checkScanner(scanner);
            pathToFile = scanner.nextLine();
        }
        scanner.close();
    }

    public static void checkScanner(Scanner scanner) {
        if (!scanner.hasNext()) {
            System.err.println("Error");
            scanner.close();
            System.exit(-1);
        }
    }
}


