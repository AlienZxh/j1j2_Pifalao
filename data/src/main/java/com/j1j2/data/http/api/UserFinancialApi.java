package com.j1j2.data.http.api;

import com.j1j2.data.model.BalanceRecord;
import com.j1j2.data.model.BalanceRecordPage;
import com.j1j2.data.model.WebReturn;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-5-12.
 */
public interface UserFinancialApi {

    @POST("UserFinancial/QueryUserCurrentMonthFinancialRecords")
    Observable<WebReturn<List<BalanceRecord>>> queryUserCurrentMonthFinancialRecords();


    @POST("UserFinancial/QueryUserFinancalRecords")
    Observable<WebReturn<BalanceRecordPage>> queryUserFinancalRecords(@Query("minTime") String minTime);
}
