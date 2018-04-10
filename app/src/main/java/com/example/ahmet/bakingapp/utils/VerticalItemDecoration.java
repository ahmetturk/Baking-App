package com.example.ahmet.bakingapp.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ahmet.bakingapp.R;

public class VerticalItemDecoration extends RecyclerView.ItemDecoration {

    private final Context mContext;

    public VerticalItemDecoration(Context context) {
        this.mContext = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        if (position == RecyclerView.NO_POSITION) {
            outRect.set(0, 0, 0, 0);
            return;
        }

        int itemDivider = mContext.getResources().getDimensionPixelSize(R.dimen.margin_normal);

        outRect.top = itemDivider;

        if (position == state.getItemCount() - 1) {
            outRect.bottom = itemDivider;
        }
    }
}
