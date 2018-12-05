package edu.fsu.cs.mobile.hw5.project2;

import java.sql.Timestamp;

public class Event {

    private String title;
    private String address;
    private String timestamp;

    public Event() {
    }

    public Event(String title, String address, String timestamp) {
        this.title = title;
        this.address = address;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
