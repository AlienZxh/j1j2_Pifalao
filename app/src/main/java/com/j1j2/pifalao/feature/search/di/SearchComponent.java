package com.j1j2.pifalao.feature.search.di;

import com.j1j2.pifalao.app.ActivityScope;
import com.j1j2.pifalao.feature.products.di.ProductsModule;
import com.j1j2.pifalao.feature.search.SearchActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-16.
 */
@ActivityScope
@Subcomponent(modules = {SearchModule.class})
public interface SearchComponent {

    void inject(SearchActivity searchActivity);

}
