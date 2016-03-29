package com.j1j2.pifalao.app.sharedpreferences.TypeAdapter;

import android.support.annotation.Nullable;

import com.j1j2.data.model.User;
import com.j1j2.pifalao.app.provider.GsonProvider;

import net.orange_box.storebox.adapters.base.BaseStringTypeAdapter;

/**
 * Created by alienzxh on 16-3-25.
 */
public class UserInfoAdapter extends BaseStringTypeAdapter<User> {
    @Nullable
    @Override
    public String adaptForPreferences(User value) {
        return GsonProvider.provideGson().toJson(value);
    }

    @Nullable
    @Override
    public User adaptFromPreferences(String value) {
        return GsonProvider.provideGson().fromJson(value, User.class);
    }
}
