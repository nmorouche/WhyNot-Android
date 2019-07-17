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
import com.example.why_not_android.data.Models.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {
    private List<User> userList;
    private ItemClickListener itemClickListener;

    public void setEventList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_match, viewGroup, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder matchViewHolder, int i) {

        User user = userList.get(i);
        matchViewHolder.nameTv.setText(user.getUsername());
        matchViewHolder.dateTv.setText(user.getBirthdate());
        Glide.with(matchViewHolder.itemView).load(user.getPhoto().replace("localhost", "10.0.2.2")).into(matchViewHolder.pictureImv);

        if (itemClickListener != null) {
            matchViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onclick(user);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    public void setItemClickListener(EventAdapter.ItemClickListener itemClickListener) {
    }

    class MatchViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_match_image)
        ImageView pictureImv;
        @BindView(R.id.item_match_username)
        TextView dateTv;
        @BindView(R.id.item_match_gender)
        TextView nameTv;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface ItemClickListener {
        void onclick(User user);
    }
}
