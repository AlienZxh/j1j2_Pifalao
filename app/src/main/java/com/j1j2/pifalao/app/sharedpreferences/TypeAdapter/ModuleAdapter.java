package com.j1j2.pifalao.app.sharedpreferences.TypeAdapter;

import android.support.annotation.Nullable;

import com.j1j2.data.model.City;
import com.j1j2.data.model.Module;
import com.j1j2.pifalao.app.provider.GsonProvider;

import net.orange_box.storebox.adapters.base.BaseStringTypeAdapter;

/**
 * Created by alienzxh on 16-4-11.
 */
public class ModuleAdapter extends BaseStringTypeAdapter<Module> {

    @Nullable
    @Override
    public String adaptForPreferences(@Nullable Module value) {
        return GsonProvider.provideGson().toJson(value);
    }

    @Nullable
    @Override
    public Module adaptFromPreferences(@Nullable String value) {
        return GsonProvider.provideGson().fromJson(value, Module.class);
    }
}
