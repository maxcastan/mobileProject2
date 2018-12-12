package edu.fsu.cs.mobile.hw5.project2;

import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.Date;

import java.sql.Time;
import java.sql.Timestamp;

public class Event {

    private String title;
    private String address;
    private com.google.firebase.Timestamp timestamp;

    public Event() {
    }

    public Event(String title, String address, com.google.firebase.Timestamp timestamp) {//constructs the event object
        this.title = title;
        this.address = address;
        this.timestamp = timestamp;
    }

    //getters to return objects values
    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public com.google.firebase.Timestamp getTimestamp() {
        return timestamp;
    }
}
