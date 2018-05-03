package com.kcrason.highperformancefriendscircle;

import android.app.Application;
import android.content.Context;

/**
 * @author KCrason
 * @date 2018/5/3
 */
public class FriendsCircleApplication extends Application {

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }
}
