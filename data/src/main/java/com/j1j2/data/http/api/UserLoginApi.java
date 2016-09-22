package com.j1j2.data.http.api;

import com.j1j2.data.model.User;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.LoginBody;


import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface UserLoginApi {

    @POST("UserLogin/Login")
    Observable<WebReturn<User>> login(@Body LoginBody loginBody);

    @POST("UserLogin/QueryUserDetail")
    Observable<WebReturn<User>> queryUserDetail();

    @POST("UserLogin/UpdateUserTerminalDetail")
    Observable<WebReturn<String>> updateUserTerminalDetail(@Body LoginBody loginBody);
    //______________________________________________________________________________________

    @Multipart
    @POST("UserLogin/PostUserProtrait")
    Observable<WebReturn<String>> postUserProtrait(@Part("imgFile") MultipartBody.Part file);


}
