package com.j1j2.pifalao.app;

import android.os.Environment;
import android.util.SparseIntArray;

import com.j1j2.pifalao.R;

/**
 * Created by alienzxh on 16-4-6.
 */
public class Constant {
    public final static String WEIXIN_APP_ID = "wxaaf65494c086b0d3";
    public final static String BUGLY_ID = "1103829290";

    public interface FilePath {
        String saveFolder = Environment
                .getExternalStorageDirectory() + "/pifalao/";

        String editPhotoCacheFolder = saveFolder + "/EditPhotoCacheFolder/";
        String takePhotoFolder = saveFolder + "/TakePhotoFolder/";
        String compressPhotoFolder = saveFolder + "/CompressPhotoFolder/";

        String cacheImgFolder = saveFolder + "/CacheImgFolder/";

        String upgradeFolder = saveFolder + "/UpgradeFolder/";

    }

    public interface ModuleType {
        int MORE = 0;
        int DELIVERY = 1;
        int SHOPSERVICE = 2;
        int VEGETABLE = 3;
        int HOUSEKEEPING = 4;
        int VIP = 5;
        int MOBILE = 6;
        int HYDROPOWER = 7;
        int OIL = 8;
        int TRAFFICFINES = 9;
        int GAME = 10;
        int BUS = 11;
        int NUMERAL = 12;
        int EXPRESS = 13;
        int TOOL = 14;
        int FINANCE = 15;
        int LOTTERY = 16;
        int TICKET = 17;
        int BANQUET = 18;
        int FRUIT = 19;
        int MEMBER = 20;
        int SPECIALOFFER = 21;
    }

    public static SparseIntArray moduleColors = new SparseIntArray() {{
        put(ModuleType.MORE, 0xffaaaaaa);
        put(ModuleType.DELIVERY, 0xfff09609);
        put(ModuleType.SHOPSERVICE, 0xff1ba1e2);
        put(ModuleType.VEGETABLE, 0xff22ac38);
        put(ModuleType.HOUSEKEEPING, 0xff01c8c6);
        put(ModuleType.VIP, 0xffa3ca19);
        put(ModuleType.MOBILE, 0xffec6941);
        put(ModuleType.HYDROPOWER, 0xff297acc);
        put(ModuleType.OIL, 0xff22ac38);
        put(ModuleType.TRAFFICFINES, 0xffff9900);
        put(ModuleType.GAME, 0xffa3ca19);
        put(ModuleType.BUS, 0xff297acc);
        put(ModuleType.NUMERAL, 0xffec6941);
        put(ModuleType.EXPRESS, 0xff1ba1e2);
        put(ModuleType.TOOL, 0xffec6941);
        put(ModuleType.FINANCE, 0xff01c8c6);
        put(ModuleType.LOTTERY, 0xff1ba1e2);
        put(ModuleType.TICKET, 0xffff9900);
        put(ModuleType.BANQUET, 0xff1ba1e2);
        put(ModuleType.FRUIT, 0xff22ac38);
        put(ModuleType.MEMBER, 0xff01c8c6);
        put(ModuleType.SPECIALOFFER, 0xffff5d24);
    }};

    public static SparseIntArray moduleIconId = new SparseIntArray() {{
        put(ModuleType.MORE, R.string.icon_service_more);
        put(ModuleType.DELIVERY, R.string.icon_service_delivery);
        put(ModuleType.SHOPSERVICE, R.string.icon_service_shopservice);
        put(ModuleType.VEGETABLE, R.string.icon_service_vegetable);
        put(ModuleType.HOUSEKEEPING, R.string.icon_service_housekeeping);
        put(ModuleType.VIP, R.string.icon_service_vip);
        put(ModuleType.MOBILE, R.string.icon_service_mobile);
        put(ModuleType.HYDROPOWER, R.string.icon_service_hydropower);
        put(ModuleType.OIL, R.string.icon_service_oil);
        put(ModuleType.TRAFFICFINES, R.string.icon_service_trafficfines);
        put(ModuleType.GAME, R.string.icon_service_game);
        put(ModuleType.BUS, R.string.icon_service_bus);
        put(ModuleType.NUMERAL, R.string.icon_service_numeral);
        put(ModuleType.EXPRESS, R.string.icon_service_express);
        put(ModuleType.TOOL, R.string.icon_service_tool);
        put(ModuleType.FINANCE, R.string.icon_service_finance);
        put(ModuleType.LOTTERY, R.string.icon_service_lottery);
        put(ModuleType.TICKET, R.string.icon_service_ticket);
        put(ModuleType.BANQUET, R.string.icon_service_banquet);
        put(ModuleType.FRUIT, R.string.icon_service_fruit);
        put(ModuleType.MEMBER, R.string.icon_service_member);
        put(ModuleType.SPECIALOFFER, R.string.icon_service_specialoffer);
    }};

