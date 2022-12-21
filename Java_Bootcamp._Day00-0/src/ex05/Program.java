import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] names = new String[10];
        String[][] classes = new String[7][10];
        String[][][][] board = new String[10][30][10][1];
        String day;
        String time;
        checkInput(scanner);
        String name = scanner.next();
        if(name.length() > 10) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }

        for (int i = 0; !name.equals("."); ++i) {
            names[i] = name;
            checkInput(scanner);
            name = scanner.next();
        }
        checkInput(scanner);
        time = scanner.next();

        while (!time.equals(".")) {
            checkInput(scanner);
            day = scanner.next();

            if (day.equals("MO")) {
                fillClasses(classes[0], time);
            } else if (day.equals("TU")) {
                fillClasses(classes[1], time);
            } else if (day.equals("WE")) {
                fillClasses(classes[2], time);
            } else if (day.equals("TH")) {
                fillClasses(classes[3], time);
            } else if (day.equals("FR")) {
                fillClasses(classes[4], time);
            } else if (day.equals("SA")) {
                fillClasses(classes[5], time);
            } else if (day.equals("SU")) {
                fillClasses(classes[6], time);
            }
            checkInput(scanner);
            time = scanner.next();
        }
        checkInput(scanner);
        name = scanner.next();

        while (!name.equals(".")) {
            int i, date, t;
            checkInput(scanner);
            time = scanner.next();
            checkInput(scanner);
            date = scanner.nextInt();
            checkInput(scanner);
            String here = scanner.next();

            for (i = 0; i < names.length && !names[i].equals(name); i++);
            t = findDayAndClassOfWeek(date, classes, time);
            board[i][date - 1][t][0] = here;
            checkInput(scanner);
            name = scanner.next();
        }

        for (int i = 0; i < 30; i++) {
            findTimeOfClassAndPrint(i, classes);
        }
        System.out.println();

        for (int i = 0; i < names.length && names[i] != null; i++) {
            System.out.printf("%10s", names[i]);
            findBoardAndPrint(board[i], classes, names[i].length());
        }
    }

    private static void findBoardAndPrint(String[][][] strings, String[][] classes, int len) {
        boolean isFirst = true;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10 && classes[(i + 1) % 7][j] != null; j++) {
                if (strings[i][j][0] != null && strings[i][j][0].equals("HERE")) {
                    System.out.printf("        %2d|", 1);
                } else if (strings[i][j][0] != null && strings[i][j][0].equals("NOT_HERE")) {
                    System.out.printf("        %2d|", -1);
                } else {
                    System.out.print("          |");
                }
            }
        }
        System.out.println();
    }

    public static int findDayAndClassOfWeek(int date, String[][] classes, String time) {
        int dayOfWeek = date % 7;
        int i = 0;

        for (; i < 10 && !classes[dayOfWeek][i].equals(time); i++);

        if (i < 10) {
            return i;
        }
        return 0;
    }

    public static void fillClasses(String[] classes, String time) {
        int i = 0;

        for (; i < classes.length && classes[i] != null; i++);

        if (i < 10) {
            classes[i] = time;
        }
    }

    private static void findTimeOfClassAndPrint(int i, String[][] classes) {
        if (i == 0) {
            System.out.printf("          ");
        }
        int dow = ++i % 7;

        for (int j = 0; j < 10 && classes[dow][j] != null; j++) {
            System.out.print(classes[dow][j] + ":00 ");

            if (dow == 0) {
                System.out.printf("MO %2d|", i);
            } else if (dow == 1) {
                System.out.printf("TU %2d|", i);
            } else if (dow == 2) {
                System.out.printf("WE %2d|", i);
            } else if (dow == 3) {
                System.out.printf("TH %2d|", i);
            } else if (dow == 4) {
                System.out.printf("FR %2d|", i);
            } else if (dow == 5) {
                System.out.printf("SA %2d|", i);
            } else if (dow == 6) {
                System.out.printf("SU %2d|", i);
            }
        }
    }

    public static void printErrorAndExit(String message, Scanner s) {
        System.err.println(message);
        s.close();
        System.exit(-1);
    }
    public static void checkInput(Scanner scanner) {
        if (!scanner.hasNext()) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
    }
}