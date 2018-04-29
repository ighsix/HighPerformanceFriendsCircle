package com.kcrason.highperformancefriendscircle.widgets;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.kcrason.highperformancefriendscircle.R;
import com.kcrason.highperformancefriendscircle.Utils;
import com.kcrason.highperformancefriendscircle.interfaces.OnPraiseOrCommentClickListener;

public class CommentOrPraisePopupWindow extends PopupWindow implements View.OnClickListener {

    private Context mContext;

    private OnPraiseOrCommentClickListener mOnPraiseOrCommentClickListener;

    private int mPopupWindowHeight;
    private int mPopupWindowWidth;

    public CommentOrPraisePopupWindow(Builder builder) {
        this.mContext = builder.context;
        this.mOnPraiseOrCommentClickListener = builder.onPraiseOrCommentClickListener;
        View contentView = LayoutInflater.from(builder.context).inflate(R.layout.popup_window_praise_or_comment_view, null);
        this.setContentView(contentView);
        contentView.findViewById(R.id.layout_praise).setOnClickListener(this);
        contentView.findViewById(R.id.layout_comment).setOnClickListener(this);
        //不设置宽高将无法显示popupWindow
        this.mPopupWindowHeight = Utils.dp2px(mContext, 38);
        this.mPopupWindowWidth = Utils.dp2px(mContext, 190);
        this.setHeight(mPopupWindowHeight);
        this.setWidth(mPopupWindowWidth);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.PraiseOrCommentAnimationStyle);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
    }

    public static class Builder {
        private Context context;

        private OnPraiseOrCommentClickListener onPraiseOrCommentClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setOnPraiseOrCommentClickListener(OnPraiseOrCommentClickListener onPraiseOrCommentClickListener) {
            this.onPraiseOrCommentClickListener = onPraiseOrCommentClickListener;
            return this;
        }

        public CommentOrPraisePopupWindow build() {
            return new CommentOrPraisePopupWindow(this);
        }
    }

    public void showPopupWindow(View anchor) {
        if (anchor == null) {
            return;
        }
        int[] location = new int[2];
        anchor.getLocationOnScreen(location);
        int xOffset = location[0] - mPopupWindowWidth - Utils.dp2px(mContext, 10f);
        int yOffset = location[1] + (anchor.getHeight() - mPopupWindowHeight) / 2;
        showAtLocation(anchor, Gravity.NO_GRAVITY, xOffset, yOffset);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.layout_praise:
                if (mOnPraiseOrCommentClickListener != null) {
                    mOnPraiseOrCommentClickListener.onPraiseClick();
                }
                break;
            case R.id.layout_comment:
                if (mOnPraiseOrCommentClickListener != null) {
                    mOnPraiseOrCommentClickListener.onCommentClick();
                }
                break;
            default:
                break;
        }
    }
}
