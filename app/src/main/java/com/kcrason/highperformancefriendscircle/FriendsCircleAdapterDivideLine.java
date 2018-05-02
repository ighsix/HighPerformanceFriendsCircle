package com.kcrason.highperformancefriendscircle;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author KCrason
 * @date 2018/5/2
 */
public class FriendsCircleAdapterDivideLine extends RecyclerView.ItemDecoration {
    private int mDivideHeight;

    public FriendsCircleAdapterDivideLine(Context context) {
        mDivideHeight = Utils.dp2px(context, 0.5f);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, mDivideHeight);
    }
}
