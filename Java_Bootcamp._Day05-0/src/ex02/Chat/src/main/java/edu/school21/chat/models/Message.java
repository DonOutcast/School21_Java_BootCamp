package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message {
    private Long id;
    private User author;
    private Chatroom room;
    private String text;
    private LocalDateTime localDateTime;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public Message(Long id, User author, Chatroom room, String text, LocalDateTime localDateTime) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.localDateTime = localDateTime;
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, room, text, localDateTime);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Message message = (Message) obj;
        return Objects.equals(id, message.id) && Objects.equals(author, message.author)
                && Objects.equals(room, message.room) && Objects.equals(text, message.text)
                && Objects.equals(localDateTime, message.localDateTime);
    }

    @Override
    public String toString() {
        return "Message {\n"
                + "id=" + id + ",\n"
                + "author=" + author + ",\n"
                + "room=" + room + ",\n"
                + "text='" + text + "',\n"
                + "localDateTime=" + localDateTime.format(FORMATTER) + "\n"
                + "}";
    }
}
