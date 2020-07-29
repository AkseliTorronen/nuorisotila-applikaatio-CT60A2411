package com.example.harkka;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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

import static com.example.harkka.ReadWriteXML.modifyXML;

public class ActivityEditEvent extends AppCompatActivity {
    Event oldEvent, newEvent;
    Button dateB, timeB, dateENDB, timeENDB, doneButton, start, delete, addPart;
    TextView dateW, timeW, dateENDW, timeENDW, part, textView;
    String date, time, datetime, dateEND, timeEND, datetimeEND;
    EditText name, description, venue;
    RadioGroup ageRadioGroup;
    RadioButton ageG;
    String ageGroup, nameOfEvent, eventDescription, eventVenue;
    int ageGroupID, participants, position;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        oldEvent = (Event) getIntent().getSerializableExtra("event");
        position = getIntent().getIntExtra("eventIndex", 0);
        System.out.println(oldEvent.name);
        context = this;

        name = findViewById(R.id.eventName);
        description = findViewById(R.id.textDescription);
        venue = findViewById(R.id.venueInput);
        ageRadioGroup = findViewById(R.id.radioGroup);
        addPart = findViewById(R.id.partButton);
        part = findViewById(R.id.textView10);

        dateB = findViewById(R.id.dateButton);
        timeB = findViewById(R.id.timeButton);
        dateW = findViewById(R.id.dateView);
        timeW = findViewById(R.id.timeView);

        dateENDB = findViewById(R.id.dateButtonEND);
        timeENDB = findViewById(R.id.timeButtonEND);
        dateENDW = findViewById(R.id.dateViewEND);
        timeENDW = findViewById(R.id.timeViewEND);
        setTextFields();
        //Checking if event has already passed and setting the buttons invisible in case it has.
        if (oldEvent.past.equals("TRUE")){
            doneButton = findViewById(R.id.buttonDone);
            doneButton.setVisibility(View.GONE);
            start = findViewById(R.id.buttonStart);
            start.setVisibility(View.GONE);
            textView = findViewById(R.id.textWindow);
            textView.setVisibility(View.VISIBLE);
            textView.setText("Event has already passed, you cannot edit it anymore!");
        }

        //Buttons for setting time
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

        //Setting the amount of participants.
        addPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                participants++;
                part.setText("Participants: " + participants);
            }
        });
        //Setting up the "done" button.
        doneButton = findViewById(R.id.buttonDone);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = findViewById(R.id.eventName);
                nameOfEvent = name.getText().toString();
                eventDescription = description.getText().toString();
                eventVenue = venue.getText().toString();
                if (date.isEmpty()) {
                    Toast.makeText(ActivityEditEvent.this, "Enter date!", Toast.LENGTH_SHORT).show();
                } else if (time.isEmpty()) {
                    Toast.makeText(ActivityEditEvent.this, "Enter time!", Toast.LENGTH_SHORT).show();
                } else if (dateEND.isEmpty()) {
                    Toast.makeText(ActivityEditEvent.this, "Enter end date!", Toast.LENGTH_SHORT).show();
                } else if (timeEND.isEmpty()) {
                    Toast.makeText(ActivityEditEvent.this, "Enter end time!", Toast.LENGTH_SHORT).show();
                } else {
                    ageG = findViewById(ageRadioGroup.getCheckedRadioButtonId());
                    ageGroup = (String) ageG.getText();
                    datetime = date + ";" + time;
                    datetimeEND = dateEND + ";" + timeEND;
                    newEvent = new Event(nameOfEvent, eventVenue, ageGroup, eventDescription, datetime, ageGroupID, datetimeEND, "FALSE", participants, "FALSE");
                    modifyXML(context, position, newEvent);

                    Intent intent = new Intent(ActivityEditEvent.this, ActivityMainMenu.class);
                    startActivityForResult(intent, 1);
                }
            }
        });
        //Setting up the start button.
        start = findViewById(R.id.buttonStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = findViewById(R.id.eventName);
                nameOfEvent = name.getText().toString();
                eventDescription = description.getText().toString();
                eventVenue = venue.getText().toString();
                if (date.isEmpty()) {
                    Toast.makeText(ActivityEditEvent.this, "Enter date!", Toast.LENGTH_SHORT).show();
                } else if (time.isEmpty()) {
                    Toast.makeText(ActivityEditEvent.this, "Enter time!", Toast.LENGTH_SHORT).show();
                } else if (dateEND.isEmpty()) {
                    Toast.makeText(ActivityEditEvent.this, "Enter end date!", Toast.LENGTH_SHORT).show();
                } else if (timeEND.isEmpty()) {
                    Toast.makeText(ActivityEditEvent.this, "Enter end time!", Toast.LENGTH_SHORT).show();
                } else {
                    ageG = findViewById(ageRadioGroup.getCheckedRadioButtonId());
                    ageGroup = (String) ageG.getText();
                    datetime = date + ";" + time;
                    datetimeEND = dateEND + ";" + timeEND;
                    newEvent = new Event(nameOfEvent, eventVenue, ageGroup, eventDescription, datetime, ageGroupID, datetimeEND, "TRUE", participants, "FALSE");

                    modifyXML(context, position,newEvent);

                    Intent intent = new Intent(ActivityEditEvent.this, ActivityMainMenu.class);
                    startActivity(intent);
                }
            }
        });
        //Setting up the delete button.
        delete = findViewById(R.id.buttonDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();

                Intent intent = new Intent(ActivityEditEvent.this, ActivityMainMenu.class);
                startActivityForResult(intent, 1);
            }
        });
    }
    //Setting the event starting time.
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
    //Setting the event starting date.
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
    //Setting the event ending time.
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
    //Setting the event ending date.
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

    //Checking the radio group and setting the value to age group variable.
    public void check(View v){
        int radioId = ageRadioGroup.getCheckedRadioButtonId();
        ageG = findViewById(radioId);
        ageGroup = (String) ageG.getText();
        ageGroupID = ageRadioGroup.indexOfChild(findViewById(ageRadioGroup.getCheckedRadioButtonId()));
    }

    //Setting text fields to be edited.
    public void setTextFields(){
        name.setText(oldEvent.name);
        description.setText(oldEvent.description);
        venue.setText(oldEvent.venue);
        this.datetime = oldEvent.datetime;
        this.datetimeEND = oldEvent.datetimeEND;
        String[] dateParts = datetime.split(";");
        String[] datePartsEND = datetimeEND.split(";");

        dateW.setText(dateParts[0]);
        date = dateParts[0];
        timeW.setText(dateParts[1]);
        time = dateParts[1];
        dateENDW.setText(datePartsEND[0]);
        dateEND = datePartsEND[0];
        timeENDW.setText(datePartsEND[1]);
        timeEND = datePartsEND[1];
        part.setText("Participants: " + oldEvent.participants);
    }

    public void delete(){
        ReadWriteXML.delete(this, position);
    }
}
