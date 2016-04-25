package com.j1j2.pifalao.app;

import android.util.SparseIntArray;

import com.j1j2.pifalao.R;

/**
 * Created by alienzxh on 16-4-6.
 */
public class Constant {

//    public interface ModuleId {
//        public final static int SHOPSERVICE_MODULEID = 23;
//        public final static int DELIVERY_MODULEID = 25;
//        public final static int VEGETABLE_MODULEID = 26;
//        public final static int HOUSEKEEPING_MODULEID = 27;
//        public final static int VIP_MODULEID = 28;
//        public final static int MORE_MODULEID = 36;
//    }

    public interface ModuleType {
        public final static int MORE = 0;
        public final static int DELIVERY = 1;
        public final static int SHOPSERVICE = 2;
        public final static int VEGETABLE = 3;
        public final static int HOUSEKEEPING = 4;
        public final static int VIP = 5;
        public final static int MOBILE = 6;
        public final static int HYDROPOWER = 7;
        public final static int OIL = 8;
        public final static int TRAFFICFINES = 9;
        public final static int GAME = 10;
        public final static int BUS = 11;
        public final static int NUMERAL = 12;
        public final static int EXPRESS = 13;
        public final static int TOOL = 14;
        public final static int FINANCE = 15;
        public final static int LOTTERY = 16;
        public final static int TICKET = 17;
    }

    public final static SparseIntArray moduleColors = new SparseIntArray() {{
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
    }};

    public final static SparseIntArray moduleIconId = new SparseIntArray() {{
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
    }};

    public interface ProductsOrderbyId {
        public final static int PRODUCTS_ORDERBY_DEFAULT = 0;
        public final static int PRODUCTS_ORDERBY_SELLS = 1;
        public final static int PRODUCTS_ORDERBY_PRICE = 2;
        public final static int PRODUCTS_ORDERBY_VIEWS = 3;
    }

    public interface CategoryType {
        public final static int FREE_FREIGHT = 1;
        public final static int FIXED_FREIGHT = 2;
        public final static int QVERLIMIT_FREIGHT = 3;
        public final static int FIXEDRATE_FREIGHT = 4;
        public final static int VIP_FREIGHT = 5;
    }

    public interface DeliveryType {
        public final static int PICKBYSELF = 1;
        public final static int HOMEDELIVERY = 2;
    }

    public interface CouponType {
        public static final int COUPON_ALL = 0;
        public static final int COUPON_DELIVERY = 1;
        public static final int COUPON_NORMAL = 2;
        public static final int COUPON_GOODS = 3;
    }

    public interface OrderType {
        public static final int ORDERTYPE_ALL = 0;//全部
        public static final int ORDERTYPE_SUBMIT = 1;//已下单
        public static final int ORDERTYPE_DELIVERY = 8;//配送中
        public static final int ORDERTYPE_EXECUTING = 4;//处理中
        public static final int ORDERTYPE_CLIENTWAITFORRECEVIE = 16;//待收货
        public static final int ORDERTYPE_WAITFORRATE = 32;//待评价
        public static final int ORDERTYPE_COMPLETE = 64;//已完成
        public static final int ORDERTYPE_INVALID = 256;//已退订
    }
}
