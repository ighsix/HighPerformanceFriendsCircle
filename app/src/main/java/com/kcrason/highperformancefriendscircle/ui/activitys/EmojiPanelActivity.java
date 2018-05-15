package com.kcrason.highperformancefriendscircle.ui.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kcrason.highperformancefriendscircle.R;
import com.kcrason.highperformancefriendscircle.others.DataCenter;
import com.kcrason.highperformancefriendscircle.widgets.EmojiPanelView;


public class EmojiPanelActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji_panel);
        EmojiPanelView emojiPanelView = findViewById(R.id.emoji_panel_view);
        emojiPanelView.initEmojiPanel(DataCenter.emojiDataSources);
        findViewById(R.id.show).setOnClickListener(v -> emojiPanelView.showEmojiPanel());
    }
}
