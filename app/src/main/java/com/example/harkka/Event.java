package com.example.harkka;

import java.io.Serializable;

public class Event implements Serializable {
    String name;
    String venue;
    String ageGroup;
    String description;
    String datetime;
    String datetimeEND;
    int ageGroupID, participants;
    String onGoing;
    String past;
    Event(String n, String v, String aG, String d, String dT, int iD, String dTEND, String oG, int part, String past){
        name = n;
        venue = v;
        ageGroup = aG;
        description = d;
        datetime = dT;
        datetimeEND = dTEND;
        ageGroupID = iD;
        onGoing = oG;
        participants = part;
        this.past = past;
    }


    public String getName() {
        return name;
    }
}
