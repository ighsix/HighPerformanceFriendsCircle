package com.kcrason.highperformancefriendscircle.widgets;

import android.content.Context;
import androidx.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kcrason.highperformancefriendscircle.R;
import com.kcrason.highperformancefriendscircle.utils.Utils;
import com.kcrason.highperformancefriendscircle.enums.TranslationState;
import com.kcrason.highperformancefriendscircle.interfaces.OnItemClickPopupMenuListener;
import com.kcrason.highperformancefriendscircle.span.TextMovementMethod;

public class CommentTranslationLayoutView extends LinearLayout implements View.OnLongClickListener {

    private TextView mTxtSourceContent;
    private TextView mTxtTranslationContent;
    private ImageView mTranslationTag;
    private View mDivideLine;
    private TextView mTranslationDesc;

    private OnItemClickPopupMenuListener mOnItemClickPopupMenuListener;
    private int mCurrentPosition;

    public CommentTranslationLayoutView(Context context) {
        super(context);
        init(context);
    }

    public CommentTranslationLayoutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CommentTranslationLayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.view_comment_translation_layout, this, false);
        mDivideLine = itemView.findViewById(R.id.view_divide_line);
        mTranslationTag = itemView.findViewById(R.id.img_translating);
        mTranslationDesc = itemView.findViewById(R.id.txt_translation_desc);
        mTxtSourceContent = itemView.findViewById(R.id.txt_source_content);
        mTxtSourceContent.setOnLongClickListener(this);
        mTxtSourceContent.setMovementMethod(new TextMovementMethod());
        mTxtTranslationContent = itemView.findViewById(R.id.txt_translation_content);
        mTxtTranslationContent.setOnLongClickListener(this);
        mTxtTranslationContent.setMovementMethod(new TextMovementMethod());
        setBackgroundResource(R.drawable.selector_view_name_state);
        mTxtSourceContent.setBackgroundResource(R.drawable.selector_view_name_state);
        mTxtTranslationContent.setBackgroundResource(R.drawable.selector_view_name_state);
        addView(itemView);
    }

    public CommentTranslationLayoutView setTranslationState(TranslationState translationState, boolean isStartAnimation) {
        switch (translationState) {
            case CENTER:
                mTranslationTag.setVisibility(VISIBLE);
                mDivideLine.setVisibility(GONE);
                mTranslationDesc.setText("翻译中");
                mTxtTranslationContent.setVisibility(GONE);
                Utils.startAlphaAnimation(mTranslationDesc, isStartAnimation);
                break;
            case END:
                mTranslationTag.setVisibility(GONE);
                mDivideLine.setVisibility(VISIBLE);
                mTranslationDesc.setText("已翻译");
                mTxtTranslationContent.setVisibility(VISIBLE);
                Utils.startAlphaAnimation(mTxtTranslationContent, isStartAnimation);
                break;
        }
        return this;
    }

    public CommentTranslationLayoutView setOnItemClickPopupMenuListener(OnItemClickPopupMenuListener mOnItemClickPopupMenuListener) {
        this.mOnItemClickPopupMenuListener = mOnItemClickPopupMenuListener;
        return this;
    }


    public CommentTranslationLayoutView setCurrentPosition(int mCurrentPosition) {
        this.mCurrentPosition = mCurrentPosition;
        return this;
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
            case R.id.txt_source_content:
            case R.id.txt_translation_content:
                Utils.showPopupMenu(getContext(), mOnItemClickPopupMenuListener, mCurrentPosition, v, TranslationState.END);
                break;
            default:
                break;
        }
        return true;
    }
}
