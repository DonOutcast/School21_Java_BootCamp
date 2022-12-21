import java.util.Objects;

public class UsersArrayList implements UserList {
    private User[] users;
    private int size;
    private int numOfUsers;

    public UsersArrayList() {
        this.users = new User[10];
        this.size = 10;
        this.numOfUsers = 0;
    }


    @Override
    public void addUser(User newUser) {
        if (this.numOfUsers == this.size) {
            User[] newUsers = new User[size * 2];
            for (int i = 0; i < this.size; i++) {
                newUsers[i] = this.users[i];
            }
            this.size = this.size * 2;
            this.users = newUsers;
        }
        this.users[numOfUsers++] = newUser;
    }

    @Override
    public User getUserById(Integer id) {
        for (int i = 0; i < this.numOfUsers; i++) {
            if (Objects.equals(users[i].getId(), id)) {
                return users[i];
            }
        }
        throw new UserNotFoundException("User with id " + id + " not found");
    }


    @Override
    public User getUserByIndex(Integer index) {
        if (index < this.numOfUsers && index >= 0) {
            return users[index];
        }
        throw new UserNotFoundException("User with index " + index + " not found");
    }

    @Override
    public Integer getUserCount() {
        return this.numOfUsers;
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("UsersList: number of users: " + this.numOfUsers + " size " + this.size + "\n");
        for (int i = 0; i < this.numOfUsers; i++) {
            s.append(users[i]).append("\n");
        }
        return s.toString();
    }

}