    public interface ProductsOrderbyId {
        int PRODUCTS_ORDERBY_DEFAULT = 0;
        int PRODUCTS_ORDERBY_SELLS = 1;
        int PRODUCTS_ORDERBY_PRICE = 2;
        int PRODUCTS_ORDERBY_VIEWS = 3;
    }

    public interface CategoryType {
        int FREE_FREIGHT = 1;
        int FIXED_FREIGHT = 2;
        int QVERLIMIT_FREIGHT = 3;
        int FIXEDRATE_FREIGHT = 4;
        int VIP_FREIGHT = 5;
    }

    public interface DeliveryType {
        int PICKBYSELF = 1;
        int HOMEDELIVERY = 2;
    }

    public interface CouponType {
        int COUPON_ALL = 0;
        int COUPON_DELIVERY = 1;
        int COUPON_NORMAL = 2;
        int COUPON_GOODS = 3;
    }

    public interface OrderType {
        int ORDERTYPE_OFFLINE = -1;//已退订
        int ORDERTYPE_ALL = 0;//全部
        int ORDERTYPE_SUBMIT = 1;//已下单
        int ORDERTYPE_UNPAY = 2;//待支付
        int ORDERTYPE_DELIVERY = 8;//配送中
        int ORDERTYPE_EXECUTING = 4;//处理中
        int ORDERTYPE_CLIENTWAITFORRECEVIE = 16;//待收货
        int ORDERTYPE_WAITFORRATE = 32;//待评价
        int ORDERTYPE_COMPLETE = 64;//已完成
        int ORDERTYPE_INVALID = 256;//已退订
    }

    public interface OrderPayType {
        int CASHONDELIVERY = 1;//全部
        int ONLINEPAYMENT = 2;//已下单
    }

    public interface OnlinePayType {
        int BANLANCEPAY = 1;//余额
        int ALIPAY = 2;//支付宝
        int WEIXINPAY = 3;//微信
    }

    public interface ActivityProductType {
        int Material = 1;/// 实物 Material=1,
        int VirtualPhoneCharge = 2;  /// 话费充值虚拟商品 VirtualPhoneCharge=2
    }

    public interface ActivityOrderState {
        int UNPAYED = 1;/// 待付款
        int SUBMIT = 2;/// 已提交
        int UNRAFFLED = 4;/// 待抽奖
        int RAFFLED = 8;/// 已揭晓
        int AWARDED = 16;/// 已领奖
        int DLIVERYED = 22;///已发货
        int USERRECEIVED = 32;// 已收货
        int SHARED = 64;/// 已评论
        int COMPLETED = 128;/// 已完成
        int INVALID = -1;/// 作废、冻结
    }

    public interface ActivityOrderType {
        int EXCHANGEORDER = 1;///兑换活动订单
        int LOTTERYORDER = 2;/// 抽奖活动订单
    }

    public interface ActivitySortType {
        int EXCHANGE = 1;///兑换活动
        int LOTTERY = 2;/// 抽奖活动
    }

    public interface ShareType {
        String PRODUCT = "product";//商品分享
        String ACTIVITY = "activity";//活动分享
    }

    public interface RedPacketState {
        int AVAILABILITY = 1;
        int USED = 2;
        int UNAVAILABILITY = 3;
    }
}
