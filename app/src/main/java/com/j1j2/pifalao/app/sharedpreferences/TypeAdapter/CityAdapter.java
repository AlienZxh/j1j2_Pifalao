package com.j1j2.pifalao.app.sharedpreferences.TypeAdapter;

import android.support.annotation.Nullable;

import com.j1j2.data.model.City;
import com.j1j2.pifalao.app.provider.GsonProvider;

import net.orange_box.storebox.adapters.base.BaseStringTypeAdapter;

/**
 * Created by alienzxh on 16-3-25.
 */
public class CityAdapter extends BaseStringTypeAdapter<City> {
    @Nullable
    @Override
    public String adaptForPreferences(City value) {
        return GsonProvider.provideGson().toJson(value);
    }

    @Nullable
    @Override
    public City adaptFromPreferences(String value) {
        return GsonProvider.provideGson().fromJson(value, City.class);
    }
}
