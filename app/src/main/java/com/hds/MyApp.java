package com.hds;

import android.app.Application;
import android.os.Looper;
import android.util.Log;
import android.util.Printer;

/**
 * Created by hds on 17-11-22.
 */

public class MyApp
        extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Looper.getMainLooper().dump(new Printer() {
            @Override
            public void println(String x) {
                Log.d("MyApp", "MyApp:" + x);
            }
        }, "crop");
    }
}
