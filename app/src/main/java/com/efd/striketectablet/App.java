package com.efd.striketectablet;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.efd.striketectablet.api.RetrofitSingleton;
import com.efd.striketectablet.util.ComboSetUtil;
import com.efd.striketectablet.util.PresetUtil;
import com.efd.striketectablet.util.StatisticUtil;


/**
 * Created by super on 20.05.17.
 */
public class App extends MultiDexApplication {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(getApplicationContext());
        context = getApplicationContext();

        RetrofitSingleton.setApplication(this);

        StatisticUtil.init(context);
        PresetUtil.init();
        ComboSetUtil.init(context);
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