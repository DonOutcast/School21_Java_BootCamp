package edu.school21.chat.repositories;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private DataSource dataSource;
    private final String uQuery = "SELECT * FROM chat.user WHERE id = ";
    private final String cQuery = "SELECT * FROM chat.chatroom WHERE id = ";
    private final String mQuery = "SELECT * FROM chat.message WHERE id = ";
    private final String upQuery = "UPDATE chat.message SET author = ?, room = ?,"
            + " text = ?, localDateTime = ? WHERE id = ?";

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void update(Message message) {
        checkMessage(message);
        Timestamp localDateTime = null;

        try (Connection con = dataSource.getConnection()) {
            PreparedStatement st = con.prepareStatement(mQuery + message.getId());
            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                save(message);
                return ;
            }

            if (message.getText() == null) {
                message.setText("");
            }

            if (message.getLocalDateTime() != null) {
                localDateTime = Timestamp.valueOf(message.getLocalDateTime());
            }
            st = con.prepareStatement(upQuery);
            st.setLong(1, message.getAuthor().getId());
            st.setLong(2, message.getRoom().getId());
            st.setString(3, message.getText());
            st.setTimestamp(4, localDateTime);
            st.setLong(5, message.getId());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(Message message) {
        Long userId, roomId;

        checkMessage(message);
        String localDateTime = "'null'";
        userId = message.getAuthor().getId();
        roomId = message.getRoom().getId();

        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            if (message.getLocalDateTime() != null) {
                localDateTime = "'" + Timestamp.valueOf(message.getLocalDateTime()) + "'";
            }

            if (message.getText() == null) {
                message.setText("");
            }
            ResultSet rs = st.executeQuery("INSERT INTO chat.message (author, room, text, localDateTime) VALUES ("
                    + userId + ", " + roomId + ", '" + message.getText() + "', " + localDateTime + ") RETURNING id");

            if (!rs.next()) {
                throw new NotSavedSubEntityException("Internal Error");
            }
            message.setId(rs.getLong(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void checkMessage(Message message) {
        if (message.getAuthor() == null || message.getAuthor().getId() == null) {
            throw new NotSavedSubEntityException("Author doesn't exist");
        }

        if (message.getRoom() == null || message.getRoom().getId() == null) {
            throw new NotSavedSubEntityException("Chatroom doesn't exist");
        }

        if (message.getRoom().getOwner() == null || message.getRoom().getOwner().getId() == null) {
            throw new NotSavedSubEntityException("Chatroom creator doesn't exist");
        }

        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            Long userId = message.getAuthor().getId();
            Long roomId = message.getRoom().getId();
            ResultSet rs = st.executeQuery(uQuery + userId);

            if (!rs.next()) {
                throw new NotSavedSubEntityException("User with id = " + userId + " doesn't exist");
            }
            rs = st.executeQuery(cQuery + roomId);

            if (!rs.next()) {
                throw new NotSavedSubEntityException("Chatroom with id = " + roomId + " doesn't exist");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<Message> findById(Long id) {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(mQuery + id);

            if (!rs.next()) {
                return null;
            }
            Long userId = rs.getLong(2);
            Long roomId = rs.getLong(3);
            User user = findUser(userId);
            Chatroom room = findChat(roomId);
            LocalDateTime localDateTime = rs.getTimestamp(5) == null
                    ? null : rs.getTimestamp(5).toLocalDateTime();
            return Optional.of(new Message(rs.getLong(1), user, room,
                    rs.getString(4), localDateTime));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    private User findUser(Long id) throws SQLException {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(uQuery + id);

            if (!rs.next()) {
                return null;
            }
            return new User(id, rs.getString(2), rs.getString(3));
        }
    }

    private Chatroom findChat(Long id) throws SQLException {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(cQuery + id);

            if (!rs.next()) {
                return null;
            }
            return new Chatroom(id, rs.getString(2));
        }
    }
}
