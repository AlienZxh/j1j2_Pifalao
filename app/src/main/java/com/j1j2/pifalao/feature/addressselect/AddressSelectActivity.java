package com.j1j2.pifalao.feature.addressselect;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.j1j2.common.util.LocationUtils;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseMapActivity;
import com.j1j2.pifalao.app.event.AddressSelectEvent;
import com.j1j2.pifalao.app.event.LocationEvent;
import com.j1j2.pifalao.databinding.ActivityAddressselectBinding;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-31.
 */
@RequireBundler
public class AddressSelectActivity extends BaseMapActivity implements OnGetGeoCoderResultListener, OnGetSuggestionResultListener, OnGetPoiSearchResultListener, AddressSelectAdapter.OnAddressSelectListener, View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener, BaiduMap.OnMapStatusChangeListener {
    ActivityAddressselectBinding binding;

    @Arg
    String city;

    @Arg
    String district;

    PoiSearch poiSearch;
    PoiNearbySearchOption poiNearbySearchOption;
    PoiBoundSearchOption poiBoundSearchOption;

    SuggestionSearch suggestionSearch;
    SuggestionSearchOption suggestionSearchOption;

    GeoCoder geoCoderSearch;
    ReverseGeoCodeOption reverseGeoCodeOption;

    boolean isFirst = true;

    @Override
    protected void initMap() {
        getMapView().showZoomControls(false);
        getMapView().showScaleControl(true);


        LatLng point = new LatLng(28.175983, 113.023015);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(point, 20.0f);
        mBaiduMap.setMapStatus(u);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);// 开启定位图层
        mBaiduMap.setOnMapStatusChangeListener(this);

        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(this);
        suggestionSearch = SuggestionSearch.newInstance();
        suggestionSearch.setOnGetSuggestionResultListener(this);
        geoCoderSearch = GeoCoder.newInstance();
        geoCoderSearch.setOnGetGeoCodeResultListener(this);
    }

    @Override
    protected MapView getMapView() {
        return binding.mapView;
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addressselect);
    }

    @Override
    protected void initViews() {
        binding.backBtn.setOnClickListener(this);
        binding.searchBtn.setOnClickListener(this);
        binding.searchview.addTextChangedListener(this);
        binding.searchview.clearFocus();
        binding.addressList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.addressList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .margin(AutoUtils.getPercentWidthSize(20), 0)
                .colorResId(R.color.colorGrayDEDEDE)
                .sizeResId(R.dimen.height_1px)
                .build());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        suggestionSearch.destroy();
        poiSearch.destroy();
        geoCoderSearch.destroy();
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLocationEvent(LocationEvent event) {
        BDLocation location = event.getLocation();
        if (LocationUtils.isLocationSuccess(location)) {
            refreshMyLocation(location);
            if (isFirst) {
                toMayLocation(location);
                isFirst = false;
            }
            if (poiSearch == null)
                return;
            searchNearby(location);
        }
    }

    public void searchNearby(BDLocation location) {
        poiNearbySearchOption = new PoiNearbySearchOption()
                .sortType(PoiSortType.distance_from_near_to_far)
                .location(new LatLng(location.getLatitude(), location.getLongitude()))
                .radius(3000)
                .keyword(location.getStreet())
                .pageNum(1).pageCapacity(50);
        poiSearch.searchNearby(poiNearbySearchOption);
    }

    public void searchPoint(LatLng point) {
        reverseGeoCodeOption = new ReverseGeoCodeOption().location(point);
        geoCoderSearch.reverseGeoCode(reverseGeoCodeOption);
    }

    public void refreshMyLocation(BDLocation location) {
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())// 定位精度
                .direction(100)// GPS定位时方向角度,顺时针0-360
                .latitude(location.getLatitude())// 百度纬度坐标
                .longitude(location.getLongitude())// 百度经度坐标
                .speed(location.getSpeed())// GPS定位时速度
                .satellitesNum(location.getSatelliteNumber())// GPS定位时卫星数目
                .build();
        if (mBaiduMap == null)
            return;
        mBaiduMap.setMyLocationData(locData);

    }

    public void toMayLocation(BDLocation location) {
        LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(point, 20.0f);
        mBaiduMap.setMapStatus(u);
    }


    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.searchBtn) {

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        poiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(((AddressSearchAdapter) parent.getAdapter()).getItemSuggestionInfo(position).uid));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence cs, int start, int before, int count) {
        if (cs.length() <= 0) {
            return;
        }
        suggestionSearch
                .requestSuggestion((new SuggestionSearchOption())
                        .keyword(cs.toString()).city(city));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        searchPoint(mapStatus.target);
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        AddressSelectAdapter addressSelectAdapter = new AddressSelectAdapter(reverseGeoCodeResult.getPoiList());
        binding.addressList.setAdapter(addressSelectAdapter);
        addressSelectAdapter.setOnAddressSelectListener(this);
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        AddressSelectAdapter addressSelectAdapter = new AddressSelectAdapter(poiResult.getAllPoi());
        binding.addressList.setAdapter(addressSelectAdapter);
        addressSelectAdapter.setOnAddressSelectListener(this);
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        EventBus.getDefault().post(new AddressSelectEvent().setAddressType(AddressSelectEvent.DETAILINFO).setPoiDetailResult(poiDetailResult));
        finish();
    }

    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {
        if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
            return;
        }
        AddressSearchAdapter sugAdapter = new AddressSearchAdapter(suggestionResult.getAllSuggestions());
        binding.searchview.setAdapter(sugAdapter);
        binding.searchview.setOnItemClickListener(this);
        sugAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    @Override
    public void onAddressSelectListener(View view, PoiInfo poiInfo, int poistion) {
        EventBus.getDefault().post(new AddressSelectEvent().setAddressType(AddressSelectEvent.POIINFO).setPoiInfo(poiInfo));
        finish();
    }
}
