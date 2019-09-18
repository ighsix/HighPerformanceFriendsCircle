package com.kcrason.highperformancefriendscircle.utils;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;

import com.kcrason.highperformancefriendscircle.R;
import com.kcrason.highperformancefriendscircle.beans.PraiseBean;
import com.kcrason.highperformancefriendscircle.span.TextClickSpan;
import com.kcrason.highperformancefriendscircle.span.VerticalImageSpan;

import java.util.List;

/**
 * @author KCrason
 * @date 2018/5/2
 */
public class SpanUtils {

    public static SpannableStringBuilder makeSingleCommentSpan(Context context, String childUserName, String commentContent) {
        String richText = String.format("%s: %s", childUserName, commentContent);
        SpannableStringBuilder builder = new SpannableStringBuilder(richText);
        if (!TextUtils.isEmpty(childUserName)) {
            builder.setSpan(new TextClickSpan(context, childUserName), 0, childUserName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    public static SpannableStringBuilder makeReplyCommentSpan(Context context, String parentUserName, String childUserName, String commentContent) {
        String richText = String.format("%s回复%s: %s", parentUserName, childUserName, commentContent);
        SpannableStringBuilder builder = new SpannableStringBuilder(richText);
        int parentEnd = 0;
        if (!TextUtils.isEmpty(parentUserName)) {
            parentEnd = parentUserName.length();
            builder.setSpan(new TextClickSpan(context, parentUserName), 0, parentEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (!TextUtils.isEmpty(childUserName)) {
            int childStart = parentEnd + 2;
            int childEnd = childStart + childUserName.length();
            builder.setSpan(new TextClickSpan(context, childUserName), childStart, childEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    public static SpannableStringBuilder makePraiseSpan(Context context, List<PraiseBean> praiseBeans) {
        if (praiseBeans != null && praiseBeans.size() > 0) {
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append("  ");
            int praiseSize = praiseBeans.size();
            for (int i = 0; i < praiseSize; i++) {
                PraiseBean praiseBean = praiseBeans.get(i);
                String praiseUserName = praiseBean.getPraiseUserName();
                int start = builder.length();
                int end = start + praiseUserName.length();
                builder.append(praiseUserName);
                if (i != praiseSize - 1) {
                    builder.append(", ");
                }
                builder.setSpan(new TextClickSpan(context, praiseUserName), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            builder.setSpan(new VerticalImageSpan(ContextCompat.getDrawable(context, R.drawable.heart_drawable_blue)),
                    0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return builder;
        }
        return null;
    }
}
