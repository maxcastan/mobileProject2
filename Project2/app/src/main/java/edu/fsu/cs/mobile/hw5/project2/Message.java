package edu.fsu.cs.mobile.hw5.project2;

import java.sql.Timestamp;

public class Message {

    private String message;
    private String name;

    public Message(){}

    public Message(String message, String name) {
        this.message = message;
        this.name = name;
    }


    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }
}
