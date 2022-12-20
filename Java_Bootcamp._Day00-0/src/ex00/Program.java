package ex00;

public class Program {
    public static void main(String[] args) {
        int number = 479598;
        int summary = 0;
        summary += number % 10;
        number /= 10;
        summary += number % 10;
        number /= 10;
        summary += number % 10;
        number /= 10;
        summary += number % 10;
        number /= 10;
        summary += number % 10;
        number /= 10;
        summary += number % 10;
        System.out.print(summary);
    }
}

