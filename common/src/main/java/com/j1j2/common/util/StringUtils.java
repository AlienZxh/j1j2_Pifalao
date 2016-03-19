package com.j1j2.common.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;

import com.j1j2.common.R;

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
        return ("距离：≈" + d);
    }

    public static CharSequence getStrWithBracket(String str) {
        return ("(" + str + ")");
    }

    public static CharSequence getPriceWiteSymbol(double price) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
        return "￥" + df.format(price);
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
}
