import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String week = "";
        long data = 0;
        int data_order = 0;
        while (!(week = scanner.nextLine()).equals("42") && ++data_order <= 18) {
            if (!week.equals("Week " + data_order)) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            if (!scanner.hasNext()) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }

            int grade1 = scanner.nextInt();
            check_numbers(grade1);
            for (int i = 0; i < 4; i++)
            {
                int grade2 = scanner.nextInt();
                check_numbers(grade2);
                if (grade2 < grade1)
                    grade1 = grade2;
            }
            long pow = 1;
            for (int i = 0; i < data_order - 1; i++)
                pow = pow * 10;
            data = grade1 * pow + data;  
            scanner.nextLine();
        }
        long count = 0;
        for (int i = 1; i <= data_order; i++)
        {
            System.out.printf("Week " + i + "\t");
            count = data % 10;
            data = data / 10;
            for (int j = 0; j < count; j++)
                System.out.print("=");
            System.out.println(">");
        }
        scanner.close();
    }

    public static void check_numbers(int number) {
        if (number < 1 || number > 9) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }

    }
}

