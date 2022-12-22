import java.io.*;
import java.util.*;

public class Program {
    private static List<String> dictionary = new ArrayList<>();
    private static int[] vector1;
    private static int[] vector2;
    private static double similarity;

    public static void main(String[] args) {
        if(args.length != 2) {
            System.err.println("Pass 2 files as arguments");
            System.exit(-1);
        }
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("dictionary.txt"))) {
            BufferedReader reader1 = new BufferedReader(new FileReader(args[0]));
            BufferedReader reader2 = new BufferedReader(new FileReader(args[1]));
            createDictionary(reader1);
            createDictionary(reader2);
            Collections.sort(dictionary);
            reader1.close();
            reader2.close();
            for (String word : dictionary) {
                writer.write(word);
                writer.newLine();
            }
            vector1 = new int[dictionary.size()];
            vector2 = new int[dictionary.size()];
            reader1 = new BufferedReader(new FileReader(args[0]));
            reader2 = new BufferedReader(new FileReader(args[1]));
            createVector(reader1, vector1);
            createVector(reader2, vector2);
            reader1.close();
            reader2.close();
            similarityCalculation();
            if((new File(args[0])).length() == 0 || (new File(args[1])).length() == 0) {
                similarity = 0;
            }
            if((new File(args[0])).length() == 0 && (new File(args[1])).length() == 0) {
                similarity = 1;
            }
            System.out.println("Similarity = " + String.format("%.2f", Math.floor(similarity * 100) / 100.0));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    private static void createDictionary(BufferedReader reader) throws IOException {
        String string;

        while((string = reader.readLine()) != null) {
            String[] words = string.replaceAll("\\p{Punct}", "").toLowerCase().split("\\s+");
            for(String word : words) {
                if(!dictionary.contains(word) && !word.isEmpty()) {
                    dictionary.add(word);
                }
            }
        }
    }

    private static void createVector(BufferedReader reader, int[] vector) throws IOException {
        String string;
        int i;

        while ((string = reader.readLine()) != null) {
            String[] words = string.replaceAll("\\p{Punct}", "")
                    .toLowerCase().split("\\s+");
            for(String word : words) {
                if(!word.isEmpty()) {
                    i = dictionary.indexOf(word);
                    vector[i]++;
                }
            }
        }
    }

    private static void similarityCalculation() {
        double numerator = 0;
        double denominatorA = 0;
        double denominatorB = 0;
        for (int i = 0; i < vector1.length; i++) {
            numerator += vector1[i] * vector2[i];
            denominatorA += vector1[i] * vector1[i];
            denominatorB += vector2[i] * vector2[i];
        }
        similarity = numerator / (Math.sqrt(denominatorA) * Math.sqrt(denominatorB));
    }
}
