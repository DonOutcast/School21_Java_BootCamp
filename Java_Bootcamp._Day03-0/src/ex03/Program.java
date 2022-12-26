import java.io.IOException;

public class Program {
    private static int threadsCount;
    private static NewThread[] threads;
    private static Url urls;
    public static void main(String[] args) {
        try {
            if(args.length != 1 || !args[0].startsWith("--threadsCount=")) {
                System.out.println("Wrong argument");
                System.exit(-1);
            }
            threadsCount = Integer.parseInt(args[0].substring("--threadsCount=".length()));
            if(threadsCount < 1) {
                System.out.println("Invalid threadsCount");
                System.exit(-1);
            }
            initThreads();
            createThreads();
        } catch (NumberFormatException ex) {
            System.out.println("Invalid argument");
            System.out.println(ex.getMessage());
        } catch (IOException | RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void initThreads() throws IOException {
        urls = new Url();
        threads = new NewThread[threadsCount];
        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new NewThread(urls, i + 1);
        }
    }

    private static void createThreads() {
        for (int i = 0; i < threadsCount; i++) {
            threads[i].start();
        }
    }
}
