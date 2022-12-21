import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            int number = scanner.nextInt();
            if (number <= 1) {
                System.err.print("Illegal Argument");
                System.exit(-1);
            } else {
                int i = 2;
                for (; i * i <= number; i++) {
                    if (number % i == 0)
                        break;
                }
                if (i * i > number)
                    System.out.println("true " + --i);
                else
                    System.out.println("false " + --i);
            }
        }

        scanner.close();
    }
}

