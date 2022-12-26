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
import java.util.Optional;
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
        long id = 1L;
        User Kate = new User(1L, "Kate", "123", new ArrayList<>(), new ArrayList<>());
        User Jack = new User(2L, "Jack", "123", new ArrayList<>(), new ArrayList<>());
        User Wer = new User(3L, "Wer", "123", new ArrayList<>(), new ArrayList<>());
        Chatroom room = new Chatroom(2L, "Chat2", Jack, new ArrayList<>());
        Message message;

        System.out.println("---UPDATE MESSAGES---");
        try {
            Optional<Message> optMessage = repository.findById(id);

            if (optMessage.isPresent()) {
                message = optMessage.get();
                System.out.println("MESSAGE BEFORE UPDATE:");
                System.out.println(message);
                message.setAuthor(Jack);
                message.setRoom(room);
                message.setText("New message from Tom!");
                message.setLocalDateTime(null);
                System.out.println("MESSAGE AFTER UPDATE:");
                repository.update(message);
                System.out.println(repository.findById(message.getId()).get());
            }
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }

        try {
            id = 2L;
            Optional<Message> optMessage = repository.findById(id);
            room = new Chatroom(3L, "Chat3", Wer, new ArrayList<>());

            if (optMessage.isPresent()) {
                message = optMessage.get();
                System.out.println("MESSAGE BEFORE UPDATE:");
                System.out.println(message);
                message.setAuthor(Wer);
                message.setRoom(room);
                message.setText("Nick is a developer))");
                message.setLocalDateTime(LocalDateTime.now().minusDays(10));
                System.out.println("MESSAGE AFTER UPDATE:");
                repository.update(message);
                System.out.println(repository.findById(message.getId()).get());
            }
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }

        try {
            id = 4L;
            Optional<Message> optMessage = repository.findById(id);

            if (optMessage.isPresent()) {
                message = optMessage.get();
                System.out.println("MESSAGE BEFORE UPDATE:");
                System.out.println(message);
                message.setAuthor(Kate);
                message.setRoom(room);
                message.setText("Hello! How are you?");
                message.setLocalDateTime(LocalDateTime.now().minusDays(100));
                System.out.println("MESSAGE AFTER UPDATE:");
                repository.update(message);
                System.out.println(repository.findById(message.getId()).get());
            }
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