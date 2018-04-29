package com.kcrason.highperformancefriendscircle;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.TypedValue;

import com.kcrason.highperformancefriendscircle.beans.PraiseBean;
import com.kcrason.highperformancefriendscircle.span.TextClickSpan;
import com.kcrason.highperformancefriendscircle.span.VerticalImageSpan;

import java.util.List;

/**
 * @author KCrason
 * @date 2018/4/28
 */
public class Utils {
    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    public static SpannableStringBuilder makePraiseNameRichText(Context context, List<PraiseBean> praiseBeans) {
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
                    builder.append(",");
                }
                builder.setSpan(new TextClickSpan(context, praiseUserName), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.heart_drawable_blue);
            if (drawable != null) {
                int size = Utils.dp2px(context,16f);
                drawable.setBounds(0, 0, size, size);
            }
            builder.setSpan(new VerticalImageSpan(drawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return builder;
        }
        return null;
    }
}
