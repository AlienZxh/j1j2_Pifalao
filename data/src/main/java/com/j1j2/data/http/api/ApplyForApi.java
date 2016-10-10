package com.j1j2.data.http.api;

import com.j1j2.data.model.ApplyForServiceCount;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.ApplyForServiceBody;
import com.j1j2.data.model.requestbody.ApplyForServicePointBody;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-9-25.
 */

public interface ApplyForApi {

    /// 提交保存申请服务信息
    @POST("ApplyFor/ApplyForService")
    Observable<WebReturn<String>> applyForService(@Body ApplyForServiceBody body);

    @POST("ApplyFor/QueryApplyForServiceCount")
    Observable<WebReturn<ApplyForServiceCount>> queryApplyForServiceCount(@Query("serviceModuleId") int serviceModuleId, @Query("mobile") String mobile);

    @POST("ApplyFor/ApplyForServicePoint")
    Observable<WebReturn<String>> applyForServicePoint(@Body ApplyForServicePointBody applyForServicePointBody);

    /// 提交保存申请服务信息
    @POST("ApplyFor/ApplyForServicePointCount")
    Observable<WebReturn<ApplyForServiceCount>> applyForServicePointCount(@Query("mobile") String mobile);
}
