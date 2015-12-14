package org.ligboy.demo.stetho.util;

import com.squareup.okhttp.OkHttpClient;

/**
 *
 * @author Ligboy.Liu ligboy@gmail.com.
 */
public final class OkHttpUtil {
    private static volatile OkHttpClient internalClient;
    private OkHttpClient client;

    /**
     * 获取全局OkHttpClient
     * @return OkHttpClient
     */
    public static OkHttpClient getClient() {
        if (internalClient == null) {
            synchronized (OkHttpUtil.class) {
                if (internalClient == null) {
                    internalClient = new OkHttpClient();
                    StethoUtil.setupOkHttp(internalClient);
                }
            }
        }
        return internalClient;
    }
}
