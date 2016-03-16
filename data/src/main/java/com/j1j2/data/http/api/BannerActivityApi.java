package com.j1j2.data.http.api;

import com.j1j2.data.model.requestbody.LoginBody;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface BannerActivityApi {

    @POST("BannerActivity/QueryBannerActivities")
    Observable<String> queryBannerActivities();

}
