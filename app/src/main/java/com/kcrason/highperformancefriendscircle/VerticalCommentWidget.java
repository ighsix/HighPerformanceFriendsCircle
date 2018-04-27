package com.kcrason.highperformancefriendscircle;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * @author KCrason
 * @date 2018/4/27
 */
public class VerticalCommentWidget extends LinearLayout {
    public VerticalCommentWidget(Context context) {
        super(context);
    }

    public VerticalCommentWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalCommentWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void addComments(List<CommentBean> commentBeans) {
        if (commentBeans != null) {
            int oldCount = getChildCount();
            int newCount = commentBeans.size();
            if (oldCount < newCount) {
                addCommentContent(oldCount, commentBeans);
                int difCount = newCount - oldCount;
                for (int i = oldCount; i < difCount; i++) {
                    TextView itemView = createItemView(commentBeans.get(oldCount).getCommentContent());
                    addViewInLayout(itemView, i - oldCount, generateDefaultLayoutParams(), true);
                }
            } else {
                removeViewsInLayout(newCount, oldCount - newCount);
                addCommentContent(newCount, commentBeans);
            }
            requestLayout();
        }
    }


    private TextView createItemView(String content) {
        TextView itemView = new TextView(getContext());
        itemView.setPadding(0,12,0,12);
        itemView.setTextColor(Color.parseColor("#697A9F"));
        itemView.setText(content);
        return itemView;
    }


    private String generateCommentContent(List<CommentBean> commentBeans, int index) {
        if (index < commentBeans.size()) {
            CommentBean commentBean = commentBeans.get(index);
            String childUserName = commentBean.getChildUserName();
            String parentUserName = commentBean.getParentUserName();
            String commentContent = commentBean.getCommentContent();
            if (commentBean.getCommentType() == Constants.CommentType.COMMENT_TYPE_SINGLE) {
                return childUserName + "：" + commentContent;
            } else {
                return parentUserName + " 回复 " + childUserName + "：" + commentContent;
            }
        }
        return null;
    }


    private void addCommentContent(int itemCount, List<CommentBean> commentBeans) {
        for (int i = 0; i < itemCount; i++) {
            TextView cacheChildView = (TextView) getChildAt(i);
            cacheChildView.setText(generateCommentContent(commentBeans, i));
        }
    }
}
