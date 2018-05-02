package com.ahmetroid.ahmet.bakingapp.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ahmetroid.ahmet.bakingapp.R;

public class MainItemDecoration extends RecyclerView.ItemDecoration {

    private final int itemDivider;
    private final int itemDividerHalf;

    public MainItemDecoration(Context context) {
        itemDivider = context.getResources().getDimensionPixelSize(R.dimen.margin_normal);
        itemDividerHalf = itemDivider / 2;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int spanCount = 1;
        int position = parent.getChildLayoutPosition(view);

        outRect.bottom = itemDivider;
        outRect.left = itemDivider;
        outRect.right = itemDivider;

        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
            spanCount = gridLayoutManager.getSpanCount();

            if (position % spanCount == 0) {
                outRect.right = itemDividerHalf;
            } else if (position % spanCount == spanCount - 1) {
                outRect.left = itemDividerHalf;
            }
        }

        if (position < spanCount) {
            outRect.top = itemDivider;
        } else {
            outRect.top = 0;
        }
    }
}
