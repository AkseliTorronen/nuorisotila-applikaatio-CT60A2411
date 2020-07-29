package com.example.harkka;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityMainMenu extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    Button out, settings, feedButton;
    Button edit;
    Button create;
    Button all, onGoing;
    String organizer = "";
    DatabaseReference reference;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        if(firebaseAuth.getCurrentUser() != null) {
            userID = firebaseAuth.getCurrentUser().getUid();
            System.out.println("################\n UID \n#####################");
        }

        //Getting the value for "organizer" from firebase database to differentiate between an organizer account and an attendee
        if(firebaseAuth.getCurrentUser() != null){
            reference = database.getReference("Users");
            reference.orderByChild(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    organizer = dataSnapshot.child(userID + "/organizer").getValue().toString();
                    if(organizer.equals("true")) {
                        edit = findViewById(R.id.eventEdit);
                        edit.setVisibility(View.VISIBLE);
                        edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadActivity("EDIT");
                            }
                        });
                        create = findViewById(R.id.eventCre);
                        create.setVisibility(View.VISIBLE);
                        create.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadActivity("CREATE");
                            }
                        });
                        onGoing = findViewById(R.id.eventsInProgress);
                        onGoing.setVisibility(View.VISIBLE);
                        onGoing.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadActivity("ONGOING");
                            }
                        });
                        feedButton = findViewById(R.id.feedbackButton);
                        feedButton.setVisibility(View.VISIBLE);
                        feedButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadActivity("FEED");
                            }
                        });
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        }

        all = findViewById(R.id.allEvents);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadActivity("VIEW");
            }
        });
        settings = findViewById(R.id.settingsButton);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadActivity("SETTINGS");
            }
        });
        out = findViewById(R.id.logOut);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadActivity("OUT");
            }
        });

    }
    public void loadActivity(String s){
        if(s.equals("OUT")){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ActivityMainMenu.this, MainActivity.class);
            startActivity(intent);
        } else if(s.equals("EDIT")){
            Intent intent = new Intent(ActivityMainMenu.this, ActivityViewAllEvents.class);
            startActivityForResult(intent, 1);
        } else if(s.equals("CREATE")){
            Intent intent = new Intent(ActivityMainMenu.this, ActivityCreateEvent.class);
            startActivityForResult(intent, 1);
        } else if(s.equals("VIEW")){
            Intent intent = new Intent(ActivityMainMenu.this, ActivityViewEvent.class);
            startActivityForResult(intent, 1);
        } else if(s.equals("SETTINGS")){
            Intent intent = new Intent(ActivityMainMenu.this, ActivitySettings.class);
            startActivityForResult(intent, 1);
        } else if(s.equals("ONGOING")){
            Intent intent = new Intent(ActivityMainMenu.this, ActivityViewOnGoing.class);
            startActivityForResult(intent, 1);
        } else if(s.equals("FEED")){
            Intent intent = new Intent(ActivityMainMenu.this, ActivityViewFeedback.class);
            startActivityForResult(intent, 1);
        }

    }
}
