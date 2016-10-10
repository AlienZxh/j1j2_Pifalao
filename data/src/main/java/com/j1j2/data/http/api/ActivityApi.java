package com.j1j2.data.http.api;

import com.j1j2.data.model.AcceptanceSpeech;
import com.j1j2.data.model.ActivityProcessState;
import com.j1j2.data.model.ActivityProduct;
import com.j1j2.data.model.ActivityWinPrize;
import com.j1j2.data.model.LotteryCacluteResult;
import com.j1j2.data.model.LotteryFillAwardReceiveAddress;
import com.j1j2.data.model.LotteryParticipationTimes;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.RedPacket;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.AcceptanceSpeechSubmit;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-9-7.
 */
public interface ActivityApi {
    int PRIZE_ONGOING = 1;
    int PRIZE_RAFFLED = 2;
    int PRIZE_AWARDED = 4;

    /// 查询活动的中奖纪录
    @POST("Activity/QueryActivityWinPrizeRecords")
    Observable<WebReturn<PagerManager<ActivityWinPrize>>> queryActivityWinPrizeRecords(@Query("pageIndex") int pageIndex);

    /// 查询某个用户参与的在特定状态下的抽奖记录  带分页
    /// 1：正在进行
    /// 2：已揭晓
    /// 4：已中奖
    @POST("Activity/QueryUserParticipateLotteryActivities")
    Observable<WebReturn<PagerManager<ActivityWinPrize>>> queryUserParticipateLotteryActivities(@Query("pageIndex") int pageIndex, @Query("state") int state);

    /// <param name="orderByProcess">是否按进度进行排序</param>
    /// <param name="type">
    /// 0:返回键值对类型
    /// 1:返回层级列表类型
    /// </param>
    @POST("Activity/QueryAvailableActivity")
    Observable<WebReturn<Map<String, Map<String, List<ActivityProduct>>>>> queryAvailableActivity(@Query("orderByProcess") boolean orderByProcess, @Query("type") int type);

    /// 查询所有晒单信息 带分页
    @POST("Activity/QueryAllAcceptanceSpeech")
    Observable<WebReturn<PagerManager<AcceptanceSpeech>>> queryAllAcceptanceSpeech(@Query("pageIndex") int pageIndex);

    /// 查询用户的晒单信息 带分页
    @POST("Activity/QueryUserAcceptanceSpeech")
    Observable<WebReturn<PagerManager<AcceptanceSpeech>>> queryUserAcceptanceSpeech(@Query("pageIndex") int pageIndex);

    /// 查询某个用户参与的兑换活动记录 带分页
    @POST("Activity/QueryUserParticipageExchangeActivities")
    Observable<WebReturn<PagerManager<ActivityProduct>>> queryUserParticipageExchangeActivities(@Query("pageIndex") int pageIndex);

    /// 查询当前用户 在某个中奖活动中参与的人次数
    @POST("Activity/QueryParticipationTimesDetails")
    Observable<WebReturn<List<String>>> queryParticipationTimesDetails(@Query("lotteryId") int lotteryId);

    /// 查询某个中奖活动中参与人的人次数记录
    @POST("Activity/QueryLotteryParticipationTimesDetails")
    Observable<WebReturn<PagerManager<LotteryParticipationTimes>>> queryParticipationTimesDetails(@Query("lotteryId") int lotteryId, @Query("pageIndex") int pageIndex);

    /// 查询当前正在进行的活动商品详情
    @POST("Activity/QueryActivityProductDetails")
    Observable<WebReturn<ActivityProduct>> queryActivityProductDetails(@Query("productId") int productId);

    /// 根据订单ID查询订单活动的处理状态
    @POST("Activity/QueryActivityOrderProcessState")
    Observable<WebReturn<ActivityProcessState>> queryActivityOrderProcessState(@Query("orderId") int orderId);

    /// 中奖后填写收货地址等信息
    @POST("Activity/FillAwardRecevieAddress")
    Observable<WebReturn<String>> fillAwardRecevieAddress(@Body LotteryFillAwardReceiveAddress lotteryFillAwardReceiveAddress);

    /// 填写获奖评论信息
    @POST("Activity/FillAcceptanceSpeech")
    Observable<WebReturn<String>> fillAcceptanceSpeech(@Body AcceptanceSpeechSubmit acceptanceSpeechSubmit);

    /// 确认领奖收货
    @POST("Activity/ConfirmReceivedPrize")
    Observable<WebReturn<String>> confirmReceivedPrize(@Query("orderId") int orderId);

    /// 查询某个抽奖活动 的计算结果 的时间记录
    @POST("Activity/QueryLotteryCacluateResult")
    Observable<WebReturn<LotteryCacluteResult>> queryLotteryCacluateResult(@Query("lotteryId") int lotteryId);

    /// 查询用户未领取的有效红包总数
    @POST("Activity/QueryFoldRedPacketCount")
    Observable<WebReturn<Integer>> queryFoldRedPacketCount();

    /// 查询用户 红包记录 带分页
    /// queryState值
    /// 1：有效未领取红包
    /// 2：已领取红包
    /// 3：已失效红包
    @POST("Activity/QueryUserRedPackets")
    Observable<WebReturn<PagerManager<RedPacket>>> queryUserRedPackets(@Query("pageIndex") int pageIndex, @Query("queryState") int queryState);


    @POST("Activity/UnfoldRedPacket")
    Observable<WebReturn<String>> unfoldRedPacket(@Query("recordId") int recordId, @Query("orderNO") String orderNO);

}
