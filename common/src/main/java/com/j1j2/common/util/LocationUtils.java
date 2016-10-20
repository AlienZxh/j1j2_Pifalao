package com.j1j2.common.util;

import com.baidu.location.BDLocation;

/**
 * Created by alienzxh on 16-10-15.
 */

public class LocationUtils {
    public static boolean isLocationSuccess(BDLocation location) {
        if (null == location)
            return false;
        switch (location.getLocType()) {
            case BDLocation.TypeGpsLocation:
            case BDLocation.TypeNetWorkLocation:
            case BDLocation.TypeOffLineLocation:
                return true;
            case BDLocation.TypeServerError:
                return false;
            case BDLocation.TypeNetWorkException:
                return false;
            case BDLocation.TypeCriteriaException:
                return false;
        }
        return false;
    }
}
