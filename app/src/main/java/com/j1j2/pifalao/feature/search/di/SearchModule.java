package com.j1j2.pifalao.feature.search.di;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.search.SearchActivity;
import com.j1j2.pifalao.feature.search.SearchViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-3-16.
 */
@Module
public class SearchModule {

    private SearchActivity searchActivity;

    public SearchModule(SearchActivity searchActivity) {
        this.searchActivity = searchActivity;
    }

    @Provides
    @ActivityScope
    SearchActivity searchActivity() {
        return searchActivity;
    }

    @Provides
    @ActivityScope
    ProductApi productApi(Retrofit retrofit) {
        return retrofit.create(ProductApi.class);
    }

    @Provides
    @ActivityScope
    SearchViewModel searchViewModel(SearchActivity searchActivity, ProductApi productApi) {
        return new SearchViewModel(searchActivity, productApi);
    }
}
