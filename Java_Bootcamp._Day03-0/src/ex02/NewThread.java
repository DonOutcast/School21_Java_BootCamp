public class NewThread extends Thread {
    private int num;
    private int first;
    private int last;
    private Print print;

    public NewThread(int num, int first, int last, Print print) {
        this.num = num;
        this.first = first;
        this.last = last;
        this.print = print;
    }

    @Override
    public void run() {
        print.print(first, last, num);
    }
}
