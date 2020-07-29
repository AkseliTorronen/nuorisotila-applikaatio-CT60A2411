package com.example.harkka;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Adapter for the activity for events in progress.
public class OnGoingAdapter extends RecyclerView.Adapter<OnGoingAdapter.ViewHolder> {
    ArrayList<Event> onGoingEventList;
    OnGoingAdapter.OnNoteListener mOnNoteListener;

    public OnGoingAdapter(ArrayList<Event> list, OnGoingAdapter.OnNoteListener onNoteListener) {
        onGoingEventList = list;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override //Creating the view holder for the adapter.
    public OnGoingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new OnGoingAdapter.ViewHolder(v, mOnNoteListener);
    }

    @Override //Setting the text fields in the view holder.
    public void onBindViewHolder(@NonNull OnGoingAdapter.ViewHolder holder, int position) {
        holder.eventName.setText(onGoingEventList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if(onGoingEventList == null){
            return 0;
        } else {
            return onGoingEventList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView eventName;
        OnGoingAdapter.OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnGoingAdapter.OnNoteListener onNoteListener) {
            super(itemView);

            eventName = (TextView) itemView.findViewById(R.id.nameView);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener{
        void onNoteClick(int pos);
    }
}

