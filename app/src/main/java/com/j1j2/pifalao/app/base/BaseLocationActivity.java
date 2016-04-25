package com.j1j2.pifalao.app.base;

import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.j1j2.pifalao.app.event.LocationEvent;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by alienzxh on 16-3-28.
 */
public abstract class BaseLocationActivity extends BaseActivity {
    // 定位相关
    protected LocationClient mLocationClient = null;
    protected BDLocationListener locationListener;


    protected void initLocation() {
        mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
        mLocationClient.registerLocationListener(locationListener); // 注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        int span = 45000;
        option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
//        option.setLocationNotify(true);//
        // 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//        option.setIsNeedLocationPoiList(true);//
        // 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        // option.setIgnoreKillProcess(false);//
        // 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        // option.SetIgnoreCacheException(false);//
        // 可选，默认false，设置是否收集CRASH信息，默认收集
        //option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationListener = new MyLocationListener();
        initLocation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mLocationClient != null) {
            mLocationClient.start();
            Logger.d("定位开始了");
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mLocationClient != null) {
            mLocationClient.stop();
            Logger.d("定位结束了");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            if (mLocationClient.isStarted())
                mLocationClient.stop();
            mLocationClient.unRegisterLocationListener(locationListener);
            mLocationClient = null;
            locationListener = null;
            Logger.d("定位释放了");
        }
    }

    protected boolean isLocationSuccess(BDLocation location) {
        if (null == location)
            return false;
        switch (location.getLocType()) {
            case BDLocation.TypeGpsLocation:
            case BDLocation.TypeNetWorkLocation:
            case BDLocation.TypeOffLineLocation:
            case BDLocation.TypeCacheLocation:
                return true;
            case BDLocation.TypeServerError:
                Logger.d("onReceiveLocation:服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                return false;
            case BDLocation.TypeNetWorkException:
                Logger.d("onReceiveLocation:网络不通导致定位失败，请检查网络是否通畅");
                return false;
            case BDLocation.TypeCriteriaException:
                Logger.d("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                return false;
        }
        return false;
    }


    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // Receive Location
            if (location == null) {
                return;
            }
            EventBus.getDefault().postSticky(new LocationEvent(location));
//            //        StringBuffer sb = new StringBuffer(256);
////        sb.append("time : ");
////        sb.append(location.getTime());
////        sb.append("\nerror code : ");
////        sb.append(location.getLocType());
////        sb.append("\nlatitude : ");
////        sb.append(location.getLatitude());
////        sb.append("\nlontitude : ");
////        sb.append(location.getLongitude());
////        sb.append("\nradius : ");
////        sb.append(location.getRadius());
////        sb.append("\naddr : ");
////        sb.append(location.getLocationDescribe());
////        sb.append("\nProvince : ");
////        sb.append(location.getProvince());
////        sb.append("\ncity : ");
////        sb.append(location.getCity());
////        sb.append("\nDistrict : ");
////        sb.append(location.getDistrict());
////        Logger.d(sb.toString());
//            switch (location.getLocType()) {
//                case BDLocation.TypeGpsLocation:
//                case BDLocation.TypeNetWorkLocation:
//                case BDLocation.TypeOffLineLocation:
//                case BDLocation.TypeCacheLocation:
//                    EventBus.getDefault().postSticky(new LocationEvent(location));
////                    Logger.d("成功发送位置信息");
//                    break;
//                case BDLocation.TypeServerError:
//                    Logger.d("onReceiveLocation:服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
//                    break;
//                case BDLocation.TypeNetWorkException:
//                    Logger.d("onReceiveLocation:网络不通导致定位失败，请检查网络是否通畅");
//                    break;
//                case BDLocation.TypeCriteriaException:
//                    Logger.d("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
//                    break;
//            }
        }
    }

}

