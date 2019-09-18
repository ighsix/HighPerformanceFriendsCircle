package com.kcrason.highperformancefriendscircle.widgets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kcrason.highperformancefriendscircle.R;
import com.kcrason.highperformancefriendscircle.beans.emoji.EmojiBean;
import com.kcrason.highperformancefriendscircle.beans.emoji.EmojiDataSource;
import com.kcrason.highperformancefriendscircle.beans.emoji.EmojiPanelBean;
import com.kcrason.highperformancefriendscircle.interfaces.OnKeyBoardStateListener;
import com.kcrason.highperformancefriendscircle.utils.TextLinkifyUtils;
import com.kcrason.highperformancefriendscircle.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EmojiPanelView extends LinearLayout implements OnKeyBoardStateListener {

    private ViewPager mViewPager;

    private HorizontalEmojiIndicators mEmojiIndicators;

    private EmojiPanelPagerAdapter mEmojiPanelPagerAdapter;

    private LinearLayout mLayoutEmojiPanel;

    private LinearLayout mLayoutPanel;

    private EditText mEditText;

    private FrameLayout mLayoutNull;

    private List<EmojiDataSource> mEmojiDataSources;

    private ImageView mImageSwitch;

    private int mDisplayHeight;

    private boolean isKeyBoardShow;
    private boolean isInitComplete;

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
        if (event.getY() < Utils.getScreenHeight() - Utils.dp2px(254f) && isShowing()) {
            dismiss();
        }
        return super.onTouchEvent(event);
    }

    public boolean isShowing() {
        return mLayoutPanel != null && mLayoutPanel.getVisibility() == VISIBLE;
    }


    private void showSoftKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && mEditText != null) {
            mEditText.post(() -> {
                mEditText.requestFocus();
                inputMethodManager.showSoftInput(mEditText, 0);
            });
            new Handler().postDelayed(() -> {
                changeLayoutNullParams(true);
                changeEmojiPanelParams(0);
            }, 200);
        }
    }


    private void hideSoftKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && mEditText != null) {
            inputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        }
    }


    private void init() {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.view_emoji_panel, this, false);
        mEditText = itemView.findViewById(R.id.edit_text);
        mEditText.setOnTouchListener((v, event) -> {
            showSoftKeyBoard();
            return true;
        });

        mImageSwitch = itemView.findViewById(R.id.img_switch);
        mImageSwitch.setOnClickListener(v -> {
            if (isKeyBoardShow) {
                mImageSwitch.setImageResource(R.drawable.input_keyboard_drawable);
                changeLayoutNullParams(false);
                hideSoftKeyBoard();
                changeEmojiPanelParams(mKeyBoardHeight);
            } else {
                mImageSwitch.setImageResource(R.drawable.input_smile_drawable);
                showSoftKeyBoard();
            }
        });
        mLayoutNull = itemView.findViewById(R.id.layout_null);
        mLayoutEmojiPanel = itemView.findViewById(R.id.layout_emoji_panel);
        mLayoutPanel = itemView.findViewById(R.id.layout_panel);
        mViewPager = itemView.findViewById(R.id.view_pager);
        mViewPager.setOverScrollMode(OVER_SCROLL_NEVER);
        mEmojiIndicators = itemView.findViewById(R.id.emoji_indicators);
        addOnSoftKeyBoardVisibleListener((Activity) getContext(), this);
        addView(itemView);
    }


    public void initEmojiPanel(List<EmojiDataSource> emojiDataSources) {
        if (emojiDataSources != null && emojiDataSources.size() > 0) {
            this.mEmojiDataSources = emojiDataSources;
            asyncInitEmojiPanelData(emojiDataSources);
        }
    }

    private void changeLayoutNullParams(boolean isShowSoftKeyBoard) {
        LinearLayout.LayoutParams params = (LayoutParams) mLayoutNull.getLayoutParams();
        if (isShowSoftKeyBoard) {
            params.weight = 1;
            params.height = 0;
            mLayoutNull.setLayoutParams(params);
        } else {
            params.weight = 0;
            params.height = mDisplayHeight;
            mLayoutNull.setLayoutParams(params);
        }
    }

    private void changeEmojiPanelParams(int keyboardHeight) {
        if (mLayoutEmojiPanel != null) {
            LinearLayout.LayoutParams params = (LayoutParams) mLayoutEmojiPanel.getLayoutParams();
            params.height = keyboardHeight;
            mLayoutEmojiPanel.setLayoutParams(params);
        }
    }

    boolean isVisiableForLast = false;

    public void addOnSoftKeyBoardVisibleListener(Activity activity, final OnKeyBoardStateListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            decorView.getWindowVisibleDisplayFrame(rect);
            //计算出可见屏幕的高度
            int displayHight = rect.bottom - rect.top;
            //获得屏幕整体的高度
            int hight = decorView.getHeight();
            //获得键盘高度
            int keyboardHeight = hight - displayHight - Utils.calcStatusBarHeight(getContext());
            boolean visible = (double) displayHight / hight < 0.8;
            if (visible != isVisiableForLast) {
                listener.onSoftKeyBoardState(visible, keyboardHeight, displayHight - Utils.dp2px(48f));
            }
            isVisiableForLast = visible;
        });
    }


    @Override
    public void onSoftKeyBoardState(boolean visible, int keyboardHeight, int displayHeight) {
        this.isKeyBoardShow = visible;
        if (visible) {
            mKeyBoardHeight = keyboardHeight;
            mDisplayHeight = displayHeight;
        }
    }


    @SuppressLint("CheckResult")
    private void asyncInitEmojiPanelData(List<EmojiDataSource> emojiDataSources) {
        Single.create((SingleOnSubscribe<List<EmojiPanelBean>>) emitter ->
                emitter.onSuccess(generateEmojiPanelBeans(emojiDataSources)))
                .subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe((emojiPanelBeans, throwable) -> {
                    mEmojiPanelPagerAdapter = new EmojiPanelPagerAdapter(emojiPanelBeans);
                    mViewPager.setAdapter(mEmojiPanelPagerAdapter);
                    mEmojiIndicators.setViewPager(mViewPager).setEmojiPanelView(this).setEmojiPanelBeans(emojiPanelBeans).build();
                    isInitComplete = true;
                });
    }

    public int getEmojiTypeSize() {
        return mEmojiDataSources == null ? 0 : mEmojiDataSources.size();
    }

    public void showEmojiPanel() {
        if (isInitComplete) {
            if (mLayoutPanel != null) {
                mLayoutPanel.setVisibility(VISIBLE);
            }
            showOrHideAnimation(true);
        } else {
            initEmojiPanel(mEmojiDataSources);
        }
        showSoftKeyBoard();
    }

    private void showOrHideAnimation(final boolean isShow) {
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, isShow ? 1.0f : 0.0f,
                Animation.RELATIVE_TO_PARENT, isShow ? 0.0f : 1.0f);
        animation.setDuration(200);
        mLayoutPanel.startAnimation(animation);
    }

    public void dismiss() {
        showOrHideAnimation(false);
        if (mLayoutPanel != null) {
            mLayoutPanel.setVisibility(GONE);
        }
        mImageSwitch.setImageResource(R.drawable.input_smile_drawable);
        hideSoftKeyBoard();
    }


    private List<EmojiPanelBean> generateEmojiPanelBeans(List<EmojiDataSource> emojiDataSources) {
        List<EmojiPanelBean> emojiPanelBeans = new ArrayList<>();
        for (int i = 0; i < emojiDataSources.size(); i++) {
            EmojiDataSource emojiDataSource = emojiDataSources.get(i);
            List<EmojiPanelBean> result = makeEmojiPanelBeans(emojiDataSource.getEmojiList(), emojiDataSource.getEmojiType());
            if (result != null) {
                emojiPanelBeans.addAll(result);
            }
        }
        return emojiPanelBeans;
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

    private int mKeyBoardHeight;

    private List<EmojiPanelBean> makeEmojiPanelBeans(List<EmojiBean> sourceEmojiBeans, int emojiType) {
        if (sourceEmojiBeans == null) {
            return null;
        }
        List<EmojiPanelBean> emojiPanelBeans = new ArrayList<>(sourceEmojiBeans.size());
        int sourceEmojiSize = sourceEmojiBeans.size();
        int maxPage = getGroupCount(sourceEmojiBeans);
        for (int i = 0; i < maxPage; i++) {
            EmojiPanelBean emojiPanelBean = new EmojiPanelBean();
            emojiPanelBean.setEmojiType(emojiType);
            emojiPanelBean.setMaxPage(maxPage);
            int start = i * 20;
            int end = start + 20;
            if (end < sourceEmojiSize) {
                emojiPanelBean.setEmojiBeansPerPage(generateItemEmojiBeans(sourceEmojiBeans, start, end));
            } else {
                emojiPanelBean.setEmojiBeansPerPage(generateItemEmojiBeans(sourceEmojiBeans, start, sourceEmojiSize));
            }
            emojiPanelBeans.add(emojiPanelBean);
        }
        return emojiPanelBeans;
    }


    private void inputEmoji(String emojiName) {
        int start = mEditText.getSelectionStart();
        Editable editable = mEditText.getEditableText();
        SpannableStringBuilder spannable = TextLinkifyUtils.getLinkifyTextContent(mEditText, " " + emojiName, TextLinkifyUtils.TextLinkifyStatus.EMOJI);
        editable.insert(start, spannable);
    }

    private List<EmojiBean> generateItemEmojiBeans(List<EmojiBean> emojiBeans, int start, int end) {
        List<EmojiBean> targetEmojiBeans = new ArrayList<>();
        for (int i = start; i < end; i++) {
            EmojiBean emojiBean = emojiBeans.get(i);
            targetEmojiBeans.add(emojiBean);
        }
        return targetEmojiBeans;
    }


    public final class EmojiPanelPagerAdapter extends PagerAdapter {

        private List<EmojiPanelBean> mEmojiPanelBeans;

        public EmojiPanelPagerAdapter(List<EmojiPanelBean> emojiPanelBeans) {
            this.mEmojiPanelBeans = emojiPanelBeans;
        }

        @Override
        public int getCount() {
            return mEmojiPanelBeans == null ? 0 : mEmojiPanelBeans.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        public Drawable getDrawable(int position) {
            if (mEmojiPanelBeans != null && position < mEmojiPanelBeans.size()) {
                EmojiPanelBean emojiPanelBean = mEmojiPanelBeans.get(position);
                if (emojiPanelBean.getEmojiBeansPerPage() != null && emojiPanelBean.getEmojiBeansPerPage().size() > 0) {
                    return ContextCompat.getDrawable(getContext(), emojiPanelBean.getEmojiBeansPerPage().get(0).getEmojiResource());
                }
            }
            return ContextCompat.getDrawable(getContext(), R.drawable.emoji_01_angry);
        }


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            RecyclerView recyclerView = new RecyclerView(container.getContext());
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
            recyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
            recyclerView.setAdapter(new EmojiPanelItemRecyclerAdapter(getContext(), mEmojiPanelBeans.get(position).getEmojiBeansPerPage()));
            container.addView(recyclerView);
            return recyclerView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
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
                holder.itemView.setOnClickListener(v -> inputEmoji(mEmojiBeans.get(position).getEmojiName()));
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

}
