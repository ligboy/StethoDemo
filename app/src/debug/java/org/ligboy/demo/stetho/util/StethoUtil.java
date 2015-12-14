package org.ligboy.demo.stetho.util;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;

/**
 * @author Ligboy.Liu ligboy@gmail.com.
 */
public class StethoUtil {
    /**
     * 初始化Stetho
     * Initialize Stetho
     * @param application Application
     */
    public static void initialize(Application application) {
        Stetho.initialize(
                Stetho.newInitializerBuilder(application)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(application))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(application))
                        .build());
    }

    /**
     * 设置OkHttp Stetho诊断
     * Setup Stetho for OkHttp
     * <p/>Same as {@link StethoUtil#setupOkHttp(List)}
     * @param client OkHttpClient
     */
    public static void setupOkHttp(OkHttpClient client) {
        setupOkHttp(client.networkInterceptors());
    }

    /**
     * 设置OkHttp Stetho诊断
     * Setup Stetho for OkHttp
     * <p/>Same as {@link StethoUtil#setupOkHttp(List)}
     * @param interceptors OkHttp network interceptors
     */
    public static void setupOkHttp(List<Interceptor> interceptors) {
        interceptors.add(new StethoInterceptor());
    }
}
