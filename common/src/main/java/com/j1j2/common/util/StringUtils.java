package com.j1j2.common.util;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;

import com.j1j2.common.R;
import com.j1j2.data.model.Address;
import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.FreightType;
import com.j1j2.data.model.OrderProductDetail;
import com.j1j2.data.model.ProductUnit;
import com.j1j2.data.model.ShopCartItem;

import java.util.List;

/**
 * Created by alienzxh on 16-3-17.
 */
public class StringUtils {

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
        return ("距离：≈" + df.format(d)) + "米";
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

    public static CharSequence getOrdersQuantity(List<OrderProductDetail> orderProductDetails) {
        int quantity = 0;
        if (null == orderProductDetails || orderProductDetails.size() <= 0)
            return "共" + quantity + "件商品";

        for (OrderProductDetail orderProductDetail : orderProductDetails) {
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


    public static CharSequence getOrdersTimeLineContent(int orderType) {
        switch (orderType) {
            case 1:
                return "请耐心等待门店确认！";
            case 4:
                return "您的订单正在拣货中！";
            case 8:
                return "订单正在配送途中，请耐心等待！";
            case 16:
                return "待收货";
            case 32:
                return "待评价";
            case 64:
                return "您的订单已配送完成！";
            case 256:
                return "已退订";
            default:
                return "已退订";
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

}
