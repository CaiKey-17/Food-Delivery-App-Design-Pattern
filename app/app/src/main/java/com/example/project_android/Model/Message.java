package com.example.project_android.Model;

public class Message {
    private String user;
    private String message;
    private String timestamp;

    public Message() {
        // Default constructor required for calls to DataSnapshot.getValue(Message.class)
    }

    public Message(String user, String message, String timestamp) {
        this.user = user;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getUser() { return user; }
    public String getMessage() { return message; }
    public String getTimestamp() { return timestamp; }
}
