package com.example.harkka;

import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ActivityCreateEvent extends AppCompatActivity {
    Button dateB, timeB, dateENDB, timeENDB, doneButton;
    TextView dateW, timeW, dateENDW, timeENDW;
    String date, time, datetime, dateEND, timeEND, datetimeEND;
    EditText name, description, venue;
    RadioGroup ageRadioGroup;
    RadioButton ageG;
    String ageGroup, nameOfEvent, eventDescription, eventVenue;
    int ageGroupID;
    Event newEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        name = findViewById(R.id.eventName);
        description = findViewById(R.id.textDescription);
        venue = findViewById(R.id.venueInput);
        ageRadioGroup = findViewById(R.id.radioGroup);

        dateB = findViewById(R.id.dateButton);
        timeB = findViewById(R.id.timeButton);
        dateW = findViewById(R.id.dateView);
        timeW = findViewById(R.id.timeView);

        dateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSet();
            }
        });

        timeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSet();
            }
        });

        dateENDB = findViewById(R.id.dateButtonEND);
        timeENDB = findViewById(R.id.timeButtonEND);
        dateENDW = findViewById(R.id.dateViewEND);
        timeENDW = findViewById(R.id.timeViewEND);

        dateENDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSetEND();
            }
        });

        timeENDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSetEND();
            }
        });

        //Setting up the done button.
        doneButton = findViewById(R.id.buttonDone);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameOfEvent = name.getText().toString();
                eventDescription = description.getText().toString();
                eventVenue = venue.getText().toString();
                if (date.isEmpty()) {
                    Toast.makeText(ActivityCreateEvent.this, "Enter date!", Toast.LENGTH_SHORT).show();
                } else if (time.isEmpty()) {
                    Toast.makeText(ActivityCreateEvent.this, "Enter time!", Toast.LENGTH_SHORT).show();
                } else if (dateEND.isEmpty()) {
                    Toast.makeText(ActivityCreateEvent.this, "Enter end date!", Toast.LENGTH_SHORT).show();
                } else if (timeEND.isEmpty()) {
                    Toast.makeText(ActivityCreateEvent.this, "Enter end time!", Toast.LENGTH_SHORT).show();
                } else {
                    ageG = findViewById(ageRadioGroup.getCheckedRadioButtonId());
                    ageGroup = (String) ageG.getText();
                    datetime = date + ";" + time;
                    datetimeEND = dateEND + ";" + timeEND;
                    newEvent = new Event(nameOfEvent, eventVenue, ageGroup, eventDescription, datetime, ageGroupID, datetimeEND, "FALSE", 0, "FALSE");
                    writeX(newEvent);

                    Intent intent = new Intent(ActivityCreateEvent.this, ActivityMainMenu.class);
                    startActivityForResult(intent, 1);
                }
            }
        });
    }

    private void timeSet(){
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time = hourOfDay + ":" + minute;
                timeW.setText(time);
            }
        }, HOUR, MINUTE, true);

        timePickerDialog.show();
    }

    private void dateSet(){
        Calendar calendar = Calendar.getInstance();

        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "." + month + "." + year;
                dateW.setText(date);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();
    }
    private void timeSetEND(){
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeEND = hourOfDay + ":" + minute;
                timeENDW.setText(timeEND);
            }
        }, HOUR, MINUTE, true);

        timePickerDialog.show();
    }

    private void dateSetEND(){
        Calendar calendar = Calendar.getInstance();

        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateEND = dayOfMonth + "." + month + "." + year;
                dateENDW.setText(dateEND);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();
    }

    //Check the chosen age group
    public void check(View v){
        int radioId = ageRadioGroup.getCheckedRadioButtonId();
        ageG = findViewById(radioId);
        ageGroup = (String) ageG.getText();
        ageGroupID = ageRadioGroup.indexOfChild(findViewById(ageRadioGroup.getCheckedRadioButtonId()));
    }

    public void writeX(Event event){
        ReadWriteXML.write(event, this);
    }
}
