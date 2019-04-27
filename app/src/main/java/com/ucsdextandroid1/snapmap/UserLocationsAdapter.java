package com.ucsdextandroid1.snapmap;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rjaylward on 2019-04-26
 */
public class UserLocationsAdapter extends RecyclerView.Adapter<UserLocationCardViewHolder> {

    private List<UserLocationData> items = new ArrayList<>();

    private UserLocationCardViewHolder.LocationCardClickListener clickListener;

    public void setItems(List<UserLocationData> locationData) {
        items.clear();
        items.addAll(locationData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserLocationCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return UserLocationCardViewHolder
                .inflate(parent)
                .setClickListener(clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserLocationCardViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setClickListener(UserLocationCardViewHolder.LocationCardClickListener clickListener) {
        this.clickListener = clickListener;
        notifyDataSetChanged();
    }

}
