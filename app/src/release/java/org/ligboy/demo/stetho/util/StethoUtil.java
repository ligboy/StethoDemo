package org.ligboy.demo.stetho.util;

import android.app.Application;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;

/**
 * @author Ligboy.Liu ligboy@gmail.com.
 */
public class StethoUtil {
    /**
     * 初始化Stetho(不做任何实际操作)
     * Initialize Stetho, but do nothing.
     * <p/>Do nothing
     * @param application Application
     */
    public static void initialize(Application application) {
        /* no-op */
    }

    /**
     * 设置OkHttp Stetho诊断(不做任何实际操作)
     * Setup Stetho for OkHttp, but do nothing.
     * <p/>Same as {@link StethoUtil#setupOkHttp(List)}
     * <p/>Do nothing
     * @param client OkHttpClient
     */
    public static void setupOkHttp(OkHttpClient client) {
        /* no-op */
    }

    /**
     * 设置OkHttp Stetho诊断(不做任何实际操作)
     * Setup Stetho for OkHttp, but do nothing.
     * <p/>Same as {@link StethoUtil#setupOkHttp(List)}
     * <p/>Do nothing
     * @param interceptors OkHttp network interceptors
     */
    public static void setupOkHttp(List<Interceptor> interceptors) {
        /* no-op */
    }
}
