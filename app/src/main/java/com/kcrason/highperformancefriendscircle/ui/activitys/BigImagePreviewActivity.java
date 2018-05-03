package com.kcrason.highperformancefriendscircle.ui.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.kcrason.highperformancefriendscircle.R;
import com.kcrason.highperformancefriendscircle.Utils;
import java.util.ArrayList;
import java.util.List;
import ch.ielse.view.imagewatcher.ImageWatcher;

public class BigImagePreviewActivity extends AppCompatActivity implements ImageWatcher.OnPictureLongPressListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image_preview);
    }

    @Override
    public void onPictureLongPress(ImageView v, String url, int pos) {

    }
}
