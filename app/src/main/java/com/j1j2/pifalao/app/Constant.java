package com.j1j2.pifalao.app;

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
        public final static int DELIVERY = 1;
        public final static int SHOPSERVICE = 2;
        public final static int VEGETABLE = 3;
        public final static int HOUSEKEEPING = 4;
        public final static int VIP = 5;
        public final static int MORE = 6;
    }

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
        public static final int ORDERTYPE_EXECUTING = 4;//处理中
        public static final int ORDERTYPE_CLIENTWAITFORRECEVIE = 16;//待收货
        public static final int ORDERTYPE_WAITFORRATE = 32;//待评价
        public static final int ORDERTYPE_COMPLETE = 64;//已完成
        public static final int ORDERTYPE_INVALID = 256;//已退订
    }
}
