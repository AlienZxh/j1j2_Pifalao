package com.j1j2.pifalao.app.di;

import com.j1j2.data.model.User;
import com.j1j2.pifalao.app.UserScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alienzxh on 16-3-11.
 */
@Module
public class UserModule {
    private User user;

    public UserModule(User user) {
        this.user = user;
    }

    @Provides
    @UserScope
    User provideUser() {
        return user;
    }
}
