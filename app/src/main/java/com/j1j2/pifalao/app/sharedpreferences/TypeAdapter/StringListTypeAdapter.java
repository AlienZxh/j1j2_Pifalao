package com.j1j2.pifalao.app.sharedpreferences.TypeAdapter;

import android.support.annotation.Nullable;

import com.google.gson.reflect.TypeToken;
import com.j1j2.pifalao.app.provider.GsonProvider;

import net.orange_box.storebox.adapters.base.BaseStringTypeAdapter;

import java.util.List;

/**
 * Created by alienzxh on 16-3-29.
 */
public class StringListTypeAdapter extends BaseStringTypeAdapter<List<String>> {
    @Nullable
    @Override
    public String adaptForPreferences(List<String> value) {
        return GsonProvider.provideGson().toJson(value);
    }

    @Nullable
    @Override
    public List<String> adaptFromPreferences(String value) {
        return GsonProvider.provideGson().fromJson(value, new TypeToken<List<String>>() {
        }.getType());
    }
}
