package com.j1j2.pifalao.app.di;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j1j2.common.util.Toastor;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.Navigate;
import com.j1j2.pifalao.app.db.DBHelper;
import com.j1j2.pifalao.app.provider.GsonProvider;
import com.j1j2.pifalao.app.sharedpreferences.FreightTypePrefrence;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.orhanobut.logger.Logger;

import net.orange_box.storebox.StoreBox;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alienzxh on 16-3-5.
 */
@Module
public class AppModule {
    private final MainAplication application;

    public AppModule(MainAplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    MainAplication application() {
        return application;
    }

    @Provides
    @Singleton
    Navigate navigate() {
        return new Navigate();
    }

    @Provides
    @Singleton
    Toastor toastor(MainAplication application) {
        return new Toastor(application.getApplicationContext());
    }

    @Provides
    @Singleton
    Gson gson() {
        return GsonProvider.provideGson();
    }

    @Provides
    @Singleton
    UserLoginPreference userLoginPreference(MainAplication application) {
        return StoreBox.create(application.getApplicationContext(), UserLoginPreference.class);
    }

    @Provides
    @Singleton
    UserRelativePreference userRelativePreference(MainAplication application) {
        return StoreBox.create(application.getApplicationContext(), UserRelativePreference.class);
    }

    @Provides
    @Singleton
    FreightTypePrefrence freightTypePrefrence(MainAplication application) {
        return StoreBox.create(application.getApplicationContext(), FreightTypePrefrence.class);
    }


    @Provides
    @Singleton
    OkHttpClient okHttpClient(final UserLoginPreference userLoginPreference, final Gson gson) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.d(message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if (url.toString().equals(BuildConfig.API_URL + "UserLogin/Login")) {
                    userLoginPreference.setLoginCookie(gson.toJson(cookies));
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                if (url.toString().equals(BuildConfig.API_URL + "UserLogin/Login")) {
                    return new ArrayList<Cookie>();
                } else {
                    return userLoginPreference.getLoginCookie(null) == null ?
                            new ArrayList<Cookie>() :
                            gson.<List<Cookie>>fromJson(userLoginPreference
                                    .getLoginCookie(null), new TypeToken<List<Cookie>>() {
                            }.getType());
                }
            }
        });
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        if (BuildConfig.DEBUG)
            return builder.addInterceptor(logging).build();
        else
            return builder.build();
    }


    @Provides
    @Singleton
    Retrofit retrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    DBHelper dbHelper(MainAplication application, UserRelativePreference userRelativePreference) {
        return new DBHelper(application.getApplicationContext(), userRelativePreference);
    }


}
