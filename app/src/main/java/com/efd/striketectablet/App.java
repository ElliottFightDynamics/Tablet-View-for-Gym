package com.efd.striketectablet;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.efd.striketectablet.util.PresetUtil;
import com.efd.striketectablet.util.StatisticUtil;


/**
 * Created by user on 20.07.15.
 */
public class App extends MultiDexApplication {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(getApplicationContext());
        context = getApplicationContext();
        StatisticUtil.init(context);
        PresetUtil.init();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public static Context getContext() {
        return context;
    }
}