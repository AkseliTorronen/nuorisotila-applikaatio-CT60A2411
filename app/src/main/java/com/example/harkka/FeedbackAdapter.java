package com.example.harkka;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> { //Recycler view adapter for the feedback.
    ArrayList<Feedback> FeedbackList;

    public FeedbackAdapter(ArrayList<Feedback> list) {
        FeedbackList = list;
    }

    @NonNull
    @Override //Creating the view holder for the adapter.
    public FeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row2, parent, false);
        return new FeedbackAdapter.ViewHolder(v);
    }

    @Override //Setting the text fields in the view holder.
    public void onBindViewHolder(@NonNull FeedbackAdapter.ViewHolder holder, int position) {
        holder.eventName.setText(FeedbackList.get(position).getEvent());
        holder.feedback.setText(FeedbackList.get(position).getFeed());
    }

    @Override
    public int getItemCount() {
        if(FeedbackList == null){
            return 0;
        } else {
            return FeedbackList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView eventName, feedback;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName = (TextView) itemView.findViewById(R.id.nameView);
            feedback = (TextView) itemView.findViewById(R.id.feedbackView);

        }
    }
}
