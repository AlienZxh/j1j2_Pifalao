package com.j1j2.common.util;

import android.support.v4.util.ArrayMap;

/**
 * Created by alienzxh on 16-7-19.
 */
public class UrlUtils {
    public static ArrayMap<String, String> toMap(String url) {
        ArrayMap<String, String> map = null;
        if (url != null && url.indexOf("&") > -1 && url.indexOf("=") > -1) {
            map = new ArrayMap<String, String>();
            String[] arrTemp = url.split("&");
            for (String str : arrTemp) {
                String[] qs = str.split("=");
                map.put(qs[0], qs[1]);
            }
        }
        return map;
    }
}
