package com.j1j2.pifalao.app.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.Navigate;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

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
    Gson gson() {
        return new GsonBuilder()
                .serializeNulls()
                .create();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.d(message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    @Provides
    @Singleton
    OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient
                .Builder()
                .addInterceptor(loggingInterceptor)
                .cookieJar(new CookieJar() {
                    private List<Cookie> cookies;

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        if (url.toString().equals(BuildConfig.API_URL + "UserLogin/Login")) {
                            this.cookies = cookies;
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        if (url.toString().equals(BuildConfig.API_URL + "UserLogin/Login")) {
                            return new ArrayList<Cookie>();
                        } else {
                            return this.cookies == null ? new ArrayList<Cookie>() : this.cookies;
                        }
                    }
                })
                .build();

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


}
