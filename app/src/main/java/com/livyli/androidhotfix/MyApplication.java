package com.livyli.androidhotfix;

import android.app.Application;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import cn.jiajixin.nuwa.Nuwa;

/**
 * Created by livy on 16/1/18.
 */
public class MyApplication extends Application {
    public static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        try {
            Nuwa.initial(this);
            Nuwa.loadPatch(this, Environment.getExternalStorageDirectory().getAbsolutePath().concat("/Patch/patch.apk"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("MyApplication", "test");
    }

    public void test() {
        Toast.makeText(getApplicationContext(), "buf fix", Toast.LENGTH_SHORT).show();
    }

}
