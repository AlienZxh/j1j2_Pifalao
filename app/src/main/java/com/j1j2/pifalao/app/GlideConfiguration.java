package com.j1j2.pifalao.app;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by alienzxh on 16-9-28.
 */

public class GlideConfiguration implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

        int cacheSize150MegaBytes = 157286400;
        String downloadDirectoryPath = Constant.FilePath.cacheImgFolder;
        builder.setDiskCache(
                new DiskLruCacheFactory(downloadDirectoryPath, cacheSize150MegaBytes)
        );
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
