public class Print {
    private int[] array;
    private int sumFromThreads;

    public Print(int[] array) {
        this.array = array;
    }

    public synchronized void print(int first, int last, int num) {
        int sum = 0;
        for (int i = first; i <= last; i++) {
            sum += array[i];
        }
        System.out.println("Thread " + num + ": from " + first + " to " + last + " sum is " + sum);
        sumFromThreads += sum;
    }

    public int getSumFromThread() {
        return sumFromThreads;
    }
}