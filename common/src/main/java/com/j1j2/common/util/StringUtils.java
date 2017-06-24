package com.j1j2.common.util;

import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;

import com.j1j2.data.model.Address;
import com.j1j2.data.model.BalanceRecord;
import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.FreightType;
import com.j1j2.data.model.OrderDetail;
import com.j1j2.data.model.OrderSimple;
import com.j1j2.data.model.Product;
import com.j1j2.data.model.ProductUnit;

import java.util.List;

/**
 * Created by alienzxh on 16-3-17.
 */
public class StringUtils {

    public static Uri getMainThumImgUri(Product product) {
        String url = "";
        if (product != null){
            if (!EmptyUtils.isEmpty(product.getMainThumImg()))
                url = product.getMainThumImg();
            else if (!EmptyUtils.isEmpty(product.getImgs()))
                url = product.getImgs().get(0).getImgUrl() == null ? "" : product.getImgs().get(0).getImgUrl();
        }
        return Uri.parse(url);
    }

    public static CharSequence getStringWithoutBlank(String str) {
        return str.replaceAll(" ", "");
    }

    public static CharSequence getLocation(String str) {
        return (str.replaceAll(" ", "") + "市");
    }

    public static CharSequence getServicePointNameWithPosition(int position, String str) {
        return ("" + position + "、" + str);
    }

    public static CharSequence getServicePointAddress(String str) {
        return ("地址：" + str);
    }

