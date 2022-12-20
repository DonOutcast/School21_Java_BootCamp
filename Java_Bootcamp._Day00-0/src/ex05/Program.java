package ex05;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] names = new String[10];
        String[][] classes = new String[7][10];
        String[][][][] attendance = new String[10][31][10][1];
        String day;
        String time;
        String name = scanner.next();
        if(name.length() > 10) {
            System.err.println("Max length of a student's name is 10", scanner);
            System.exit(-1);
        }

        for (int i = 0; !name.equals("."); i++) {
            names[i] = name;
            name = scanner.next();
        }
        time = scanner.next();

        while (!time.equals(".")) {
            day = scanner.next();

            switch (day) {
                case "MO" -> fillClasses(classes[0], time);
                case "TU" -> fillClasses(classes[1], time);
                case "WE" -> fillClasses(classes[2], time);
                case "TH" -> fillClasses(classes[3], time);
                case "FR" -> fillClasses(classes[4], time);
                case "SA" -> fillClasses(classes[5], time);
                case "SU" -> fillClasses(classes[6], time);
            }
            time = scanner.next();
        }
        name = scanner.next();

        while (!name.equals(".")) {
            int i, date, t;
            time = scanner.next();
            date = scanner.nextInt();
            String here = scanner.next();

            for (i = 0; i < names.length && !names[i].equals(name); i++);
            t = findDayAndClassOfWeek(date, classes, time);
            attendance[i][date][t][0] = here;
            name = scanner.next();
        }

        for (int i = 1; i < 31; i++) {
            findTimeOfClassAndPrint(i, classes);
        }

        for (int i = 0; i < names.length && names[i] != null; i++) {
            System.out.print(names[i]);
            findAttendanceAndPrint(attendance[i], classes, names[i].length());
        }
    }

    private static void findAttendanceAndPrint(String[][][] strings, String[][] classes, int len) {
        boolean isFirst = true;

        for (int i = 1; i < 31; i++) {
            for (int j = 0; j < 10 && classes[i % 7][j] != null; j++) {
                if (isFirst) {
                    isFirst = false;

                    for (int z = 0; z < 13 - len; z++) {
                        System.out.print(" ");
                    }
                }

                if (i > 9) {
                    System.out.print(" ");
                }

                if (strings[i][j][0] != null && strings[i][j][0].equals("HERE")) {
                    System.out.print(" 1|        ");
                } else if (strings[i][j][0] != null && strings[i][j][0].equals("NOT_HERE")) {
                    System.out.print("-1|        ");
                } else {
                    System.out.print("  |        ");
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
        if (i == 1) {
            System.out.print("     ");
        }
        int dow = i % 7;

        for (int j = 0; j < 10 && classes[dow][j] != null; j++) {
            System.out.print(classes[dow][j] + ":00 ");

            if (dow == 0) {
                System.out.print("MO  " + i + "|");
            } else if (dow == 1) {
                System.out.print("TU  " + i + "|");
            } else if (dow == 2) {
                System.out.print("WE  " + i + "|");
            } else if (dow == 3) {
                System.out.print("TH  " + i + "|");
            } else if (dow == 4) {
                System.out.print("FR  " + i + "|");
            } else if (dow == 5) {
                System.out.print("SA  " + i + "|");
            } else {
                System.out.print("SU  " + i + "|");
            }
        }

        if (i == 30) {
            System.out.println();
        }
    }

    public static void printErrorAndExit(String message, Scanner s) {
        System.err.println(message);
        s.close();
        System.exit(-1);
    }

