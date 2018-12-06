package edu.fsu.cs.mobile.hw5.project2;

import java.sql.Timestamp;


public class User {

    private String userName;
    private String houseName;

    public User(){
        //empty constructor needed for firebase
    }

    public User(String userName, String houseName){
        this.userName = userName;
        this.houseName = houseName;
    }

    //this must be the name of the method for firebase to work properly
    public String getUserName(){
        return userName;
    }

    //same applies here
    public String getHouseName(){
        return houseName;
    }
}
