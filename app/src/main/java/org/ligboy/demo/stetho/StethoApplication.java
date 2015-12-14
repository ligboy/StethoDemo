package org.ligboy.demo.stetho;

import android.app.Application;

import org.ligboy.demo.stetho.util.StethoUtil;

/**
 * @author Ligboy.Liu ligboy@gmail.com.
 */
public class StethoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        StethoUtil.initialize(this);
    }
}
