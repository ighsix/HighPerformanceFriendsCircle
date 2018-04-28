package com.kcrason.highperformancefriendscircle;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

/**
 * @author KCrason
 * @date 2018/4/28
 */
public class UserNameClickableSpan extends ClickableSpan {

    private Context mContext;

    private String mUserName;

    public UserNameClickableSpan(Context context, String userName) {
        this.mContext = context;
        this.mUserName = userName;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(ContextCompat.getColor(mContext, R.color.base_697A9F));
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {
        Toast.makeText(mContext, "You Click " + mUserName, Toast.LENGTH_SHORT).show();
    }
}
