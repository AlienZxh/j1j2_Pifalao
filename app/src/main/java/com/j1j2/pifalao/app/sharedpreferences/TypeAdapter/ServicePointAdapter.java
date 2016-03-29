package com.j1j2.pifalao.app.sharedpreferences.TypeAdapter;

import android.support.annotation.Nullable;

import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.app.provider.GsonProvider;

import net.orange_box.storebox.adapters.base.BaseStringTypeAdapter;

/**
 * Created by alienzxh on 16-3-25.
 */
public class ServicePointAdapter extends BaseStringTypeAdapter<ServicePoint> {
    @Nullable
    @Override
    public String adaptForPreferences(ServicePoint value) {
        return GsonProvider.provideGson().toJson(value);
    }

    @Nullable
    @Override
    public ServicePoint adaptFromPreferences(String value) {
        return GsonProvider.provideGson().fromJson(value, ServicePoint.class);
    }
}
