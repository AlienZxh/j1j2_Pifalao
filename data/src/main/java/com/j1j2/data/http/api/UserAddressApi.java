package com.j1j2.data.http.api;

import com.j1j2.data.model.requestbody.EditorUserRecivingAddressBody;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface UserAddressApi {

    @POST("UserAddress/QueryUserReceiveAddress")
    Observable<String> queryUserReceiveAddress();

    @POST("UserAddress/QueryUserDefaultAddress")
    Observable<String> queryUserDefaultAddress();

    @POST("UserAddress/InsertOrUpdateUserAddress")
    Observable<String> insertOrUpdateUserAddress(@Body EditorUserRecivingAddressBody editorUserRecivingAddressBody);

    @POST("UserAddress/SetUserDefaultAddress")
    Observable<String> setUserDefaultAddress(@Query("addressId") int addressId);

    @POST("UserAddress/DeleteUserAddress")
    Observable<String> deleteUserAddress(@Query("addressId") int addressId);
}
