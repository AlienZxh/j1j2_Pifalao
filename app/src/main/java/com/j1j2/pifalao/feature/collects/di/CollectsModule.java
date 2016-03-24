package com.j1j2.pifalao.feature.collects.di;

import com.j1j2.data.http.api.UserFavoriteApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.collects.CollectsActivity;
import com.j1j2.pifalao.feature.collects.CollectsViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-24.
 */
@Module
public class CollectsModule {

    private CollectsActivity collectsActivity;

    public CollectsModule(CollectsActivity collectsActivity) {
        this.collectsActivity = collectsActivity;
    }

    @Provides
    @ActivityScope
    UserFavoriteApi userFavoriteApi(Retrofit retrofit) {
        return retrofit.create(UserFavoriteApi.class);
    }

    @Provides
    @ActivityScope
    CollectsActivity collectsActivity() {
        return collectsActivity;
    }

    @Provides
    @ActivityScope
    CollectsViewModel collectsViewModel(CollectsActivity collectsActivity, UserFavoriteApi userFavoriteApi) {
        return new CollectsViewModel(collectsActivity, userFavoriteApi);
    }
}
