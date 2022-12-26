package edu.school21.chat.app;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.JdbcDataSource;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    public static void main(String[] args)  {
        JdbcDataSource dataSource = new JdbcDataSource();
        updateData("schema.sql", dataSource);
        updateData("data.sql", dataSource);
        MessagesRepository repository = new MessagesRepositoryJdbcImpl(dataSource.getDataSource());
        createNewMessage(repository);
    }

    private static void createNewMessage(MessagesRepository repository) {
        User creator = new User(1L, "Kate", "123", new ArrayList<>(), new ArrayList<>());
        User author = creator;
        Chatroom room = new Chatroom(1L, "Chat1", creator, new ArrayList<>());
        Message message = new Message(null, author, room, "Hello from Kate!", LocalDateTime.now());

        System.out.println("---NEW MESSAGE---");
        try {
            repository.save(message);
            System.out.println("New message id = " + message.getId());
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("---AUTHOR IS EMPTY---");
        try {
            creator = new User(2L, "Jack", "123", null, null);
            author = null;
            room = new Chatroom(2L, "Chat2", creator, null);
            message = new Message(null, author, room, "Hello", LocalDateTime.now());
            repository.save(message);
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("---CHATROOM DOESN'T EXIST---");
        try {
            creator = new User(2L, "Wer", "123", null, null);
            author = creator;
            room = new Chatroom(21L, "Chat22", creator, null);
            message = new Message(null, author, room, "No Chatroom", LocalDateTime.now());
            repository.save(message);
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("---AUTHOR DOESN'T EXIST---");
        try {
            creator = new User(22L, "T22", "t22", null, null);
            author = creator;
            room = new Chatroom(2L, "Chat2", creator, null);
            message = new Message(null, author, room, "Unknown author", LocalDateTime.now());
            repository.save(message);
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("---ANOTHER MESSAGE FROM Kate---");
        try {
            creator = new User(1L, "Kate", "123", null, null);
            author = creator;
            room = new Chatroom(6L, "Chat1", creator, null);
            message = new Message(null, author, room, "Hello from Kate", LocalDateTime.now());
            repository.save(message);
            System.out.println("New message id = " + message.getId());
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }
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