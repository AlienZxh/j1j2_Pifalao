package com.j1j2.pifalao.feature.addressselect;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
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

import java.util.ArrayList;
import java.util.List;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-31.
 */
@RequireBundler
public class AddressSelectActivity extends BaseMapActivity implements OnGetDistricSearchResultListener, OnGetSuggestionResultListener, OnGetPoiSearchResultListener, AddressSelectAdapter.OnAddressSelectListener, View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener {
    ActivityAddressselectBinding binding;

    @Arg
    String city;

    @Arg
    String district;

    DistrictSearch districtSearch;
    DistrictSearchOption districtSearchOption;

    PoiSearch poiSearch;
    PoiNearbySearchOption poiNearbySearchOption;
    PoiBoundSearchOption poiBoundSearchOption;

    SuggestionSearch suggestionSearch;
    SuggestionSearchOption suggestionSearchOption;

    private OverlayManager servicepointOverlayManager = null;
    private List<OverlayOptions> overlayOptionses = null;

    @Override
    protected void initMap() {
        getMapView().showZoomControls(false);
        getMapView().showScaleControl(true);

        overlayOptionses = new ArrayList<>();
        servicepointOverlayManager = new AddressOverlayManager(mBaiduMap, overlayOptionses);

        LatLng point = new LatLng(28.175983, 113.023015);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(point, 14.0f);
        mBaiduMap.setMapStatus(u);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);// 开启定位图层

        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(this);
        districtSearch = DistrictSearch.newInstance();
        districtSearch.setOnDistrictSearchListener(this);
        suggestionSearch = SuggestionSearch.newInstance();
        suggestionSearch.setOnGetSuggestionResultListener(this);
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
        districtSearch.destroy();
        suggestionSearch.destroy();
        poiSearch.destroy();
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLocationEvent(LocationEvent event) {
        BDLocation location = event.getLocation();
        if (location == null)
            return;
        refreshMyLocation(location);
        if (poiSearch == null || poiNearbySearchOption != null)
            return;
        poiNearbySearchOption = new PoiNearbySearchOption()
                .sortType(PoiSortType.distance_from_near_to_far)
                .location(new LatLng(location.getLatitude(), location.getLongitude()))
                .radius(10000)
                .keyword("小区")
                .pageNum(1).pageCapacity(20);
        poiSearch.searchNearby(poiNearbySearchOption);

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
        if (mBaiduMap != null)
            mBaiduMap.setMyLocationData(locData);
    }

    public void refreshPoisMarker(List<PoiInfo> poiInfos) {
        if (overlayOptionses.size() > 0) {
            servicepointOverlayManager.removeFromMap();
            overlayOptionses.clear();
        }
        if (null == poiInfos || poiInfos.size() <= 0)
            return;
        BitmapDescriptor markIcon = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_servicepoint);
        int i = 0;
        for (PoiInfo poiInfo : poiInfos) {
            MarkerOptions mark = new MarkerOptions()//
                    .position(poiInfo.location)//
                    .icon(markIcon)//
                    .zIndex(i)//
                    .draggable(false)//
                    .animateType(MarkerOptions.MarkerAnimateType.none);
            // 把数据的shopId保存到队列
            overlayOptionses.add(mark);
            i++;
        }
        servicepointOverlayManager.addToMap();
        servicepointOverlayManager.zoomToSpan();
        markIcon.recycle();
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
    public void onGetDistrictResult(DistrictResult districtResult) {

    }


    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        refreshPoisMarker(poiResult.getAllPoi());
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
    public void onAddressSelectListener(View view, PoiInfo poiInfo, int poistion) {
        EventBus.getDefault().post(new AddressSelectEvent().setAddressType(AddressSelectEvent.POIINFO).setPoiInfo(poiInfo));
        finish();
    }

    public class AddressOverlayManager extends OverlayManager {
        private List<OverlayOptions> overlayOptionses;

        public AddressOverlayManager(BaiduMap baiduMap, List<OverlayOptions> overlayOptionses) {
            super(baiduMap);
            this.overlayOptionses = overlayOptionses;
        }

        @Override
        public List<OverlayOptions> getOverlayOptions() {
            return this.overlayOptionses;
        }

        @Override
        public boolean onMarkerClick(Marker marker) {
            return false;
        }

        @Override
        public boolean onPolylineClick(Polyline polyline) {
            return false;
        }
    }
}
