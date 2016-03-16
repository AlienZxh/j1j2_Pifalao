package com.j1j2.data.http.api;

import com.j1j2.data.model.User;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.LoginBody;


import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface UserLoginApi {

    @POST("UserLogin/Login")
    Observable<WebReturn<User>> login(@Body LoginBody loginBody);

    //______________________________________________________________________________________

    @POST("UserLogin/UpdateUserTerminalDetail")
    Observable<String> updateUserTerminalDetail(@Body LoginBody loginBody);

    @POST("UserLogin/QueryUserDetail")
    Observable<String> queryUserDetail();
}
