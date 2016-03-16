package com.j1j2.data.http.api;

import com.j1j2.data.model.requestbody.RemoveUserFavoritesBody;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface UserLotteryApi {
    @POST("UserLottery/QueryLotteryConfig")
    Observable<String> queryLotteryConfig();

    @POST("UserLottery/PickLottery")
    Observable<String> pickLottery( @Query("lotteryId") int lotteryId);

    @POST("UserLottery/QueryLotteryRecords")
    Observable<String> queryLotteryRecords(@Query("pageIndex") int pageIndex);

    @POST("UserLottery/QueryMaterialObjectPrize")
    Observable<String> queryMaterialObjectPrize();
}
