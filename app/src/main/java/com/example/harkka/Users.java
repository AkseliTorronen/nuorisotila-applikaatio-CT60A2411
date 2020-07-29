package com.example.harkka;

public class Users {
    private String organizer;
    private String username;
    private String userID;

    public Users(String organizer, String userN, String uID){
        this.organizer = organizer;
        this.username = userN;
        this.userID = uID;
    }
    public String getOrganizer(){
        return organizer;
    }

    public String getUsername(){
        return username;
    }

    public String getUserID(){
        return userID;
    }
}
