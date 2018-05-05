package com.kcrason.highperformancefriendscircle.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kcrason.highperformancefriendscircle.R;
import com.kcrason.highperformancefriendscircle.beans.EmojiBean;
import com.kcrason.highperformancefriendscircle.utils.Utils;

import java.util.List;


public class EmojiPanelView extends LinearLayout {

    private ViewPager mViewPager;
    private HorizontalEmojiIndicators mEmojiIndicators;

    private EmojiPanelPagerAdapter mEmojiPanelPagerAdapter;

    public EmojiPanelView(Context context) {
        super(context);
        init();
    }

    public EmojiPanelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EmojiPanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getY() < Utils.getScreenHeight() - Utils.dp2px(254f)) {
            dismiss();
        }
        return super.onTouchEvent(event);
    }


    private void init() {
        setVisibility(GONE);
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.view_emoji_panel, this, false);
        mViewPager = itemView.findViewById(R.id.view_pager);
        mViewPager.setOverScrollMode(OVER_SCROLL_NEVER);
        mEmojiIndicators = itemView.findViewById(R.id.emoji_indicators);
        addView(itemView);
    }


    public void showEmojiPanel(List<List<EmojiBean>> allEmojiTypes) {
//        showOrHideAnimation(true);
        setVisibility(VISIBLE);
        if (allEmojiTypes != null) {
            if (mEmojiPanelPagerAdapter == null) {
                mEmojiPanelPagerAdapter = new EmojiPanelPagerAdapter(allEmojiTypes);
            }
            mViewPager.setAdapter(mEmojiPanelPagerAdapter);
            mEmojiIndicators.setViewPager(mViewPager, true);
        }
    }

//    private void showOrHideAnimation(final boolean isShow) {
//        if (isShow) {
//            setVisibility(VISIBLE);
//        }
//        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, isShow ? 1.0f : 0.0f,
//                Animation.RELATIVE_TO_PARENT, isShow ? 0.0f : 1.0f);
//        animation.setDuration(300);
//        startAnimation(animation);
//    }

    public void dismiss() {
//        showOrHideAnimation(false);
        setVisibility(GONE);
    }


    public final class EmojiPanelPagerAdapter extends PagerAdapter {

        private List<List<EmojiBean>> allEmojiTypes;

        public EmojiPanelPagerAdapter(List<List<EmojiBean>> allEmojiTypes) {
            this.allEmojiTypes = allEmojiTypes;
        }

        @Override
        public int getCount() {
            return allEmojiTypes == null ? 0 : allEmojiTypes.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        public Drawable getDrawable(int position) {
            if (allEmojiTypes != null && position < allEmojiTypes.size()) {
                List<EmojiBean> emojiBeans = allEmojiTypes.get(position);
                if (emojiBeans != null && emojiBeans.size() > 0) {
                    return ContextCompat.getDrawable(getContext(), emojiBeans.get(0).getEmojiResource());
                }
            }
            return ContextCompat.getDrawable(getContext(), R.drawable.emoji_01_angry);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            EmojiPanelItemView emojiPanelItemView = new EmojiPanelItemView(container.getContext());
            emojiPanelItemView.showItemEmojiPanel(allEmojiTypes.get(position));
            container.addView(emojiPanelItemView);
            return emojiPanelItemView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
