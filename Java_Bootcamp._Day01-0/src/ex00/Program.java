public class Program {
     public static void main(String[] args) {
        User user1 = new User(1, "John", 0);
        User user2 = new User(2, "Kate", 200);
        User user3 = new User(3, "Tom", 500);
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);

        Transaction t1 = new Transaction(user1, user2, Transaction.Category.CREDIT,-100);
        System.out.println(t1);
        Transaction t2 = new Transaction(user3, user1, Transaction.Category.DEBIT,200);
        System.out.println(t2);
    }
}
