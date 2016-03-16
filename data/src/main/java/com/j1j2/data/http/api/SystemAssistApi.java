package com.j1j2.data.http.api;

import com.j1j2.data.model.PromtionTime;
import com.j1j2.data.model.WebReturn;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface SystemAssistApi {
    @POST("SystemAssist/QueryPromtionTime")
    Observable<WebReturn<PromtionTime>> queryPromtionTimeAsync();

    @POST("SystemAssist/QueryPromtionTime")
    WebReturn<PromtionTime> queryPromtionTimeSync();

    //_______________________________________________________________________________________
    @POST("SystemAssist/QuerySystemCurrentTime")
    Observable<String> querySystemCurrentTime();

    @POST("SystemAssist/QuerySystemNotice")
    Observable<String> querySystemNotice();


    @POST("SystemAssist/QueryLotterRecorsScroll")
    Observable<String> queryLotterRecorsScroll(@Query("pageIndex") int pageIndex);
}
