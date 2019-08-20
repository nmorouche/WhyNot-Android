package com.example.why_not_android.data.Models;

public class Message {
    private String text;
    private boolean belongsToCurrentUser;

    public Message(String text, boolean belongsToCurrentUser) {
        this.text = text;
        this.belongsToCurrentUser = belongsToCurrentUser;
    }

    public String getText() {
        return text;
    }

    public boolean isBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }
}

