package com.j1j2.pifalao.app.base;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.Poi;
import com.j1j2.common.util.LocationUtils;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.app.event.LocationEvent;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

/**
 * @author baidu
 */
public class LocationService {
    private LocationClient client = null;
    private LocationClientOption mOption, DIYoption;
    private Object objLock = new Object();

    private BDLocation mLocation = null;
    private BDLocation successLocation = null;

    /***
     * @param locationContext
     */
    public LocationService(Context locationContext) {
        synchronized (objLock) {
            if (client == null) {
                client = new LocationClient(locationContext);
                client.setLocOption(getDefaultLocationClientOption());
            }
        }
    }


    public boolean registerListener() {
        boolean isSuccess = false;
        if (mListener != null) {
            client.registerLocationListener(mListener);
            isSuccess = true;
        }
        return isSuccess;
    }

    public void unregisterListener() {
        if (mListener != null) {
            client.unRegisterLocationListener(mListener);
        }
    }

    /***
     * @param option
     * @return isSuccessSetOption
     */
    public boolean setLocationOption(LocationClientOption option) {
        boolean isSuccess = false;
        if (option != null) {
            if (client.isStarted())
                client.stop();
            DIYoption = option;
            client.setLocOption(option);
            isSuccess = true;
        }
        return isSuccess;
    }

    public LocationClientOption getOption() {
        return DIYoption;
    }


    public BDLocation getSuccessLocation() {
        return successLocation;
    }

    public BDLocation getmLocation() {
        return mLocation;
    }

    /***
     * @return DefaultLocationClientOption
     */
    public LocationClientOption getDefaultLocationClientOption() {
        if (mOption == null) {
            mOption = new LocationClientOption();
            mOption.setLocationMode(LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
            //mOption.setScanSpan(3000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            mOption.setOpenAutoNotifyMode(60 * 1000, 300, LocationClientOption.LOC_SENSITIVITY_HIGHT);//分别为频率，距离，敏感程度设置后，SDK会按照“或”的关系，达到标准才会发起定位
            mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
            mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
            mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
            mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
//            mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
            mOption.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        }
        return mOption;
    }

    public void start() {
        synchronized (objLock) {
            if (client != null && !client.isStarted()) {
                client.start();
            }
        }
    }

    public void stop() {
        synchronized (objLock) {
            if (client != null && client.isStarted()) {
                client.stop();
            }
        }
    }

    public void requestLocation() {
        synchronized (objLock) {
            if (client != null) {
                client.requestLocation();
            }
        }
    }


    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                if (BuildConfig.DEBUG) {
                    StringBuffer sb = new StringBuffer(256);
                    sb.append("time : ");
                    /**
                     * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                     * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                     */
                    sb.append(location.getTime());
                    sb.append("  locType : ");// 定位类型
                    sb.append(location.getLocType());
                    sb.append("  locType description : ");// *****对应的定位类型说明*****
                    sb.append(location.getLocTypeDescription());
                    sb.append("  latitude : ");// 纬度
                    sb.append(location.getLatitude());
                    sb.append("  lontitude : ");// 经度
                    sb.append(location.getLongitude());
                    sb.append("  radius : ");// 半径
                    sb.append(location.getRadius());
                    sb.append("  CountryCode : ");// 国家码
                    sb.append(location.getCountryCode());
                    sb.append("  Country : ");// 国家名称
                    sb.append(location.getCountry());
                    sb.append("  citycode : ");// 城市编码
                    sb.append(location.getCityCode());
                    sb.append("  city : ");// 城市
                    sb.append(location.getCity());
                    sb.append("  District : ");// 区
                    sb.append(location.getDistrict());
                    sb.append("  Street : ");// 街道
                    sb.append(location.getStreet());
                    sb.append("  addr : ");// 地址信息
                    sb.append(location.getAddrStr());
                    sb.append("  UserIndoorState: ");// *****返回用户室内外判断结果*****
                    sb.append(location.getUserIndoorState());
                    sb.append("  Direction(not all devices have value): ");
                    sb.append(location.getDirection());// 方向
                    sb.append("  locationdescribe: ");
                    sb.append(location.getLocationDescribe());// 位置语义化信息
                    sb.append("  Poi: ");// POI信息
                    if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                        for (int i = 0; i < location.getPoiList().size(); i++) {
                            Poi poi = (Poi) location.getPoiList().get(i);
                            sb.append(poi.getName() + ";");
                        }
                    }
                    if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                        sb.append("  speed : ");
                        sb.append(location.getSpeed());// 速度 单位：km/h
                        sb.append("  satellite : ");
                        sb.append(location.getSatelliteNumber());// 卫星数目
                        sb.append("  height : ");
                        sb.append(location.getAltitude());// 海拔高度 单位：米
                        sb.append("  gps status : ");
                        sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                        sb.append("  describe : ");
                        sb.append("gps定位成功");
                    } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                        // 运营商信息
                        if (location.hasAltitude()) {// *****如果有海拔高度*****
                            sb.append("  height : ");
                            sb.append(location.getAltitude());// 单位：米
                        }
                        sb.append("  operationers : ");// 运营商信息
                        sb.append(location.getOperators());
                        sb.append("  describe : ");
                        sb.append("网络定位成功");
                    } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                        sb.append("  describe : ");
                        sb.append("离线定位成功，离线定位结果也是有效的");

                    } else if (location.getLocType() == BDLocation.TypeServerError) {
                        sb.append("  describe : ");
                        sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                    } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                        sb.append("  describe : ");
                        sb.append("网络不同导致定位失败，请检查网络是否通畅");
                    } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                        sb.append("  describe : ");
                        sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                    }
                    Logger.d("onReceiveLocation:" + sb.toString());
                }
                if (LocationUtils.isLocationSuccess(location))
                    successLocation = location;
                mLocation = location;
                EventBus.getDefault().postSticky(new LocationEvent(location));

            } else
                Logger.d("onReceiveLocation:定位失败");
        }

    };

}
