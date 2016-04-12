package com.j1j2.pifalao.app.di;

import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.feature.city.di.CityComponent;
import com.j1j2.pifalao.feature.city.di.CityModule;
import com.j1j2.pifalao.feature.guide.di.GuideComponent;
import com.j1j2.pifalao.feature.guide.di.GuideModule;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts.di.DeliveryProductsComponent;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeproducts.di.DeliveryProductsModule;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint.DeliveryHomeServicepointFragment;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint.di.DeliveryServicepointComponent;
import com.j1j2.pifalao.feature.home.deliveryhome.deliveryhomeservicepoint.di.DeliveryServicepointModule;
import com.j1j2.pifalao.feature.home.deliveryhome.di.DeliveryHomeComponent;
import com.j1j2.pifalao.feature.home.deliveryhome.di.DeliveryHomeModule;
import com.j1j2.pifalao.feature.home.morehome.di.MoreHomeComponent;
import com.j1j2.pifalao.feature.home.morehome.di.MoreHomeModule;
import com.j1j2.pifalao.feature.home.storestylehome.di.StoreStyleHomeComponent;
import com.j1j2.pifalao.feature.home.storestylehome.di.StoreStyleHomeModule;
import com.j1j2.pifalao.feature.home.vegetablehome.di.VegetableHomeComponent;
import com.j1j2.pifalao.feature.home.vegetablehome.di.VegetableHomeModule;
import com.j1j2.pifalao.feature.launch.di.LaunchComponent;
import com.j1j2.pifalao.feature.launch.di.LaunchModule;
import com.j1j2.pifalao.feature.location.di.LocationComponent;
import com.j1j2.pifalao.feature.location.di.LocationModule;
import com.j1j2.pifalao.feature.login.di.LoginComponent;
import com.j1j2.pifalao.feature.login.di.LoginModule;
import com.j1j2.pifalao.feature.main.di.MainComponent;
import com.j1j2.pifalao.feature.main.di.MainModule;
import com.j1j2.pifalao.feature.productdetail.di.ProductDetailComponent;
import com.j1j2.pifalao.feature.productdetail.di.ProductDetailModule;
import com.j1j2.pifalao.feature.products.di.ProductsComponent;
import com.j1j2.pifalao.feature.products.di.ProductsModule;
import com.j1j2.pifalao.feature.register.stepone.di.RegisterStepOneComponent;
import com.j1j2.pifalao.feature.register.stepone.di.RegisterStepOneModule;
import com.j1j2.pifalao.feature.register.steptwo.di.RegisterStepTwoComponent;
import com.j1j2.pifalao.feature.register.steptwo.di.RegisterStepTwoModule;
import com.j1j2.pifalao.feature.search.di.SearchComponent;
import com.j1j2.pifalao.feature.search.di.SearchModule;
import com.j1j2.pifalao.feature.servicepoint.di.ServicePointComponent;
import com.j1j2.pifalao.feature.servicepoint.di.ServicePointModule;
import com.j1j2.pifalao.feature.services.di.ServicesComponent;
import com.j1j2.pifalao.feature.services.di.ServicesModule;
import com.j1j2.pifalao.feature.vegetablesort.di.VegetableSortComponent;
import com.j1j2.pifalao.feature.vegetablesort.di.VegetableSortModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alienzxh on 16-3-5.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(MainAplication aplication);

    LaunchComponent plus(LaunchModule launchModule);

    GuideComponent plus(GuideModule guideModule);

    UserComponent plus(UserModule userModule);

    ActivityComponent plus(ActivityModule activityModule);

    CityComponent plus(CityModule cityModule);

    LocationComponent plus(LocationModule locationModule);

    ServicePointComponent plus(ServicePointModule servicePointModule);

    ServicesComponent plus(ServicesModule servicesModule);

    StoreStyleHomeComponent plus(StoreStyleHomeModule storeStyleHomeModule);

    VegetableHomeComponent plus(VegetableHomeModule vegetableHomeModule);

    ProductsComponent plus(ProductsModule productsModule);

    ProductDetailComponent plus(ProductDetailModule productDetailModule);

    MainComponent plus(MainModule mainModule);

    SearchComponent plus(SearchModule searchModule);

    LoginComponent plus(LoginModule loginModule);

    RegisterStepOneComponent plus(RegisterStepOneModule registerStepOneModule);

    RegisterStepTwoComponent plus(RegisterStepTwoModule registerStepTwoModule);

    DeliveryHomeComponent plus(DeliveryHomeModule deliveryHomeModule);

    VegetableSortComponent plus(VegetableSortModule vegetableSortModule);

    DeliveryServicepointComponent plus(DeliveryServicepointModule deliveryServicepointModule);

    DeliveryProductsComponent plus(DeliveryProductsModule deliveryProductsModule);

    MoreHomeComponent plus(MoreHomeModule moreHomeModule);
}
