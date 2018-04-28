package com.kcrason.highperformancefriendscircle;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.TypedValue;
import android.view.View;

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
                builder.setSpan(new UserNameClickableSpan(context, praiseUserName), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.heart_drawable);
            drawable.setBounds(0, 0, 50, 50);
            builder.setSpan(new ImageSpan(drawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return builder;
        }
        return null;
    }
}
