package edu.school21.chat.app;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.*;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args)  {
        JdbcDataSource dataSource = new JdbcDataSource();
        updateData("schema.sql", dataSource);
        updateData("data.sql", dataSource);
        UsersRepository repository = new UsersRepositoryJdbcImpl(dataSource.getDataSource());
        findAllUsers(repository);
    }

    private static void findAllUsers(UsersRepository repository) {
        List<User> users = repository.findAll(0, 2);
        System.out.println("LIST OF ALL USERS FROM PAGE=0 SIZE=2:");
        users.forEach(System.out::println);

        users = repository.findAll(1, 3);
        System.out.println("\nLIST OF ALL USERS FROM PAGE=1 SIZE=3:");
        users.forEach(System.out::println);

        users = repository.findAll(3, 5);
        System.out.println("\nLIST OF ALL USERS FROM PAGE=3 SIZE=5:");
        users.forEach(System.out::println);

        users = repository.findAll(4, 10);
        System.out.println("\nLIST OF ALL USERS FROM PAGE=4 SIZE=10:");
        users.forEach(System.out::println);
        System.out.println("---MUST BE EMPTY---");
    }

    private static void updateData(String file, JdbcDataSource dataSource) {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            InputStream input = Program.class.getClassLoader().getResourceAsStream(file);
            Scanner scanner = new Scanner(input).useDelimiter(";");

            while (scanner.hasNext()) {
                st.executeUpdate(scanner.next().trim());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}