    public static CharSequence getServicePointDistance(double d) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
        if (d <= 0) {
            return "距离：定位失败";
        } else if (d > 30000) {
            return "距离：距离过远";
        } else if (d <= 30000 && d > 0) {
            return ("距离：≈" + df.format(d)) + "米";
        }
        return "距离：--";
    }

    public static CharSequence getStrWithBracket(String str) {
        return ("(" + str + ")");
    }

    public static CharSequence getPriceWiteSymbol(double price) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
        return "￥" + df.format(price);
    }

    public static CharSequence getPriceStr(double price) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
        return "" + df.format(price);
    }

    public static CharSequence getStrWithStrikethrough(String str) {
        SpannableString msp = new SpannableString(str);
        msp.setSpan(new StrikethroughSpan(), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return msp;
    }

    public static CharSequence getPriceWithStrikethrough(double price) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
        SpannableString msp = new SpannableString("￥" + df.format(price));
        msp.setSpan(new StrikethroughSpan(), 0, msp.toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return msp;
    }

    public static CharSequence getRetialPrice(double price) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
        SpannableString msp = new SpannableString("市场价：￥" + df.format(price));
        msp.setSpan(new StrikethroughSpan(), 0, msp.toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return msp;
    }

    public static CharSequence getRetialPrice(double price, String unit) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
        SpannableString msp = new SpannableString("市场价：" + df.format(price) + "元/" + unit);
        msp.setSpan(new StrikethroughSpan(), 0, msp.toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return msp;
    }

    public static CharSequence getPriceSave(double retialPrice, double memberPrice) {
        double save = retialPrice - memberPrice;
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
        return "省：￥" + df.format(save);
    }

    public static CharSequence getOrdersQuantity(int quantity) {

        return "共" + quantity + "件商品";
    }

    public static CharSequence getOrdersQuantity(List<OrderDetail.OrderProductDetail> orderProductDetails) {
        int quantity = 0;
        if (null == orderProductDetails || orderProductDetails.size() <= 0)
            return "共" + quantity + "件商品";

        for (OrderDetail.OrderProductDetail orderProductDetail : orderProductDetails) {
            quantity += orderProductDetail.getQuantity();
        }
        return "共" + quantity + "件商品";
    }


    public static CharSequence getCouponLimit(Coupon coupon) {
        String limit = "";
        switch (coupon.getType()) {
            case 1:
//                limit = "配送费满" + coupon.getCouponValue() + "元可用";
                return limit;
            case 2:
                limit = "(订单满" + coupon.getConstraints() + "元可用)";
                return limit;
        }
        return limit;
    }

    public static CharSequence getOrdersState(int orderType) {
        switch (orderType) {
            case 1:
                return "已下单";
            case 2:
                return "待支付";
            case 4:
                return "处理中";
            case 8:
                return "配送中";
            case 16:
                return "待收货";
            case 32:
                return "待评价";
            case 64:
                return "已完成";
            case 256:
                return "已退订";
            default:
                return "已退订";
        }
    }


    public static CharSequence getOrdersTimeLineContent(int orderType, int orderPayType) {
        switch (orderType) {
            case 1:
                if (orderPayType == 1)
                    return "请耐心等待门店确认！";
                else
                    return "订单支付成功，请耐心等待门店确认！";
            case 2:
                return "请及时支付订单，过期订单将取消！";
            case 4:
                return "您的订单正在拣货中！";
            case 8:
                return "订单正在配送途中，请耐心等待！";
            case 16:
                return "您的订单已配送到服务点，请及时收取！";
            case 32:
                return "订单配送完成，请您对此次服务做出评价！";
            case 64:
                return "您的订单已配送完成！祝您购物愉快！";
            case 256:
                if (orderPayType == 1)
                    return "订单取消成功！交易已关闭。";
                else
                    return "订单取消成功！交易已关闭，订单取消所产生的退款将直接退回到您的账户余额，请注意查收！";
            default:
                return "";
        }
    }

    public static CharSequence getPrizeOrdersState(int orderType) {
        switch (orderType) {
            case 1:
                return "待付款";
            case 2:
                return "已提交";
            case 4:
                return "待抽奖";
            case 8:
                return "待领奖";
            case 16:
                return "待发货";
            case 22:
                return "待收货";
            case 32:
                return "待晒单";
            case 64:
                return "已晒单";
            case 128:
                return "已完成";
            case -1:
                return "已作废";
            default:
                return "已作废";
        }

    }


    public static CharSequence getPrizeOrdersTimeLineContent(int orderType) {
        switch (orderType) {
            case 1:
                return "待付款";
            case 2:
                return "已提交";
            case 4:
                return "待抽奖";
            case 8:
                return "请尽快领取您的奖品～";
            case 16:
                return "据说好东西都需要稍微等一下～";
            case 22:
                return "据说好东西都需要稍微等一下～";
            case 32:
                return "听说晒单会带来好运呢，写下您的中奖感言吧~";
            case 64:
                return "已评论";
            case 128:
                return "已完成";
            case -1:
                return "已作废";
            default:
                return "已作废";
        }

    }


    public static double getCouponValue(Coupon coupon, double freight) {
        double value = 0.0;
        if (coupon != null)
            switch (coupon.getType()) {
                case 1:
                    value = (coupon.getCouponValue() <= freight ? coupon.getCouponValue() : freight);
                    return value;
                case 2:
                    value = coupon.getCouponValue();
                    return value;
            }
        return value;
    }

    public static CharSequence getAddressStr(Address address) {
        if (address == null)
            return "";
        StringBuilder str = new StringBuilder();
        str.append(address.getAddressSegementF() == null ? "" : address.getAddressSegementF());
        str.append(address.getAddressSegementS() == null ? "" : address.getAddressSegementS());
        str.append(address.getAddressSegementT() == null ? "" : address.getAddressSegementT());
        str.append(address.getAddress() == null ? "" : address.getAddress());
        return str.toString();
    }

    public static CharSequence getProductDetailUnit(List<ProductUnit> productUnits) {
        String result = "";
        if (null == productUnits || productUnits.size() <= 0)
            return result;

        for (ProductUnit productUnit : productUnits) {
            result += (productUnit.getUnit() == null ? "" : productUnit.getUnit()) + "、";
        }
        return result.substring(0, result.length() - 1);
    }

    public static CharSequence getProductDetailBarCode(List<ProductUnit> productUnits) {
        String result = "";
        if (null == productUnits || productUnits.size() <= 0)
            return result;

        for (ProductUnit productUnit : productUnits) {
            result += (productUnit.getBarCode() == null ? "" : productUnit.getBarCode()) + "、";
        }
        return result.substring(0, result.length() - 1);
    }

    public static CharSequence getServiceTimeWithFreightType(FreightType freightType) {
        String result = "";
        if (null == freightType)
            return result;
        result = freightType.getDliveryTimeSegement().substring(0, 5) + "-" + freightType.getDliveryTimeSegement().substring(freightType.getDliveryTimeSegement().length() - 5, freightType.getDliveryTimeSegement().length());
        return result;
    }

    public static CharSequence getUserBalanceStr(double price) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
        return "" + df.format(Math.abs(price));
    }

    public static CharSequence getPointStr(int point) {
        SpannableString msp = new SpannableString("积分：" + point);
//        msp.setSpan(new ForegroundColorSpan(0xffff9900), 3, msp.toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        msp.setSpan(new StyleSpan(Typeface.BOLD), 3, msp.toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return msp;
    }

    public static CharSequence getShowStr(int showType, int position) {
        String str = "";
        if (showType == 0) {
            str = "批发佬服务点实拍照片展示";
        } else {
            str = "批发佬仓库实拍照片展示";
        }
        switch (position) {
            case 0:
                return str + "（一）";
            case 1:
                return str + "（二）";
            case 2:
                return str + "（三）";
            case 3:
                return str + "（四）";
            case 4:
                return str + "（五）";
            case 5:
                return str + "（六）";
            case 6:
                return str + "（七）";
            case 7:
                return str + "（八）";
            case 8:
                return str + "（九）";
            case 9:
                return str + "（十）";
        }
        return str;
    }

    public static CharSequence getOnlinePayType(int onlinePayType) {
        switch (onlinePayType) {
            case 1:
                return "在线支付（余额支付）";
            case 2:
                return "在线支付（支付宝支付）";
            case 3:
                return "在线支付（微信支付）";
            default:
                return "在线支付";
        }
    }


    public static CharSequence getBalanceRecordStr(BalanceRecord balanceRecord) {
        String str = balanceRecord.getRecordTypeStr() + "（" + balanceRecord.getCreateTimeStr() + "）";
        SpannableString msp = new SpannableString(str);
        msp.setSpan(new ForegroundColorSpan(0xff999999), str.indexOf("（"), str.indexOf("）") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new RelativeSizeSpan(0.75f), str.indexOf("（"), str.indexOf("）") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return msp;
    }

    public static CharSequence getOfflineOrderPayType(int payType) {
        switch (payType) {
            case 1:
                return "现金支付";
            case 2:
                return "支付宝支付";
            case 3:
                return "微信支付";
            default:
                return "现金支付";
        }
    }

    public static CharSequence getOrdersActionBarTitle(int orderType) {
        switch (orderType) {
            case -1:
                return "批发佬线下订单";
            case 1:
                return "已下单订单";
            case 2:
                return "待支付订单";
            case 4:
                return "处理中订单";
            case 8:
                return "配送中订单";
            case 16:
                return "待收货订单";
            case 32:
                return "待评价订单";
            case 64:
                return "已完成订单";
            case 256:
                return "已退订订单";
            case 0:
            default:
                return "全部订单";

        }
    }
}
