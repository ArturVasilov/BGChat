package ru.androidacademy.bgchat.model;

public class Message {
    private String id;
    private String message;
    private String author;
    private String authorName;

    public Message(String id, String message, String author, String authorName) {
        this.id = id;
        this.message = message;
        this.author = author;
        this.authorName = authorName;
    }

    public Message() {
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
