package com.kcrason.highperformancefriendscircle.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * @author KCrason
 * @date 2018/5/6
 */
public class TestChildView extends AppCompatTextView {
    public TestChildView(Context context) {
        super(context);
    }

    public TestChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.i("KCrason", "TestChildView onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        Log.i("KCrason", "TestChildView onLayout");
        super.onLayout(changed, l, t, r, b);
    }
}
