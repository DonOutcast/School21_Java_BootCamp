public class Egg implements Runnable {
    private int count;
    private Print print;

    public Egg(int count, Print print) {
        this.count = count;
        this.print = print;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            try {
                print.printEgg();
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
