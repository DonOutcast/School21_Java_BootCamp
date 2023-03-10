public class Program {
    public static void main(String[] args) {
        UsersArrayList userList = new UsersArrayList();
        for (int i = 0; i < 25; i++) {
            userList.addUser(new User(("User" + (i + 1)), (i * 1000)));
        }
        System.out.println("Весь список пользователей:");
        System.out.println(userList);

        User user10 = userList.getUserById(10);
        User user11 = userList.getUserByIndex(10);
        System.out.println("\nИнформация о пользователе с ID = 10");
        System.out.println(user10);
        System.out.println("\nИнформация о пользователе с INDEX = 10");
        System.out.println(user11 + "\n");

        try {
            User user12 = userList.getUserById(28);
        } catch (UserNotFoundException ex) {
            System.out.println(ex);
        }

        try {
            User user13 = userList.getUserByIndex(144);
            System.out.println("Это не напечатается!");
        } catch (UserNotFoundException ex) {
            System.out.println(ex);
        }

        System.out.println("Good bye!");
    }
}