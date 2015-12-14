package org.ligboy.demo.stetho.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp.OkHttpGlideModule;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;

import org.ligboy.demo.stetho.util.OkHttpUtil;

import java.io.InputStream;

/**
 * Custom Glide Module
 * @author Ligboy.Liu ligboy@gmail.com.
 */
public class GlideModule extends OkHttpGlideModule {
    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(OkHttpUtil.getClient()));
    }
}
