package com.example.harkka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class ActivityViewFeedback extends AppCompatActivity {
    ArrayList<Feedback> FeedbackList;
    RecyclerView.Adapter adapter;
    RecyclerView recycler;
    RecyclerView.LayoutManager manager;
    private Feedback feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);
            recycler = findViewById(R.id.recycleAll);
            FeedbackList = ReadWriteXML.readFeedback(this);

            manager = new LinearLayoutManager(this);
            adapter = new FeedbackAdapter(FeedbackList);
            recycler.setLayoutManager(manager);
            recycler.setAdapter(adapter);
    }
}