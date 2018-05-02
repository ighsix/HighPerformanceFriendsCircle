package com.kcrason.highperformancefriendscircle.ui.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.kcrason.highperformancefriendscircle.R;

public class BigImagePreviewActivity extends AppCompatActivity {
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image_preview);
    }
}
