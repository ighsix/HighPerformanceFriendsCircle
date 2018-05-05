package com.kcrason.highperformancefriendscircle.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kcrason.highperformancefriendscircle.R;
import com.kcrason.highperformancefriendscircle.beans.EmojiBean;
import com.kcrason.highperformancefriendscircle.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class EmojiPanelItemView extends LinearLayout {

    private ViewPager mViewPager;
    private HorizontalEmojiIndicators mEmojiIndicators;

    private Context mContext;

    public EmojiPanelItemView(Context context) {
        super(context);
        init();
    }

    public EmojiPanelItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EmojiPanelItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        this.mContext = getContext();
        mViewPager = new ViewPager(getContext());
        mViewPager.setOverScrollMode(OVER_SCROLL_NEVER);
        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        mViewPager.setLayoutParams(layoutParams);
        addView(mViewPager);

        mEmojiIndicators = new HorizontalEmojiIndicators(getContext());
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dp2px(44f));
        mEmojiIndicators.setLayoutParams(layoutParams);
        addView(mEmojiIndicators);
    }


    public void showItemEmojiPanel(List<EmojiBean> emojiBeans) {
        if (emojiBeans != null) {
            List<List<EmojiBean>> groupEmojiBeans = makeEmojiPanelFragments(emojiBeans);
            mViewPager.setAdapter(new EmojiPanelPagerItemAdapter(groupEmojiBeans));
            mEmojiIndicators.setViewPager(mViewPager, false);
        }
    }


    private int getGroupCount(List<EmojiBean> emojiBeans) {
        int emojiCount = emojiBeans.size();
        int itemCount = emojiCount / 20;
        int modular = emojiCount % 20;
        if (modular == 0) {
            return itemCount;
        }
        return itemCount + 1;
    }


    private List<List<EmojiBean>> makeEmojiPanelFragments(List<EmojiBean> emojiBeans) {
        List<List<EmojiBean>> emojiPanelFragments = new ArrayList<>();
        for (int i = 0; i < getGroupCount(emojiBeans); i++) {
            int start = i * 20;
            int end = start + 20;
            if (end < emojiBeans.size()) {
                emojiPanelFragments.add(generateItemEmojiBeans(emojiBeans, start, end));
            } else {
                emojiPanelFragments.add(generateItemEmojiBeans(emojiBeans, start, emojiBeans.size()));
            }
        }
        return emojiPanelFragments;
    }

    private List<EmojiBean> generateItemEmojiBeans(List<EmojiBean> emojiBeans, int start, int end) {
        List<EmojiBean> targetEmojiBeans = new ArrayList<>();
        for (int i = start; i < end; i++) {
            EmojiBean emojiBean = emojiBeans.get(i);
            targetEmojiBeans.add(emojiBean);
        }
        return targetEmojiBeans;
    }


    public final class EmojiPanelItemRecyclerAdapter extends RecyclerView.Adapter<EmojiPanelItemViewHolder> {

        private List<EmojiBean> mEmojiBeans;

        private final static int MAX_COUNT = 20;
        private Context mContext;

        public EmojiPanelItemRecyclerAdapter(Context context, List<EmojiBean> emojiBeans) {
            this.mContext = context;
            this.mEmojiBeans = emojiBeans;
        }

        @NonNull
        @Override
        public EmojiPanelItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new EmojiPanelItemViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recycler_emoji, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull EmojiPanelItemViewHolder holder, int position) {
            if (position == mEmojiBeans.size()) {
                holder.imgEmoji.setImageResource(R.drawable.emoji_delete_drawable);
            } else {
                holder.imgEmoji.setImageResource(mEmojiBeans.get(position).getEmojiResource());
                holder.itemView.setOnClickListener(v -> Toast.makeText(mContext, mEmojiBeans.get(position).getEmojiName(), Toast.LENGTH_SHORT).show());
            }
        }

        @Override
        public int getItemCount() {
            if (mEmojiBeans == null) {
                return 0;
            } else {
                int drawSize = mEmojiBeans.size();
                if (drawSize <= MAX_COUNT) {
                    return drawSize + 1;
                }
                return MAX_COUNT + 1;
            }
        }
    }

    private final class EmojiPanelItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imgEmoji;

        public EmojiPanelItemViewHolder(View itemView) {
            super(itemView);
            imgEmoji = itemView.findViewById(R.id.img_emoji);
        }
    }


    private final class EmojiPanelPagerItemAdapter extends PagerAdapter {

        private List<List<EmojiBean>> groupEmojiBeans;

        public EmojiPanelPagerItemAdapter(List<List<EmojiBean>> groupEmojiBeans) {
            this.groupEmojiBeans = groupEmojiBeans;
        }

        @Override
        public int getCount() {
            return groupEmojiBeans == null ? 0 : groupEmojiBeans.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            RecyclerView recyclerView = new RecyclerView(container.getContext());
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 7));
            recyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
            recyclerView.setAdapter(new EmojiPanelItemRecyclerAdapter(mContext, groupEmojiBeans.get(position)));
            container.addView(recyclerView);
            return recyclerView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
