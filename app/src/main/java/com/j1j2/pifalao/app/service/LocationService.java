package com.j1j2.pifalao.app.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.j1j2.pifalao.app.base.RxBus;
import com.j1j2.pifalao.app.constants.RxBusTag;
import com.orhanobut.logger.Logger;

/**
 * Created by alienzxh on 16-3-12.
 */
public class LocationService extends Service {

    // 定位相关
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLocation();
        Logger.d("后台线程启动了");
    }

    @Override
    public void onDestroy() {
        mLocationClient.stop();
        mLocationClient = null;
        super.onDestroy();
        Logger.d("后台线程结束了");
    }

    private void initLocation() {
        mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
        mLocationClient.registerLocationListener(myListener); // 注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        int span = 5000;
        option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        // option.setLocationNotify(true);//
        // 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        //option.setIsNeedLocationPoiList(true);//
        // 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        // option.setIgnoreKillProcess(false);//
        // 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        // option.SetIgnoreCacheException(false);//
        // 可选，默认false，设置是否收集CRASH信息，默认收集
        //option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // Receive Location
            if (location == null) {
                return;
            }
            //        StringBuffer sb = new StringBuffer(256);
//        sb.append("time : ");
//        sb.append(location.getTime());
//        sb.append("\nerror code : ");
//        sb.append(location.getLocType());
//        sb.append("\nlatitude : ");
//        sb.append(location.getLatitude());
//        sb.append("\nlontitude : ");
//        sb.append(location.getLongitude());
//        sb.append("\nradius : ");
//        sb.append(location.getRadius());
//        sb.append("\naddr : ");
//        sb.append(location.getLocationDescribe());
//        sb.append("\nProvince : ");
//        sb.append(location.getProvince());
//        sb.append("\ncity : ");
//        sb.append(location.getCity());
//        sb.append("\nDistrict : ");
//        sb.append(location.getDistrict());
//        Logger.d(sb.toString());
            switch (location.getLocType()) {
                case BDLocation.TypeGpsLocation:
                case BDLocation.TypeNetWorkLocation:
                case BDLocation.TypeOffLineLocation:
                case BDLocation.TypeCacheLocation:
                    RxBus.get().post(RxBusTag.LOCATION_EVENT, location);
//                    Logger.d("成功发送位置信息");
                    break;
                case BDLocation.TypeServerError:
                    Logger.d("onReceiveLocation:服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                    break;
                case BDLocation.TypeNetWorkException:
                    Logger.d("onReceiveLocation:网络不通导致定位失败，请检查网络是否通畅");
                    break;
                case BDLocation.TypeCriteriaException:
                    Logger.d("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                    break;
            }
        }
    }


}
