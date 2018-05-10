package com.kcrason.highperformancefriendscircle.ui.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kcrason.highperformancefriendscircle.R;
import com.kcrason.highperformancefriendscircle.widgets.TestLayout;


public class EmojiPanelActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji_panel);
        TestLayout testLayout = findViewById(R.id.test_layout);
        findViewById(R.id.btn_add_view).setOnClickListener(v -> {
            for (int i = 0; i < 10; i++) {
                TextView textView = new TextView(this);
                String tag = "这是TestLayou添加子View时调用的";
                textView.setText(tag);
                testLayout.setTag(tag);
                testLayout.addViewInLayout(textView, i,
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT), true);
            }
            //添加View之后requestLayout一次
            testLayout.requestLayout();
        });
    }
}
