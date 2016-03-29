package com.j1j2.pifalao.feature.account.di;

import com.j1j2.data.http.api.UserVipApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.account.AccountActivity;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-28.
 */
@Module
public class AccountModule {
    private AccountActivity accountActivity;

    public AccountModule(AccountActivity accountActivity) {
        this.accountActivity = accountActivity;
    }
    @Provides
    @ActivityScope
    AccountActivity accountActivity() {
        return accountActivity;
    }
}
