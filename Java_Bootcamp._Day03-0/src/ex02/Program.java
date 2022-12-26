
import java.util.Arrays;
import java.util.Random;

public class Program {
    private static int arraySize, threadsCount;
    private static int[] array;
    private static NewThread[] threads;
    private static Print print;

    public static void main(String[] args) {
        try {
            if(args.length != 2 || !args[0].startsWith("--arraySize=") || !args[1].startsWith("--threadsCount=")) {
                System.out.println("Wrong arguments");
                System.exit(-1);
            }
            arraySize = Integer.parseInt(args[0].substring("--arraySize=".length()));
            threadsCount = Integer.parseInt(args[1].substring("--threadsCount=".length()));
            if(arraySize > 2_000_000 || threadsCount < 1 || threadsCount > arraySize) {
                System.out.println("Wrong arguments");
                System.exit(-1);
            }
            createArray();
            createThreads();
            System.out.println("Sum by threads: " + print.getSumFromThread());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid argument");
            System.out.println(ex.getMessage());
        }

    }

    private static void createArray() {
        int chunk, first, last = 0;
        int n = 0;
        array = new int[arraySize];
        threads = new NewThread[threadsCount];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(2001) - 1000;
        }
        System.out.println("Sum: " + Arrays.stream(array).sum());
        print = new Print(array);
        chunk = arraySize / threadsCount;
        for (int i = 0; i < (threads.length - 1); i++, n++) {
            first = chunk * n;
            last = first + chunk - 1;
            threads[i] = new NewThread((i + 1), first, last, print);
        }
        if(arraySize % threadsCount != 0) {
            chunk = arraySize - (chunk * (threadsCount - 1));
        }
        if(threadsCount > 1) {
            threads[threads.length - 1] = new NewThread(threads.length, last + 1, last + chunk, print);
        } else {
            threads[0] = new NewThread(1, 0, arraySize - 1, print);
        }
    }

    private static void createThreads() {
        try {
            for (int i = 0; i < threads.length; i++) {
                threads[i].start();
                NewThread.sleep(5);
            }
            for (int i = 0; i < threads.length; i++) {
                threads[i].join();
            }
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
