package com.j1j2.data.http.api;

import com.j1j2.data.model.Address;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.EditorUserRecivingAddressBody;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface UserAddressApi {

    @POST("UserAddress/QueryUserReceiveAddress")
    Observable<WebReturn<List<Address>>> queryUserReceiveAddress();

    @POST("UserAddress/QueryUserDefaultAddress")
    Observable<WebReturn<Address>> queryUserDefaultAddress();

    @POST("UserAddress/InsertOrUpdateUserAddress")
    Observable<WebReturn<String>> insertOrUpdateUserAddress(@Body EditorUserRecivingAddressBody editorUserRecivingAddressBody);

    @POST("UserAddress/SetUserDefaultAddress")
    Observable<WebReturn<String>> setUserDefaultAddress(@Query("addressId") int addressId);

    @POST("UserAddress/DeleteUserAddress")
    Observable<WebReturn<String>> deleteUserAddress(@Query("addressId") int addressId);
}
