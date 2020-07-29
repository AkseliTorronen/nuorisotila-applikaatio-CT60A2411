package com.example.harkka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;

import com.example.harkka.ActivityEditEvent;
import com.example.harkka.Event;
import com.example.harkka.R;
import com.example.harkka.ReadWriteXML;
import com.example.harkka.RecyclerAdapter;

import java.util.ArrayList;

public class ActivityViewOnGoing extends AppCompatActivity implements OnGoingAdapter.OnNoteListener {
    ArrayList<Event> eventList;
    RecyclerView.Adapter adapter1;
    RecyclerView recycler1;
    RecyclerView.LayoutManager manager1;
    private Event event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_on_going);
        recycler1 = findViewById(R.id.recycleAll);
        eventList = ReadWriteXML.read(this);

        manager1 = new LinearLayoutManager(this);
        adapter1 = new OnGoingAdapter(eventList, this);
        recycler1.setLayoutManager(manager1);
        recycler1.setAdapter(adapter1);

    }

    @Override
    public void onNoteClick(int pos) {
        event = eventList.get(pos);
        Intent intent = new Intent(ActivityViewOnGoing.this, ActivityEnd.class);
        intent.putExtra("eventIndex", pos);
        intent.putExtra("event", event);
        startActivityForResult(intent, 1);
    }
}