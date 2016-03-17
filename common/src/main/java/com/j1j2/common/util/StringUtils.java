package com.j1j2.common.util;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.j1j2.common.R;

/**
 * Created by alienzxh on 16-3-17.
 */
public class StringUtils {

    public static String getStringWithoutBlank(String str) {
        return str.replaceAll(" ", "");
    }

    public static String getLocation(String str) {
        return (str.replaceAll(" ", "") + "市");
    }

    public static String getServicePointNameWithPosition(int position, String str) {
        return ("" + position + "、" + str);
    }

    public static String getServicePointAddress(String str) {
        return ("地址：" + str);
    }

    public static String getServicePointDistance(double d) {
        return ("距离：≈" + d);
    }

    public static String getStrWithBracket(String str) {
        return ("(" + str + ")");
    }


}
