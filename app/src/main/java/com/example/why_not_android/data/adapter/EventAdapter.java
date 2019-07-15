package com.example.why_not_android.data.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.why_not_android.R;
import com.example.why_not_android.data.Models.Event;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private List<Event> eventList;
    private ItemClickListener itemClickListener;

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_event, viewGroup, false);
        return new EventViewHolder(view);
    }

    @Override public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {

        Event event = eventList.get(i);
        Log.d("bind",event.getName());
        eventViewHolder.nameTv.setText(event.getName());
        eventViewHolder.dateTv.setText(event.getDate());
        Glide.with(eventViewHolder.itemView).load(event.getImageURL().replace("localhost","10.0.2.2")).into(eventViewHolder.pictureImv);

        if (itemClickListener!= null){
            eventViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onclick(event);
                }
            });
        }
    }

    @Override public int getItemCount() {
        return eventList != null ? eventList.size() : 0;
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemImageView)
        ImageView pictureImv;
        @BindView(R.id.itemTextViewDate)
        TextView dateTv;
        @BindView(R.id.itemTextViewName) TextView nameTv;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
    public interface ItemClickListener{
        void onclick(Event event);
    }
}
