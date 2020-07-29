package com.example.harkka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityOneEvent extends AppCompatActivity {
    TextView nameOfEvent, eventDescription, eventAudience, location, timeSTART, timeEND, setOnGoing;
    Event event;
    EditText feedbackInput;
    Button submit;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_event);
        event = (Event) getIntent().getSerializableExtra("event");
        System.out.println(event.name);

        nameOfEvent = findViewById(R.id.eventName);
        eventDescription = findViewById(R.id.description);
        eventAudience = findViewById(R.id.ageGroup);
        location = findViewById(R.id.venue);
        timeSTART = findViewById(R.id.datetimeSTART);
        timeEND = findViewById(R.id.datetimeEND);
        setOnGoing = findViewById(R.id.onGoingText);

        nameOfEvent.setText(event.name);
        eventDescription.setText(event.description);
        eventAudience.setText(event.ageGroup);
        location.setText(event.venue);
        timeSTART.setText(event.datetime);
        timeEND.setText(event.datetimeEND);
        context = getApplicationContext();

        if (event.onGoing.equals("FALSE")){ //Checking if the event hasn't yet started.
            setOnGoing.setText("Event hasn't yet started.");
        } else if (event.onGoing.equals("TRUE") && event.past.equals("TRUE")) { //Checking if the event has already ended.
            setOnGoing.setText("Event has ended! Would you still like to leave feedback?");
            setOnGoing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submit = findViewById(R.id.buttonSubmit);
                    submit.setVisibility(View.VISIBLE);
                    feedbackInput = findViewById(R.id.feedback);
                    feedbackInput.setVisibility(View.VISIBLE);

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Feedback feed = new Feedback(feedbackInput.getText().toString(), event.name);
                            ReadWriteXML.writeFeedback(feed, context);
                            Intent intent = new Intent(ActivityOneEvent.this, ActivityMainMenu.class);
                            startActivityForResult(intent, 1);
                        }
                    });
                }
            });
        } else if (event.onGoing.equals("TRUE")){ //Checking if the event is currently in progress.
            setOnGoing.setText("Event is currently in progress! Would you like to leave feedback?");
            setOnGoing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submit = findViewById(R.id.buttonSubmit);
                    submit.setVisibility(View.VISIBLE);
                    feedbackInput = findViewById(R.id.feedback);
                    feedbackInput.setVisibility(View.VISIBLE);

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Feedback feed = new Feedback(feedbackInput.getText().toString(), event.name);
                            ReadWriteXML.writeFeedback(feed, context);
                            Intent intent = new Intent(ActivityOneEvent.this, ActivityMainMenu.class);
                            startActivityForResult(intent, 1);
                        }
                    });
                }
            });
        }
    }
}