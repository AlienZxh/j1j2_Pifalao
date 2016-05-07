package com.j1j2.pifalao.feature.addaddress;

import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.j1j2.data.model.Address;
import com.j1j2.data.model.City;
import com.j1j2.data.model.requestbody.EditorUserRecivingAddressBody;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.AddressSelectEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityAddaddressBinding;
import com.j1j2.pifalao.feature.addaddress.di.AddAddressModule;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import in.workarounds.bundler.annotations.Required;

/**
 * Created by alienzxh on 16-3-31.
 */
@RequireBundler
public class AddAddressActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static final int ADD_NEW_ADDRESS = 1;
    public static final int EDIT_ADDRESS = 2;

    ActivityAddaddressBinding binding;

    @Inject
    AddAddressViewModel addAddressViewModel;

    @Arg
    public int activityType;

    @Arg
    @Required(false)
    Address address;

    @Inject
    UserRelativePreference userRelativePreference;

    AddressSpinnerAdapter citySpinnerAdapter;
    AddressSpinnerAdapter districtSpinnerAdapter;

    double lat;
    double lng;
    String userName;
    String userMobile;
//    String city;
//    String district;
//    String addressLoction;
    String addressDetail;
    boolean isDefault = false;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addaddress);
        binding.setAddAddressViewModel(addAddressViewModel);
        binding.defaultCheck.setOnCheckedChangeListener(this);
        isDefault = binding.defaultCheck.isChecked();
    }

    @Override
    protected void initViews() {
//        initCitys();
    }

    public void initDatas() {
        if (activityType == EDIT_ADDRESS) {
            binding.username.setText(address.getReceiverName());
            userName = address.getReceiverName();
            binding.userphone.setText(address.getReceiverTel());
            userMobile = address.getReceiverTel();
//            binding.addresscity.setSelection(citySpinnerAdapter.getPosition(address.getAddressSegementF()));
//            binding.addressdistrict.setSelection(districtSpinnerAdapter.getPosition(address.getAddressSegementS()));
//            setAddressLoction(address.getAddressSegementT(), address.getLat(), address.getLng());
            binding.addressdetail.setText(address.getAddress());
            addressDetail = address.getAddress();
            binding.defaultCheck.setChecked(address.isDefaultAddress());
            isDefault = address.isDefaultAddress();
        }
    }

//    public void initCitys() {
//        List<String> cityStrings = new ArrayList<>();
//        cityStrings.add("长沙");
//        citySpinnerAdapter = new AddressSpinnerAdapter(cityStrings);
//        binding.addresscity.setAdapter(citySpinnerAdapter);
//        binding.addresscity.setOnItemSelectedListener(this);
//        addAddressViewModel.queryDistrict(userRelativePreference.getSelectedCity(null).getPCCId());
//        if (cityStrings.size() > 0)
//            city = cityStrings.get(0);
//    }

//    public void initDistrict(List<City> cities) {
//        List<String> districtStrings = new ArrayList<>();
//        for (City city : cities) {
//            districtStrings.add(city.getPCCName());
//        }
//        districtSpinnerAdapter = new AddressSpinnerAdapter(districtStrings);
//        binding.addressdistrict.setAdapter(districtSpinnerAdapter);
//        binding.addressdistrict.setOnItemSelectedListener(this);
//        if (districtStrings.size() > 0)
//            district = districtStrings.get(0);
//        initDatas();
//    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new AddAddressModule(this)).inject(this);
    }


    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.defaultBtn)
            binding.defaultCheck.setChecked(!binding.defaultCheck.isChecked());
//        if (v == binding.addressSelect) {
//            if (city == null || district == null) {
//                toastor.showSingletonToast("请选择城市、区域");
//                return;
//            }
//            navigate.navigateToAddressSelect(this, null, false, city, district);
//        }
        if (v == binding.addAddress) {
            userName = binding.username.getText().toString();
            userMobile = binding.userphone.getText().toString();
//            addressLoction = binding.addresslocation.getText().toString();
            addressDetail = binding.addressdetail.getText().toString();
            if (TextUtils.isEmpty(userName)) {
                toastor.showSingletonToast("请填写收货人姓名");
                return;
            }
            if (TextUtils.isEmpty(userMobile)) {
                toastor.showSingletonToast("请填写收货人手机号");
                return;
            }
            if (userMobile.length() != 11) {
                toastor.showSingletonToast("请填写正确的手机号");
                return;
            }
//            if (TextUtils.isEmpty(addressLoction)) {
//                toastor.showSingletonToast("请选择收货人位置");
//                return;
//            }
            if (TextUtils.isEmpty(addressDetail)) {
                toastor.showSingletonToast("请填写收货人详细地址");
                return;
            }
            EditorUserRecivingAddressBody editorUserRecivingAddressBody = new EditorUserRecivingAddressBody();
//            editorUserRecivingAddressBody.setAddressSegementF(city);
//            editorUserRecivingAddressBody.setAddressSegementS(district);
//            editorUserRecivingAddressBody.setAddressSegementT(addressLoction);
            editorUserRecivingAddressBody.setAddress(addressDetail);
            editorUserRecivingAddressBody.setReceiverName(userName);
            editorUserRecivingAddressBody.setReceiverTel(userMobile);
            editorUserRecivingAddressBody.setIsDefaultAddress(isDefault);
            editorUserRecivingAddressBody.setLat(lat);
            editorUserRecivingAddressBody.setLng(lng);
            if (activityType == EDIT_ADDRESS) {
                editorUserRecivingAddressBody.setAddressId(address.getAddressId());
            }
            addAddressViewModel.addUserAddress(editorUserRecivingAddressBody);
        }
    }


//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        if (parent == binding.addresscity) {
//            city = (String) parent.getAdapter().getItem(position);
//        }
//        if (parent == binding.addressdistrict) {
//            district = (String) parent.getAdapter().getItem(position);
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isDefault = isChecked;
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onAddressSelectEvent(AddressSelectEvent event) {
//        if (event.getAddressType() == AddressSelectEvent.POIINFO) {
//            PoiInfo poiInfo = event.getPoiInfo();
//            setAddressLoction(poiInfo.name, poiInfo.location.latitude, poiInfo.location.longitude);
//        }
//        if (event.getAddressType() == AddressSelectEvent.DETAILINFO) {
//            PoiDetailResult poiInfo = event.getPoiDetailResult();
//            setAddressLoction(poiInfo.getName(), poiInfo.getLocation().latitude, poiInfo.getLocation().longitude);
//        }
//
//    }

//    public void setAddressLoction(String mAddressLoction, double mlat, double mlng) {
//        binding.addresslocation.setText(mAddressLoction);
//        binding.addresslocation.setTextColor(0xff333333);
//        addressLoction = mAddressLoction;
//        lat = mlat;
//        lng = mlng;
//    }
}
