public class User {
    private Integer Identifier;
    private String Name;
    private Integer Balance;

    public User(Integer identifier, String name, Integer balance) {
        Identifier = identifier;
        Name = name;
        Balance = balance;
    }

    public Integer getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(Integer identifier) {
        Identifier = identifier;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getBalance() {
        return Balance;
    }

    public void setBalance(Integer balance) {
        if (balance >= 0) {
            Balance = balance;
        } else {
            Balance = 0;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "Identifier=" + Identifier +
                ", Name='" + Name + '\'' +
                ", Balance=" + Balance +
                '}';
    }
}
