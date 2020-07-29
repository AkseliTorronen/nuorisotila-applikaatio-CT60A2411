package com.example.harkka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;


import java.util.ArrayList;

public class ActivityViewEvent extends AppCompatActivity implements RecyclerAdapter.OnNoteListener {
    ArrayList<Event> eventList;
    RecyclerView.Adapter adapter;
    RecyclerView recycler;
    RecyclerView.LayoutManager manager;
    private Event event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        recycler = findViewById(R.id.recycleAll);
        eventList = ReadWriteXML.read(this);
        manager = new LinearLayoutManager(this);
        adapter = new RecyclerAdapter(eventList, this);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);


    }

    @Override
    public void onNoteClick(int pos) {
        event = eventList.get(pos);
        Intent intent = new Intent(ActivityViewEvent.this, ActivityOneEvent.class);
        intent.putExtra("event", event);
        startActivityForResult(intent, 1);
    }
}