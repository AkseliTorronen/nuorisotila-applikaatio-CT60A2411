package com.example.harkka;

public class Feedback {
    String feed;
    String event;

    public Feedback(String feed, String event) {
        this.feed = feed;
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public String getFeed() {
        return feed;
    }
}
