package com.kcrason.highperformancefriendscircle.widgets;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kcrason.highperformancefriendscircle.R;
import com.kcrason.highperformancefriendscircle.others.SimpleWeakObjectPool;
import com.kcrason.highperformancefriendscircle.utils.TimerUtils;
import com.kcrason.highperformancefriendscircle.enums.TranslationState;
import com.kcrason.highperformancefriendscircle.interfaces.OnItemClickPopupMenuListener;
import com.kcrason.highperformancefriendscircle.utils.Utils;
import com.kcrason.highperformancefriendscircle.beans.CommentBean;
import com.kcrason.highperformancefriendscircle.span.TextMovementMethod;

import java.util.List;

/**
 * @author KCrason
 * @date 2018/4/27
 */
public class VerticalCommentWidget extends LinearLayout implements ViewGroup.OnHierarchyChangeListener, OnItemClickPopupMenuListener {

    private List<CommentBean> mCommentBeans;

    private LinearLayout.LayoutParams mLayoutParams;
    private SimpleWeakObjectPool<View> COMMENT_TEXT_POOL;
    private int mCommentVerticalSpace;

    public VerticalCommentWidget(Context context) {
        super(context);
        init();
    }

    public VerticalCommentWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerticalCommentWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCommentVerticalSpace = Utils.dp2px(3f);
        COMMENT_TEXT_POOL = new SimpleWeakObjectPool<>();
        setOnHierarchyChangeListener(this);
    }


    public void addComments(List<CommentBean> commentBeans, boolean isStartAnimation) {
        this.mCommentBeans = commentBeans;
        if (commentBeans != null) {
            int oldCount = getChildCount();
            int newCount = commentBeans.size();
            if (oldCount > newCount) {
                removeViewsInLayout(newCount, oldCount - newCount);
            }
            for (int i = 0; i < newCount; i++) {
                boolean hasChild = i < oldCount;
                View childView = hasChild ? getChildAt(i) : null;
                CommentBean commentBean = commentBeans.get(i);
                SpannableStringBuilder commentSpan = commentBean.getCommentContentSpan();
                TranslationState translationState = commentBean.getTranslationState();
                if (childView == null) {
                    childView = COMMENT_TEXT_POOL.get();
                    if (childView == null) {
                        addViewInLayout(makeCommentItemView(commentSpan, i, translationState, isStartAnimation), i, generateMarginLayoutParams(i), true);
                    } else {
                        addCommentItemView(childView, commentSpan, i, translationState, isStartAnimation);
                    }
                } else {
                    updateCommentData(childView, commentSpan, i, translationState, isStartAnimation);
                }
            }
            requestLayout();
        }
    }


    /**
     * 更新指定的position的comment
     */
    public void updateTargetComment(int position, List<CommentBean> commentBeans) {
        int oldCount = getChildCount();
        for (int i = 0; i < oldCount; i++) {
            if (i == position) {
                View childView = getChildAt(i);
                if (childView != null) {
                    CommentBean commentBean = commentBeans.get(i);
                    SpannableStringBuilder commentSpan = commentBean.getCommentContentSpan();
                    TranslationState translationState = commentBean.getTranslationState();
                    updateCommentData(childView, commentSpan, i, translationState, true);
                }
                break;
            }
        }
        requestLayout();
    }


    /**
     * 創建Comment item view
     */
    private View makeCommentItemView(SpannableStringBuilder content, int index, TranslationState translationState, boolean isStartAnimation) {
        if (translationState == TranslationState.START) {
            return makeContentTextView(content, index);
        } else {
            return new CommentTranslationLayoutView(getContext())
                    .setOnItemClickPopupMenuListener(this)
                    .setCurrentPosition(index)
                    .setSourceContent(content)
                    .setTranslationContent(content)
                    .setTranslationState(translationState, isStartAnimation);
        }
    }


    /**
     * 添加需要的Comment View
     */
    private void addCommentItemView(View view, SpannableStringBuilder builder, int index, TranslationState translationState, boolean isStartAnimation) {
        if (view instanceof CommentTranslationLayoutView) {
            if (translationState == TranslationState.START) {
                addViewInLayout(makeCommentItemView(builder, index, translationState, isStartAnimation), index, generateMarginLayoutParams(index), true);
            } else {
                CommentTranslationLayoutView translationLayoutView = (CommentTranslationLayoutView) view;
                translationLayoutView.setOnItemClickPopupMenuListener(this).setCurrentPosition(index).setSourceContent(builder).setTranslationContent(builder);
                addViewInLayout(translationLayoutView, index, generateMarginLayoutParams(index), true);
            }
        } else if (view instanceof TextView) {
            if (translationState == TranslationState.START) {
                ((TextView) view).setText(builder);
                addOnItemClickPopupMenuListener(view, index, TranslationState.START);
                addViewInLayout(view, index, generateMarginLayoutParams(index), true);
            } else {
                addViewInLayout(makeCommentItemView(builder, index, translationState, isStartAnimation), index, generateMarginLayoutParams(index), true);
            }
        }
    }


    private void addOnItemClickPopupMenuListener(View view, int index, TranslationState translationState) {
        view.setOnLongClickListener(v -> {
            Utils.showPopupMenu(getContext(), VerticalCommentWidget.this, index, v, translationState);
            return false;
        });
    }

    /**
     * 更新comment list content
     */
    private void updateCommentData(View view, SpannableStringBuilder builder, int index, TranslationState translationState, boolean isStartAnimation) {
        if (view instanceof CommentTranslationLayoutView) {
            if (translationState == TranslationState.START) {
                addViewInLayout(makeCommentItemView(builder, index, translationState, isStartAnimation), index, generateMarginLayoutParams(index), true);
                removeViewInLayout(view);
            } else {
                CommentTranslationLayoutView translationLayoutView = (CommentTranslationLayoutView) view;
                translationLayoutView.setCurrentPosition(index)
                        .setSourceContent(builder)
                        .setTranslationContent(builder)
                        .setTranslationState(translationState, isStartAnimation);
            }
        } else if (view instanceof TextView) {
            if (translationState == TranslationState.START) {
                ((TextView) view).setText(builder);
            } else {
                addViewInLayout(makeCommentItemView(builder, index, translationState, isStartAnimation), index, generateMarginLayoutParams(index), true);
                removeViewInLayout(view);
            }
        }
    }

    private TextView makeContentTextView(SpannableStringBuilder content, int index) {
        TextView textView = new TextView(getContext());
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.base_333333));
        textView.setBackgroundResource(R.drawable.selector_view_name_state);
        textView.setTextSize(16f);
        textView.setLineSpacing(mCommentVerticalSpace, 1f);
        textView.setText(content);
        textView.setMovementMethod(new TextMovementMethod());
        addOnItemClickPopupMenuListener(textView, index, TranslationState.START);
        return textView;
    }


    private LayoutParams generateMarginLayoutParams(int index) {
        if (mLayoutParams == null) {
            mLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        if (mCommentBeans != null && index > 0) {
            mLayoutParams.bottomMargin = index == mCommentBeans.size() - 1 ? 0 : mCommentVerticalSpace;
        }
        return mLayoutParams;
    }

    @Override
    public void onChildViewAdded(View parent, View child) {

    }

    @Override
    public void onChildViewRemoved(View parent, View child) {
        COMMENT_TEXT_POOL.put(child);
    }

    @Override
    public void onItemClickCopy(int position) {
        Toast.makeText(getContext(), "已复制", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickTranslation(int position) {
        if (mCommentBeans != null && position < mCommentBeans.size()) {
            mCommentBeans.get(position).setTranslationState(TranslationState.CENTER);
            updateTargetComment(position, mCommentBeans);
            TimerUtils.timerTranslation(() -> {
                if (mCommentBeans != null && position < mCommentBeans.size()) {
                    mCommentBeans.get(position).setTranslationState(TranslationState.END);
                    updateTargetComment(position, mCommentBeans);
                }
            });
        }
    }


    @Override
    public void onItemClickHideTranslation(int position) {
        if (mCommentBeans != null && position < mCommentBeans.size()) {
            mCommentBeans.get(position).setTranslationState(TranslationState.START);
            updateTargetComment(position, mCommentBeans);
        }
    }

    @Override
    public void onItemClickCollection(int position) {
        Toast.makeText(getContext(), "已收藏", Toast.LENGTH_SHORT).show();
    }


}
