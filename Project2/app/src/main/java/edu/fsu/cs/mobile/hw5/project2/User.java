package edu.fsu.cs.mobile.hw5.project2;

import java.sql.Timestamp;


public class User {

    private String userEmail;
    private String userName;

    public User(){
        //empty constructor needed for firebase
    }

    public User(String userEmail, String userName){
        this.userEmail = userEmail;
        this.userName = userName;
    }

    //this must be the name of the method for firebase to work properly
    public String getUserEmail(){
        return userEmail;
    }

    //same applies here
    public String getUserName(){
        return userName;
    }
}
