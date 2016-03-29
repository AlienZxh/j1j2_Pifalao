package com.j1j2.common.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;

import com.j1j2.data.model.OrderProductDetail;
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

    

    public static CharSequence getOrdersState(int orderType) {
        switch (orderType) {
            case 1:
                return "已下单";
            case 4:
                return "处理中";
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
}
