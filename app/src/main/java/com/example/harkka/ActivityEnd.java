package com.example.harkka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.harkka.ReadWriteXML.modifyXML;

public class ActivityEnd extends AppCompatActivity {
    Event currentEvent;
    TextView name, desc, mText;
    Button end;
    Context context;
    int indexOfEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        currentEvent = (Event) getIntent().getSerializableExtra("event");
        indexOfEvent = getIntent().getIntExtra("eventIndex", 0);
        name = findViewById(R.id.nameBox);
        desc = findViewById(R.id.descriptionBox);
        context = getApplicationContext();
        mText = findViewById(R.id.text1);
        name.setText(currentEvent.name);
        desc.setText(currentEvent.description);

        //Checking if the event is currently in progress
        if(currentEvent.onGoing.equals("TRUE") && currentEvent.past.equals("FALSE")) {
            mText.setText("Do you want to end this event?");
            end = findViewById(R.id.buttonEnd);
            end.setVisibility(View.VISIBLE);
            end.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Event endedEvent = new Event(currentEvent.name, currentEvent.venue, currentEvent.ageGroup, currentEvent.description, currentEvent.datetime, currentEvent.ageGroupID, currentEvent.datetimeEND, currentEvent.onGoing, currentEvent.participants, "TRUE");
                    modifyXML(context, indexOfEvent, endedEvent);
                    Intent intent = new Intent(ActivityEnd.this, ActivityMainMenu.class);
                    startActivity(intent);
                }
            });
        } else {
            mText.setText("This event hasn't yet started or has already passed!");
        }

    }
}