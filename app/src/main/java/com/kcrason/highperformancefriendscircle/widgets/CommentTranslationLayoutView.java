package com.kcrason.highperformancefriendscircle.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kcrason.highperformancefriendscircle.R;
import com.kcrason.highperformancefriendscircle.Utils;
import com.kcrason.highperformancefriendscircle.enums.TranslationState;
import com.kcrason.highperformancefriendscircle.interfaces.OnItemClickPopupMenuListener;
import com.kcrason.highperformancefriendscircle.span.TextMovementMothod;

public class CommentTranslationLayoutView extends LinearLayout implements View.OnLongClickListener {

    private TextView mTxtSourceContent;
    private TextView mTxtTranslationContent;
    private LinearLayout mLayoutTranslation;
    private OnItemClickPopupMenuListener mOnItemClickPopupMenuListener;
    private int mCurrentPosition;

    public CommentTranslationLayoutView(Context context) {
        super(context);
        init(context, null);
    }

    public CommentTranslationLayoutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CommentTranslationLayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet set) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.view_comment_translation_layout, this, false);
        mLayoutTranslation = itemView.findViewById(R.id.layout_translation);
        mTxtSourceContent = itemView.findViewById(R.id.txt_source_content);
        mTxtSourceContent.setOnLongClickListener(v -> {
            Utils.showPopupMenu(getContext(), mOnItemClickPopupMenuListener, mCurrentPosition, v, TranslationState.END);
            return false;
        });
        mTxtSourceContent.setMovementMethod(new TextMovementMothod());
        mTxtTranslationContent = itemView.findViewById(R.id.txt_translation_content);
        mTxtTranslationContent.setOnLongClickListener(v -> {
            Utils.showPopupMenu(getContext(), mOnItemClickPopupMenuListener, mCurrentPosition, v, TranslationState.END);
            return false;
        });
        mTxtTranslationContent.setMovementMethod(new TextMovementMothod());
        setBackgroundResource(R.drawable.selector_view_name_state);
        mTxtSourceContent.setBackgroundResource(R.drawable.selector_view_name_state);
        mTxtTranslationContent.setBackgroundResource(R.drawable.selector_view_name_state);
        addView(itemView);
    }

    public CommentTranslationLayoutView setOnItemClickPopupMenuListener(OnItemClickPopupMenuListener mOnItemClickPopupMenuListener) {
        this.mOnItemClickPopupMenuListener = mOnItemClickPopupMenuListener;
        return this;
    }


    public CommentTranslationLayoutView setCurrentPosition(int mCurrentPosition) {
        this.mCurrentPosition = mCurrentPosition;
        return this;
    }

    public CommentTranslationLayoutView setShowTranslationLayout(boolean isShow) {
        if (isShow) {
            showTranslationLayout();
        } else {
            hideTranslationLayout();
        }
        return this;
    }


    public void showTranslationLayout() {
        if (mLayoutTranslation != null) {
            mLayoutTranslation.setVisibility(VISIBLE);
        }
    }

    public void hideTranslationLayout() {
        if (mLayoutTranslation != null) {
            mLayoutTranslation.setVisibility(GONE);
        }
    }


    public CommentTranslationLayoutView setSourceContent(SpannableStringBuilder builder) {
        if (mTxtSourceContent != null) {
            mTxtSourceContent.setText(builder);
        }
        return this;
    }

    public CommentTranslationLayoutView setTranslationContent(SpannableStringBuilder builder) {
        if (mTxtTranslationContent != null) {
            mTxtTranslationContent.setText(builder);
        }
        return this;
    }


    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.txt_translation_content:
                break;
            default:
                break;
        }
        return true;
    }
}
