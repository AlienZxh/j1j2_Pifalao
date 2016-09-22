package com.j1j2.common.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alienzxh on 16-9-9.
 */
public class ValidateUtils {


    public static boolean isMobileNO(String mobiles) {
        if (TextUtils.isEmpty(mobiles))
            return false;
        Pattern p = Pattern.compile("[1][3578]\\d{9}");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
