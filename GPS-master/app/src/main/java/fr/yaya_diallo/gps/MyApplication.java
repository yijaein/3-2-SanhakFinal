package fr.yaya_diallo.gps;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jan on 2016-11-15.
 */

public class MyApplication extends Application {
    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}