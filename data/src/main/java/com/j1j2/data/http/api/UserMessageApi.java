package com.j1j2.data.http.api;

import com.j1j2.data.model.Message;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.SetSystemNoteiceReadBody;
import com.j1j2.data.model.requestbody.UserPushSearcherBody;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-3-10.
 */
public interface UserMessageApi {

    @POST("UserMessage/QueryPushMessageByUserId")
    Observable<WebReturn<PagerManager<Message>>> queryPushMessageByUserId(@Body UserPushSearcherBody userPushSearcherBody);

    //______________________________________________________________________________________________

    @POST("UserMessage/QueryUserUnReadInfo")
    Observable<String> queryUserUnReadInfo();

    @POST("UserMessage/QueryUnreadPushMessageCount")
    Observable<String> queryUnreadPushMessageCount();


    @POST("UserMessage/MarkPushMessage")
    Observable<String> markPushMessage(@Query("recordId") int recordId);

    @POST("UserMessage/MarkPushMessageList")
    Observable<String> markPushMessageList(@Body SetSystemNoteiceReadBody setSystemNoteiceReadBody);
}
