package com.j1j2.pifalao.app.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by 兴昊 on 2015/12/17.
 */
public final class GsonProvider {

    private GsonProvider() {
        // singleton
    }

    public static Gson provideGson() {
        return GsonHolder.sGson;
    }

    private static class GsonHolder {
        // lazy instantiate
        private static volatile Gson sGson = new GsonBuilder()
                .serializeNulls()
                .create();
    }
}

