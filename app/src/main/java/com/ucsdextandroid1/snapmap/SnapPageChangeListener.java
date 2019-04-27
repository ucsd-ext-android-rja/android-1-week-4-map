package com.ucsdextandroid1.snapmap;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

/**
 * Created by rjaylward on 2019-04-27
 */
public class SnapPageChangeListener extends RecyclerView.OnScrollListener {

    private SnapHelper snapHelper;

    private int snapPosition = RecyclerView.NO_POSITION;

    private OnSnapPageChanged listener;

    public SnapPageChangeListener(SnapHelper snapHelper, OnSnapPageChanged listener) {
        this.snapHelper = snapHelper;
        this.listener = listener;
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        if(newState == RecyclerView.SCROLL_STATE_IDLE) {

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if(layoutManager == null)
                return;

            View view = snapHelper.findSnapView(layoutManager);
            if(view == null)
                return;

            int position = layoutManager.getPosition(view);

            boolean snapPositionChanged = this.snapPosition != position;

            if(snapPositionChanged) {
                if(listener != null)
                    listener.onSnapToItem(position);

                this.snapPosition = position;
            }
        }
    }



    public interface OnSnapPageChanged {
        void onSnapToItem(int index);
    }
}
