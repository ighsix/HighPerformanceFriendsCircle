package com.kcrason.highperformancefriendscircle.widgets;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kcrason.highperformancefriendscircle.R;
import com.kcrason.highperformancefriendscircle.utils.Utils;

public class HorizontalEmojiIndicators extends LinearLayout implements ViewPager.OnPageChangeListener {

    private boolean isOutEmojiIndicator;

    public HorizontalEmojiIndicators(Context context) {
        super(context);
        init();
    }

    public HorizontalEmojiIndicators(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalEmojiIndicators(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
    }

    public void setViewPager(ViewPager viewPager, boolean isOutEmojiIndicator) {
        this.isOutEmojiIndicator = isOutEmojiIndicator;
        if (isOutEmojiIndicator) {
            setGravity(Gravity.CENTER_VERTICAL);
            setBackgroundColor(Color.WHITE);
        } else {
            setBackgroundColor(ContextCompat.getColor(getContext(), R.color.base_F2F2F2));
            setGravity(Gravity.CENTER);
        }
        if (viewPager == null) {
            throw new NullPointerException("viewpager my be null");
        }
        PagerAdapter pagerAdapter = viewPager.getAdapter();
        if (pagerAdapter == null) {
            throw new NullPointerException("pageradapter my be null");
        }
        viewPager.addOnPageChangeListener(this);
        removeAllViews();
        for (int i = 0, size = pagerAdapter.getCount(); i < size; i++) {
            final int index = i;
            ImageView itemView = isOutEmojiIndicator ? createImageView(i, pagerAdapter) : createImageView(i);
            itemView.setOnClickListener(v -> viewPager.setCurrentItem(index));
            addView(itemView);
        }
    }

    private ImageView createImageView(int index) {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LayoutParams(Utils.dp2px(6f), Utils.dp2px(6f));
        layoutParams.leftMargin = Utils.dp2px(4f);
        layoutParams.rightMargin = Utils.dp2px(4f);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageDrawable(index == 0 ? ContextCompat.getDrawable(getContext(), R.drawable.indicator_selected) :
                ContextCompat.getDrawable(getContext(), R.drawable.indicator_normal));
        return imageView;
    }

    private ImageView createImageView(int index, PagerAdapter pagerAdapter) {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LayoutParams(Utils.dp2px(60), Utils.dp2px(48f));
        imageView.setLayoutParams(layoutParams);
        imageView.setBackgroundColor(index == 0 ? ContextCompat.getColor(getContext(), R.color.base_DCDCDC) : ContextCompat.getColor(getContext(), R.color.base_FFFFFF));
        int padding = Utils.dp2px(8f);
        imageView.setPadding(padding, padding, padding, padding);
        if (pagerAdapter instanceof EmojiPanelView.EmojiPanelPagerAdapter) {
            imageView.setImageDrawable(((EmojiPanelView.EmojiPanelPagerAdapter) pagerAdapter).getDrawable(index));
        }
        return imageView;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (childView instanceof ImageView) {
                if (i == position) {
                    if (isOutEmojiIndicator) {
                        childView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.base_DCDCDC));
                    } else {
                        ((ImageView) childView).setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.indicator_selected));
                    }
                } else {
                    if (isOutEmojiIndicator) {
                        childView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.base_FFFFFF));
                    } else {
                        ((ImageView) childView).setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.indicator_normal));
                    }
                }
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
