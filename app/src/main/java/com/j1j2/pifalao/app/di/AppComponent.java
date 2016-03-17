package com.j1j2.pifalao.app.di;

import android.app.Activity;
import android.app.Application;

import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.Navigate;
import com.j1j2.pifalao.feature.city.di.CityComponent;
import com.j1j2.pifalao.feature.city.di.CityModule;
import com.j1j2.pifalao.feature.home.storestylehome.di.StoreStyleHomeComponent;
import com.j1j2.pifalao.feature.home.storestylehome.di.StoreStyleHomeModule;
import com.j1j2.pifalao.feature.location.di.LocationComponent;
import com.j1j2.pifalao.feature.location.di.LocationModule;
import com.j1j2.pifalao.feature.main.di.MainComponent;
import com.j1j2.pifalao.feature.main.di.MainModule;
import com.j1j2.pifalao.feature.productdetail.di.ProductDetailComponent;
import com.j1j2.pifalao.feature.productdetail.di.ProductDetailModule;
import com.j1j2.pifalao.feature.products.di.ProductsComponent;
import com.j1j2.pifalao.feature.products.di.ProductsModule;
import com.j1j2.pifalao.feature.search.di.SearchComponent;
import com.j1j2.pifalao.feature.search.di.SearchModule;
import com.j1j2.pifalao.feature.servicepoint.di.ServicePointComponent;
import com.j1j2.pifalao.feature.servicepoint.di.ServicePointModule;
import com.j1j2.pifalao.feature.services.di.ServicesComponent;
import com.j1j2.pifalao.feature.services.di.ServicesModule;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by alienzxh on 16-3-5.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    UserComponent plus(UserModule userModule);

    ActivityComponent plus(ActivityModule activityModule);

    CityComponent plus(CityModule cityModule);

    LocationComponent plus(LocationModule locationModule);

    ServicePointComponent plus(ServicePointModule servicePointModule);

    ServicesComponent plus(ServicesModule servicesModule);

    StoreStyleHomeComponent plus(StoreStyleHomeModule storeStyleHomeModule);

    ProductsComponent plus(ProductsModule productsModule);

    ProductDetailComponent plus(ProductDetailModule productDetailModule);

    MainComponent plus(MainModule mainModule);

    SearchComponent plus(SearchModule searchModule);
}
