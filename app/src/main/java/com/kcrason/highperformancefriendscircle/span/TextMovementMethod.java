package com.kcrason.highperformancefriendscircle.span;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

public class TextMovementMethod extends LinkMovementMethod {

    private TextClickSpan mTextClickSpan;

    //记录开始按下的时间
    private long mStartTime;

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mStartTime = System.currentTimeMillis();
            mTextClickSpan = getTextSpan(widget, buffer, event);
            if (mTextClickSpan != null) {
                mTextClickSpan.setPressed(true);
                Selection.setSelection(buffer, buffer.getSpanStart(mTextClickSpan), buffer.getSpanEnd(mTextClickSpan));
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            TextClickSpan touchTextClickSpan = getTextSpan(widget, buffer, event);
            if (mTextClickSpan != null && touchTextClickSpan != mTextClickSpan) {
                mTextClickSpan.setPressed(false);
                mTextClickSpan = null;
                Selection.removeSelection(buffer);
            }
        } else {
            if (mTextClickSpan != null) {
                mTextClickSpan.setPressed(false);
                mTextClickSpan = null;
                Selection.removeSelection(buffer);
                /**
                 *  当用户长按span时，不响应相应的点击事件。判断规则为从开始到结束的时间是否大于500ms
                 */
                if (System.currentTimeMillis() - mStartTime < 500) {
                    super.onTouchEvent(widget, buffer, event);
                }
            }
        }
        return true;
    }

    /**
     * 得到匹配的span
     *
     * @param widget
     * @param spannable
     * @param event
     * @return
     */
    private TextClickSpan getTextSpan(TextView widget, Spannable spannable, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        x -= widget.getTotalPaddingLeft();
        y -= widget.getTotalPaddingTop();

        x += widget.getScrollX();
        y += widget.getScrollY();

        Layout layout = widget.getLayout();

        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);

        TextClickSpan[] link = spannable.getSpans(off, off, TextClickSpan.class);
        TextClickSpan touchedSpan = null;
        if (link.length > 0) {
            touchedSpan = link[0];
        }
        return touchedSpan;
    }
}
