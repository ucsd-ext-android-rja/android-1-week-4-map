package com.ucsdextandroid1.snapmap;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by rjaylward on 2019-04-26
 */
public class UserLocationCardViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView titleView;
    private TextView subtitleView;

    private UserLocationData currentLocationData;
    private LocationCardClickListener listener;

    public static UserLocationCardViewHolder inflate(ViewGroup parent) {
        return new UserLocationCardViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_user_location_card, parent, false));
    }

    public UserLocationCardViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.vulc_image);
        titleView = itemView.findViewById(R.id.vulc_title);
        subtitleView = itemView.findViewById(R.id.vulc_subtitle);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null && currentLocationData != null && getAdapterPosition() != RecyclerView.NO_POSITION)
                    listener.onCardClick(currentLocationData, getAdapterPosition());
            }
        });
    }

    public void bind(UserLocationData locationData) {
        currentLocationData = locationData;

        imageView.setImageTintList(ColorStateList.valueOf(Color.parseColor(locationData.getColor())));
        imageView.setImageResource(R.drawable.ic_person_black_24dp);
        titleView.setText(locationData.getUserName());
        subtitleView.setText(locationData.getLocationName());
    }

    public UserLocationCardViewHolder setClickListener(LocationCardClickListener listener) {
        this.listener = listener;
        return this;
    }

    public interface LocationCardClickListener {
        void onCardClick(UserLocationData locationData, int index);
    }

}
