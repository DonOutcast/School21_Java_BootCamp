public class Hen implements Runnable {
    private int count;
    private Print print;

    public Hen(int count, Print print) {
        this.count = count;
        this.print = print;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            try {
                print.printHen();
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}