import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = 0;
        int count = 0;
        while(number != 42 ) {
            if (!(scanner.hasNext() && scanner.hasNextInt())) {
                scanner.close();
                System.exit(-1);
            }
            number = scanner.nextInt();
            if(is_simple_number(sum_id_integer(number))) {
                count++;
            }
        }
        System.out.println("Count of coffee-request - " + count);
        scanner.close();
    }
    public static int sum_id_integer(int number) {
        int result = 0;
        while (number != 0) {
            result += number % 10;
            number /= 10;
        }
        return result;
    }
    public static boolean is_simple_number(int number) {
        boolean result = true;
        if (number <= 1) {
            System.err.print("Illegal Argument");
            System.exit(-1);
        } else {
            int i = 2;
            for (; i * i <= number; i++) {
                if (number % i == 0)
                    break;
            }
            result = i * i > number;
        }
        return result;
    }
}
