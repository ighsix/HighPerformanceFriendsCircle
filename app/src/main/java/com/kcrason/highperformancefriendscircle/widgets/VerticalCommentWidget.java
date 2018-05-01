package com.kcrason.highperformancefriendscircle.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kcrason.highperformancefriendscircle.R;
import com.kcrason.highperformancefriendscircle.enums.TranslationState;
import com.kcrason.highperformancefriendscircle.interfaces.OnItemClickPopupMenuListener;
import com.kcrason.highperformancefriendscircle.span.TextMovementMothod;
import com.kcrason.highperformancefriendscircle.Utils;
import com.kcrason.highperformancefriendscircle.beans.CommentBean;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.List;

/**
 * @author KCrason
 * @date 2018/4/27
 */
public class VerticalCommentWidget extends LinearLayout implements ViewGroup.OnHierarchyChangeListener,
        OnItemClickPopupMenuListener {

    private List<CommentBean> mCommentBeans;

    private int mVerticalSpace;

    public VerticalCommentWidget(Context context) {
        super(context);
        init(context);
    }

    public VerticalCommentWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VerticalCommentWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mVerticalSpace = Utils.dp2px(context, 4f);
        COMMENT_TEXT_POOL = new SimpleWeakObjectPool<>();
        setOnHierarchyChangeListener(this);
    }


    public void addComments(List<CommentBean> commentBeans) {
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
                SpannableStringBuilder commentSpan = commentBean.getRichTextCommentContent();
                boolean isShowTranslation = commentBean.isShowContentTranslation();
                if (childView == null) {
                    childView = COMMENT_TEXT_POOL.get();
                    if (childView == null) {
                        addViewInLayout(createItemView(commentSpan, i, isShowTranslation), i, generateDefaultLayoutParams(), true);
                    } else {
                        if (isShowTranslation) {
                            if (childView instanceof CommentTranslationLayoutView) {
                                addViewInLayout(childView, i, generateDefaultLayoutParams(), true);
                            } else {
                                addViewInLayout(createItemView(commentSpan, i, true), i, generateDefaultLayoutParams(), true);
                            }
                        } else {
                            if (childView instanceof TextView) {
                                addViewInLayout(childView, i, generateDefaultLayoutParams(), true);
                            } else {
                                addViewInLayout(createItemView(commentSpan, i, false), i, generateDefaultLayoutParams(), true);
                            }
                        }
                    }
                } else {
                    setCommentData(childView, commentSpan, i, isShowTranslation);
                }
            }
            requestLayout();
        }
    }


    private View createItemView(SpannableStringBuilder content, int index, boolean isShowTranslation) {
        if (isShowTranslation) {
            CommentTranslationLayoutView translationLayoutView = new CommentTranslationLayoutView(getContext());
            translationLayoutView.setOnItemClickPopupMenuListener(this).setCurrentPosition(index);
            setCommentData(translationLayoutView, content, 0, true);
            return translationLayoutView;
        } else {
            TextView textView = createContentTextView(index);
            textView.setText(content);
            return textView;
        }
    }

    private TextView createContentTextView(int index) {
        TextView textView = new TextView(getContext());
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.base_333333));
        textView.setBackgroundResource(R.drawable.selector_view_name_state);
        textView.setTextSize(16f);
        textView.setPadding(0, mVerticalSpace, 0, mVerticalSpace);
        textView.setLineSpacing(3f, 1f);
        textView.setOnLongClickListener(v -> {
            Utils.showPopupMenu(getContext(), this, index, v, TranslationState.START);
            return false;
        });
        return textView;
    }

    private SimpleWeakObjectPool<View> COMMENT_TEXT_POOL;

    @Override
    public void onChildViewAdded(View parent, View child) {

    }

    @Override
    public void onChildViewRemoved(View parent, View child) {
        COMMENT_TEXT_POOL.put(child);
    }

    @Override
    public void onItemClickCopy(int position) {
        Toast.makeText(getContext(), "You Click" + position + " Copy!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickTranslation(int position) {
        Toast.makeText(getContext(), "You Click" + position + "  Translation!", Toast.LENGTH_SHORT).show();
        if (mCommentBeans != null && position < mCommentBeans.size()) {
            mCommentBeans.get(position).setShowContentTranslation(true);
            addComments(mCommentBeans);
        }
    }

    @Override
    public void onItemClickHideTranslation(int position) {
        Toast.makeText(getContext(), "You Click" + position + "  Hide Translation!", Toast.LENGTH_SHORT).show();
        if (mCommentBeans != null && position < mCommentBeans.size()) {
            mCommentBeans.get(position).setShowContentTranslation(false);
            addComments(mCommentBeans);
        }
    }

    @Override
    public void onItemClickCollection(int position) {
        Toast.makeText(getContext(), "You Click" + position + "  Collection!", Toast.LENGTH_SHORT).show();
    }


    final class SimpleWeakObjectPool<T> {

        private WeakReference<T>[] objsPool;
        private int size;
        private int curPointer = -1;


        public SimpleWeakObjectPool() {
            this(5);
        }

        public SimpleWeakObjectPool(int size) {
            this.size = size;
            objsPool = (WeakReference<T>[]) Array.newInstance(WeakReference.class, size);
        }

        public synchronized T get() {
            if (curPointer == -1 || curPointer > objsPool.length) return null;
            T obj = objsPool[curPointer].get();
            objsPool[curPointer] = null;
            curPointer--;
            return obj;
        }

        public synchronized boolean put(T t) {
            if (curPointer == -1 || curPointer < objsPool.length - 1) {
                curPointer++;
                objsPool[curPointer] = new WeakReference<T>(t);
                return true;
            }
            return false;
        }

        public void clearPool() {
            for (int i = 0; i < objsPool.length; i++) {
                objsPool[i].clear();
                objsPool[i] = null;
            }
            curPointer = -1;
        }

        public int size() {
            return objsPool == null ? 0 : objsPool.length;
        }

    }

    private void setCommentData(View view, SpannableStringBuilder builder, int index, boolean isShowTranslation) {
        if (view instanceof CommentTranslationLayoutView) {
            if (isShowTranslation) {
                CommentTranslationLayoutView translationLayoutView = (CommentTranslationLayoutView) view;
                translationLayoutView.setSourceContent(builder);
                translationLayoutView.setTranslationContent(builder);
            } else {
                addViewInLayout(createItemView(builder, index, false), index, generateDefaultLayoutParams(), true);
                removeViewInLayout(view);
            }
        } else if (view instanceof TextView) {
            if (isShowTranslation) {
                addViewInLayout(createItemView(builder, index, true), index, generateDefaultLayoutParams(), true);
                removeViewInLayout(view);
            } else {
                ((TextView) view).setText(builder);
            }
        }
    }
}
