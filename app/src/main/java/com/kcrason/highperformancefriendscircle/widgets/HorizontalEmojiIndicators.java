package com.kcrason.highperformancefriendscircle.widgets;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kcrason.highperformancefriendscircle.R;
import com.kcrason.highperformancefriendscircle.beans.emoji.EmojiIndicatorInfo;
import com.kcrason.highperformancefriendscircle.beans.emoji.EmojiPanelBean;
import com.kcrason.highperformancefriendscircle.utils.Utils;

import java.util.List;

public class HorizontalEmojiIndicators extends LinearLayout implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;

    /**
     * 表情的总页数
     */
    private List<EmojiPanelBean> mEmojiPanelBeans;

    /**
     * 表情面板View
     */
    private EmojiPanelView mEmojiPanelView;

    /**
     * 显示表情的adapter
     */
    private PagerAdapter mPagerAdapter;

    /**
     * 内部指示器
     */
    private LinearLayout mInnerIndicatorParentView;

    /**
     * 外部指示器
     */
    private LinearLayout mOutIndicatorParentView;


    /**
     * 用于保存外部指示器的ViewPager position和外部指示器index的对应关系。
     */
    private SparseArray<EmojiIndicatorInfo> mOutInfoSparseArray = new SparseArray<>();
    /**
     * 用于保存内部指示器的ViewPager position和外部指示器index的对应关系。
     */
    private SparseArray<EmojiIndicatorInfo> mInnerInfoSparseArray = new SparseArray<>();


    /**
     * 用于保存内部指示器的位置。使得点击切换时可以定位到响应的位置
     * key：emojiType（emoji的类型）
     * value:当前这一类型下的索引
     */
    private SparseIntArray mOutIndicatorLastPosition = new SparseIntArray();

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
        setOrientation(VERTICAL);
        addView(mInnerIndicatorParentView = createIndicatorParentView(true));
        addView(mOutIndicatorParentView = createIndicatorParentView(false));
    }


    public HorizontalEmojiIndicators setViewPager(ViewPager viewPager) {
        if (viewPager == null) {
            throw new NullPointerException("viewpager my be null");
        }
        mViewPager = viewPager;
        mPagerAdapter = viewPager.getAdapter();
        if (mPagerAdapter == null) {
            throw new NullPointerException("pageradapter my be null");
        }
        viewPager.addOnPageChangeListener(this);
        return this;
    }


    public HorizontalEmojiIndicators setEmojiPanelView(EmojiPanelView emojiPanelView) {
        mEmojiPanelView = emojiPanelView;
        return this;
    }

    public HorizontalEmojiIndicators setEmojiPanelBeans(List<EmojiPanelBean> emojiPanelBeans) {
        if (emojiPanelBeans != null && emojiPanelBeans.size() > 0) {
            mEmojiPanelBeans = emojiPanelBeans;
        }
        return this;
    }

    public void build() {
        if (mEmojiPanelBeans != null && mEmojiPanelBeans.size() > 0) {
            createInnerIndicator(mEmojiPanelBeans.get(0).getMaxPage(), mEmojiPanelBeans.get(0).getEmojiType());
            generateInnerPositinInfo();
            generateOutPositionInfo();
        }
        createOutIndicator(mEmojiPanelView.getEmojiTypeSize());
    }


    private LinearLayout createIndicatorParentView(boolean isInnerIndicator) {
        LinearLayout indicatorParentView = new LinearLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dp2px(44f));
        if (isInnerIndicator) {
            indicatorParentView.setGravity(Gravity.CENTER);
            indicatorParentView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.base_F2F2F2));
        } else {
            indicatorParentView.setGravity(Gravity.CENTER_VERTICAL);
            indicatorParentView.setBackgroundColor(Color.WHITE);
        }
        indicatorParentView.setLayoutParams(params);
        return indicatorParentView;
    }


    private void createInnerIndicator(int itemCount, int emojiType) {
        if (mInnerIndicatorParentView == null) {
            mInnerIndicatorParentView = createIndicatorParentView(true);
            mInnerIndicatorParentView.removeAllViews();
            addInnerIndicatorItemView(mInnerIndicatorParentView, itemCount, emojiType);
            addView(mInnerIndicatorParentView);
        } else {
            mInnerIndicatorParentView.removeAllViews();
            addInnerIndicatorItemView(mInnerIndicatorParentView, itemCount, emojiType);
        }
    }


    private void createOutIndicator(int newViewCount) {
        if (mOutIndicatorParentView == null) {
            mOutIndicatorParentView = createIndicatorParentView(false);
            mOutIndicatorParentView.removeAllViews();
            addOutIndcatorItemView(mOutIndicatorParentView, newViewCount);
            addView(mOutIndicatorParentView);
        } else {
            mOutIndicatorParentView.removeAllViews();
            addOutIndcatorItemView(mOutIndicatorParentView, newViewCount);
        }
    }

    /**
     * 移除多余的View
     *
     * @param parentView
     * @param oldViewCount
     * @param newViewCount
     */
    private void removeSurplusViews(LinearLayout parentView, int oldViewCount, int newViewCount) {
        if (oldViewCount > newViewCount) {
            parentView.removeViews(newViewCount, oldViewCount - newViewCount);
        }
    }

    /**
     * 创建并加入外部指示器的itemView
     *
     * @param parentView
     * @param newViewCount
     */
    private void addOutIndcatorItemView(LinearLayout parentView, int newViewCount) {
        int oldViewCount = parentView.getChildCount();
        removeSurplusViews(parentView, oldViewCount, newViewCount);
        for (int i = 0; i < newViewCount; i++) {
            boolean hasChild = i < oldViewCount;
            View childView = hasChild ? parentView.getChildAt(i) : null;
            if (childView == null) {
                final int index = i;
                ImageView itemView = createOutImageView(i);
                itemView.setOnClickListener(v -> switchOutIndicatorToTargetPosition(index));
                mOutIndicatorParentView.addView(itemView);
            }
        }
    }

    /**
     * 创建并加入内部指示器的itemView
     *
     * @param parentView
     * @param newViewCount
     * @param emjiType
     */
    private void addInnerIndicatorItemView(LinearLayout parentView, int newViewCount, int emjiType) {
        int oldViewCount = parentView.getChildCount();
        removeSurplusViews(parentView, oldViewCount, newViewCount);
        for (int i = 0; i < newViewCount; i++) {
            boolean hasChild = i < oldViewCount;
            View childView = hasChild ? parentView.getChildAt(i) : null;
            if (childView == null) {
                final int index = i;
                ImageView itemView = createInnerImageView(i);
                itemView.setOnClickListener(v -> switchInnerIndicatorToTargetPosition(index, emjiType));
                mInnerIndicatorParentView.addView(itemView);
            }
        }
    }


    /**
     * 点击内部指示器时切换到指定页面
     *
     * @param index
     * @param emojiType
     */
    private void switchInnerIndicatorToTargetPosition(int index, int emojiType) {
        for (int i = 0; i < mInnerInfoSparseArray.size(); i++) {
            EmojiIndicatorInfo innerInfo = mInnerInfoSparseArray.get(i);
            if (innerInfo.getIndicatorIndex() == index && innerInfo.getEmojiType() == emojiType) {
                int keyValue = mInnerInfoSparseArray.keyAt(i);
                mViewPager.setCurrentItem(keyValue);
                break;
            }
        }
    }

    /**
     * 点击外部指示器时切换到指定页面
     *
     * @param index
     */

    private void switchOutIndicatorToTargetPosition(int index) {
        for (int i = 0; i < mOutInfoSparseArray.size(); i++) {
            EmojiIndicatorInfo emojiIndicatorInfo = mOutInfoSparseArray.get(i);
            if (emojiIndicatorInfo.getIndicatorIndex() == index) {
                int keyValue = mOutInfoSparseArray.keyAt(i);
                EmojiIndicatorInfo innerInfo = mInnerInfoSparseArray.get(keyValue);
                int lastPosition = mOutIndicatorLastPosition.get(emojiIndicatorInfo.getEmojiType());
                if (innerInfo.getIndicatorIndex() == lastPosition) {
                    mViewPager.setCurrentItem(keyValue);
                    break;
                }
            }
        }
    }


    /**
     * 将ViewPager的position去生成内部指示器的index，形成对应关系，便于后面指示器的显示状态的切换。
     */
    private void generateInnerPositinInfo() {
        int innerIndex = 0;
        for (int i = 0; i < mEmojiPanelBeans.size(); i++) {
            int type = mEmojiPanelBeans.get(i).getEmojiType();
            if (isExistEmojiTypeInSparse(true, type)) {
                mInnerInfoSparseArray.put(i, new EmojiIndicatorInfo(type, ++innerIndex));
            } else {
                //不存在
                innerIndex = 0;
                mInnerInfoSparseArray.put(i, new EmojiIndicatorInfo(type, innerIndex));
            }
        }
    }


    /**
     * 将ViewPager的position去生成外部指示器的index，形成对应关系，便于后面指示器的显示状态的切换。
     */
    private void generateOutPositionInfo() {
        int outIndex = -1;
        for (int i = 0; i < mEmojiPanelBeans.size(); i++) {
            int type = mEmojiPanelBeans.get(i).getEmojiType();
            if (isExistEmojiTypeInSparse(false, type)) {
                mOutInfoSparseArray.put(i, new EmojiIndicatorInfo(type, outIndex));
            } else {
                mOutInfoSparseArray.put(i, new EmojiIndicatorInfo(type, ++outIndex));
            }
        }
    }


    /**
     * 检查是否已经在SparseArray中存在相应emoji类型的所对应的索引，用于构建内外指示器SpareArray数据调用
     *
     * @param isInnerIndicator
     * @param emojiType
     * @return
     */
    private boolean isExistEmojiTypeInSparse(boolean isInnerIndicator, int emojiType) {
        int size = isInnerIndicator ? mInnerInfoSparseArray.size() : mOutInfoSparseArray.size();
        for (int j = 0; j < size; j++) {
            EmojiIndicatorInfo emojiIndicatorInfo = isInnerIndicator ? mInnerInfoSparseArray.get(j) : mOutInfoSparseArray.get(j);
            if (emojiIndicatorInfo != null && emojiIndicatorInfo.getEmojiType() == emojiType) {
                return true;
            }
        }
        return false;
    }


    /**
     * 创建内部指示器的ItemView（ImageView）
     *
     * @param index
     * @return
     */
    private ImageView createInnerImageView(int index) {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LayoutParams(Utils.dp2px(6f), Utils.dp2px(6f));
        layoutParams.leftMargin = Utils.dp2px(4f);
        layoutParams.rightMargin = Utils.dp2px(4f);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageDrawable(index == 0 ? ContextCompat.getDrawable(getContext(), R.drawable.indicator_selected) :
                ContextCompat.getDrawable(getContext(), R.drawable.indicator_normal));
        return imageView;
    }

    /**
     * 创建外部指示器的itemView（ImageView）
     *
     * @param index
     * @return
     */
    private ImageView createOutImageView(int index) {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LayoutParams(Utils.dp2px(60), Utils.dp2px(48f));
        imageView.setLayoutParams(layoutParams);
        imageView.setBackgroundColor(index == 0 ? ContextCompat.getColor(getContext(), R.color.base_DCDCDC) : ContextCompat.getColor(getContext(), R.color.base_FFFFFF));
        int padding = Utils.dp2px(8f);
        imageView.setPadding(padding, padding, padding, padding);
        if (mPagerAdapter instanceof EmojiPanelView.EmojiPanelPagerAdapter) {
            imageView.setImageDrawable(((EmojiPanelView.EmojiPanelPagerAdapter) mPagerAdapter).getDrawable(index));
        }
        return imageView;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 滑动切换ViewPager时，更新外部指示器的状态。
     *
     * @param count
     * @param position
     * @param emojiType
     */
    private void updateInnerIndicatorState(int count, int position, int emojiType) {
        for (int k = 0; k < count; k++) {
            ImageView imageView = (ImageView) mInnerIndicatorParentView.getChildAt(k);
            if (mInnerInfoSparseArray.get(position).getIndicatorIndex() == k) {
                mOutIndicatorLastPosition.put(emojiType, k);
                imageView.setImageResource(R.drawable.indicator_selected);
            } else {
                imageView.setImageResource(R.drawable.indicator_normal);
            }
        }
    }


    /**
     * 滑动切换ViewPager时，更新内部部指示器的状态。
     *
     * @param position
     */
    private void updateOutIndicatorState(int position) {
        int outCount = mOutIndicatorParentView.getChildCount();
        for (int j = 0; j < outCount; j++) {
            ImageView imageView = (ImageView) mOutIndicatorParentView.getChildAt(j);
            if (mOutInfoSparseArray.get(position).getIndicatorIndex() == j) {
                imageView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.base_DCDCDC));
            } else {
                imageView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.base_FFFFFF));
            }
        }
    }


    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (childView instanceof LinearLayout) {
                if (i == 0) {
                    //内部指示器
                    if (mInnerIndicatorParentView != null) {
                        int maxCount = mEmojiPanelBeans.get(position).getMaxPage();
                        int innerCount = mInnerIndicatorParentView.getChildCount();
                        int emojitType = positionToEmojiType(position);
                        if (innerCount != maxCount) {
                            createInnerIndicator(maxCount, emojitType);
                            updateInnerIndicatorState(maxCount, position, emojitType);
                        } else {
                            updateInnerIndicatorState(innerCount, position, emojitType);
                        }
                    }
                } else {
                    //外部指示器
                    if (mOutIndicatorParentView != null) {
                        updateOutIndicatorState(position);
                    }
                }
            }
        }
    }


    /**
     * 通过当前position获取该position的emoji的类型
     *
     * @param position
     * @return
     */
    public int positionToEmojiType(int position) {
        if (mEmojiPanelBeans != null && position < mEmojiPanelBeans.size()) {
            return mEmojiPanelBeans.get(position).getEmojiType();
        }
        return -1;
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
