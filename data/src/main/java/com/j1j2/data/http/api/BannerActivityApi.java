package com.j1j2.data.http.api;

import com.j1j2.data.model.BannerActivity;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.LoginBody;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface BannerActivityApi {

    @POST("BannerActivity/QueryBannerActivities")
    Observable<WebReturn<List<BannerActivity>>> queryBannerActivities(@Query("moduleId") int moduleId);

}
