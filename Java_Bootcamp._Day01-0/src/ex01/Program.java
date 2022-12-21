public class Program {
    public static void main(String[] args) {
        User user1 = new User("Sam", 500);
        User user2 = new User("Bob", 1000);
        User user3 = new User("Tom", 0);
        User user4 = new User("Ted", -200);
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);
        System.out.println(user4);
        System.out.println(UserIdsGenerator.getIdentifier());
        System.out.println(UserIdsGenerator.getIdentifier());
        User user5 = new User("Tim", 100);
        System.out.println(user5);
    }
}
