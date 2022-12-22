import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Signature {
    private static final String FILE = "Java_Bootcamp._Day02-0/src/ex00/test.txt";
    public  static Map<String, String> fileReader() {
        Map <String, String> signature = new HashMap<>();
        FileInputStream fileInputStream;
        try (Scanner scanner = new Scanner(new FileInputStream(FILE)))
        {
            while (scanner.hasNextLine()) {
                String string = scanner.nextLine();
                String[] array = string.split(",");
                signature.put(array[0],array[1].replaceAll("\\s+", ""));
            }

        } catch (IOException ex) {
            System.err.println("File not found");
            System.exit(-1);
        }
        return signature;
    }
    public static void readSignature(String pathToFile, Map <String, String> signatureMap) {
        try(FileInputStream inputStream = new FileInputStream(pathToFile)) {
            byte[] bytes = new byte[8];
            inputStream.read(bytes, 0, 8);
            String signature = bytesToHex(bytes);
            findSignatureAndWrite(signature, signatureMap);
        } catch (Exception e) {
            System.err.println("File not found");
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for(byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }

    private static void findSignatureAndWrite(String signature, Map<String, String> signatureMap) {

        try(FileOutputStream fileOutputStream = new FileOutputStream("result.txt", true)) {
            for(Map.Entry<String, String> i : signatureMap.entrySet()) {
                if(signature.contains(i.getValue())) {
                    fileOutputStream.write(i.getKey().getBytes());
                    fileOutputStream.write('\n');
                    System.out.println("PROCESSED");
                    return;
                }
            }
            System.out.println("UNDEFINED");
        } catch (Exception e) {
            System.err.println("File not found");
        }
    }

}
