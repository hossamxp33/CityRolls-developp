package com.codesroots.osamaomar.cityrolls.helper;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;


public class MyApplication extends Application {

    public static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication mInstance;
    public static Context context;
    PreferenceHelper preferenceHelper;
    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
        context = getApplicationContext();
        preferenceHelper  = new PreferenceHelper(context);
//        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        mInstance = this;
//        PreferenceHelper preferenceHelper=new PreferenceHelper(mInstance);
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/JF-Flat-Regular.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
        if (ResourceUtil.getCurrentLanguage(this).matches("ar")) {
            ResourceUtil.changeLang("ar", this);

        } else {
            ResourceUtil.changeLang("en", this);

        }
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }



}

