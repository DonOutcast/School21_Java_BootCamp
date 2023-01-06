package models;

import orm.Constraints;
import orm.OrmColumn;
import orm.OrmColumnId;
import orm.OrmEntity;

@OrmEntity(table = "users")
public class User {
    @OrmColumnId(constraints = @Constraints(primaryKey = true, allowNull = false, unique = true))
    private Integer id;
    @OrmColumn(name = "FirstName", length = 30)
    private String firstName;
    @OrmColumn(name = "LastName", length = 30)
    private String lastName;
    @OrmColumn(name = "Age")
    private Integer age;

    public User(Integer id, String firstName, String lastName, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
