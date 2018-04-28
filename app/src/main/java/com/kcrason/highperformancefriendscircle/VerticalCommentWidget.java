package com.kcrason.highperformancefriendscircle;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * @author KCrason
 * @date 2018/4/27
 */
public class VerticalCommentWidget extends LinearLayout {

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
        mVerticalSpace = Utils.dp2px(context, 2f);
    }


    public void addComments(List<CommentBean> commentBeans) {
        if (commentBeans != null) {
            int oldCount = getChildCount();
            int newCount = commentBeans.size();
            if (oldCount > newCount) {
                removeViewsInLayout(newCount, oldCount - newCount);
            }
            for (int i = 0; i < newCount; i++) {
                boolean hasChild = i < oldCount;
                TextView childView = hasChild ? (TextView) getChildAt(i) : null;
                if (childView == null) {
                    childView = createItemView(generateCommentContent(commentBeans, i));
                    addViewInLayout(childView, i, generateDefaultLayoutParams(), true);
                } else {
                    childView.setText(generateCommentContent(commentBeans, i));
                }
            }
            requestLayout();
        }
    }


    private TextView createItemView(SpannableStringBuilder content) {
        TextView itemView = new TextView(getContext());
        itemView.setMovementMethod(LinkMovementMethod.getInstance());
        itemView.setPadding(0, mVerticalSpace, 0, mVerticalSpace);
        itemView.setTextSize(16f);
        itemView.setTextColor(ContextCompat.getColor(getContext(), R.color.base_333333));
        itemView.setText(content);
        return itemView;
    }


    private SpannableStringBuilder generateCommentContent(List<CommentBean> commentBeans, int index) {
        if (index < commentBeans.size()) {
            CommentBean commentBean = commentBeans.get(index);
            return commentBean.getRichTextCommentContent();
        }
        return null;
    }
}
