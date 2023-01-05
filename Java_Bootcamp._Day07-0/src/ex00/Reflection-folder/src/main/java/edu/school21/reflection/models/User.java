package edu.school21.reflection.models;

public class User {
    private String firstName;
    private String lastName;
    private int age;
    private int height;

    public User(String firstName, String lastName, int age, int height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.height = height;
    }

    public User() {
    }

    public int grow(int value) {
        this.height += value;
        return height;
    }

    public int getOlder(int value) {
        this.age += value;
        return age;
    }

    public String newValues(String firstName, String lastName, int age, int height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.height = height;
        return this.toString();
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", height=" + height +
                '}';
    }
}

