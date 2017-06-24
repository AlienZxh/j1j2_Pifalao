package com.j1j2.pifalao.app.sharedpreferences;

import com.j1j2.data.model.City;
import com.j1j2.data.model.Shop;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.pifalao.app.sharedpreferences.TypeAdapter.CityAdapter;
import com.j1j2.pifalao.app.sharedpreferences.TypeAdapter.ModuleAdapter;
import com.j1j2.pifalao.app.sharedpreferences.TypeAdapter.ServicePointAdapter;
import com.j1j2.pifalao.app.sharedpreferences.TypeAdapter.StringListTypeAdapter;

import net.orange_box.storebox.annotations.method.KeyByString;
import net.orange_box.storebox.annotations.method.RemoveMethod;
import net.orange_box.storebox.annotations.method.TypeAdapter;

import java.util.List;

/**
 * Created by alienzxh on 16-3-25.
 */
public interface UserRelativePreference {
    String KEY_ISFIRST = "key_isfirst";
    String KEY_SELECTED_CITY = "key_selected_city";
    String KEY_SELECTED_SERVICEPOINT = "key_selected_servicepoint";
    String KEY_SELECTED_MODULE = "key_selected_module";
    String KEY_HISTORY_KEY = "key_history_key";
    String KEY_USER_IMG = "key_user_img";
    String KEY_SHOW_DELIVERYAREA = "key_show_deliveryarea";
    String KEY_SHOW_LOCATION = "key_show_location";
    String KEY_SHOW_HIGHLIGHT = "key_show_highlight";
    String KEY_SHOW_BRIBERYMONEY = "key_show_briberymoney";

    @KeyByString(KEY_ISFIRST)
    boolean getIsFirst(boolean mDefault);

    @KeyByString(KEY_ISFIRST)
    UserRelativePreference setIsFirst(boolean isFirst);

    @KeyByString(KEY_ISFIRST)
    @RemoveMethod
    UserRelativePreference removeIsFirst();


    @KeyByString(KEY_SELECTED_CITY)
    @TypeAdapter(CityAdapter.class)
    City getSelectedCity(City mDefault);

    @KeyByString(KEY_SELECTED_CITY)
    @TypeAdapter(CityAdapter.class)
    UserRelativePreference setSelectedCity(City city);

    @KeyByString(KEY_SELECTED_CITY)
    @RemoveMethod
    UserRelativePreference removeSelectedCity();


    @KeyByString(KEY_SELECTED_SERVICEPOINT)
    @TypeAdapter(ServicePointAdapter.class)
    Shop getSelectedServicePoint(Shop mDefault);

    @KeyByString(KEY_SELECTED_SERVICEPOINT)
    @TypeAdapter(ServicePointAdapter.class)
    UserRelativePreference setSelectedServicePoint(Shop shop);

    @KeyByString(KEY_SELECTED_SERVICEPOINT)
    @RemoveMethod
    UserRelativePreference removeSelectedServicePoint();


    @KeyByString(KEY_SELECTED_MODULE)
    @TypeAdapter(ModuleAdapter.class)
    ShopSubscribeService getSelectedModule(ShopSubscribeService mDefault);

    @KeyByString(KEY_SELECTED_MODULE)
    @TypeAdapter(ModuleAdapter.class)
    UserRelativePreference setSelectedModule(ShopSubscribeService shopSubscribeService);

    @KeyByString(KEY_SELECTED_MODULE)
    @RemoveMethod
    UserRelativePreference removeSelectedModule();


    @KeyByString(KEY_HISTORY_KEY)
    @TypeAdapter(StringListTypeAdapter.class)
    List<String> getHistoryKey(List<String> mDefault);

    @KeyByString(KEY_HISTORY_KEY)
    @TypeAdapter(StringListTypeAdapter.class)
    UserRelativePreference setHistoryKey(List<String> keys);

    @KeyByString(KEY_HISTORY_KEY)
    @RemoveMethod
    UserRelativePreference removeHistoryKey();

    @KeyByString(KEY_USER_IMG)
    String getUserImg(String mDefault);

    @KeyByString(KEY_USER_IMG)
    UserRelativePreference setUserImg(String imgPaht);

    @KeyByString(KEY_USER_IMG)
    @RemoveMethod
    UserRelativePreference removeUserImg();

    @KeyByString(KEY_SHOW_DELIVERYAREA)
    boolean getShowDeliveryArea(boolean mDefault);

    @KeyByString(KEY_SHOW_DELIVERYAREA)
    UserRelativePreference setShowDeliveryArea(boolean showDeliveryArea);

    @KeyByString(KEY_SHOW_DELIVERYAREA)
    @RemoveMethod
    UserRelativePreference removeShowDeliveryArea();

    @KeyByString(KEY_SHOW_LOCATION)
    boolean getShowLocation(boolean mDefault);

    @KeyByString(KEY_SHOW_LOCATION)
    UserRelativePreference setShowLocation(boolean showLocation);

    @KeyByString(KEY_SHOW_LOCATION)
    @RemoveMethod
    UserRelativePreference removeShowLocation();

    @KeyByString(KEY_SHOW_HIGHLIGHT)
    boolean getShowHighLight(boolean mDefault);

    @KeyByString(KEY_SHOW_HIGHLIGHT)
    UserRelativePreference setShowHighLight(boolean showHighLight);

    @KeyByString(KEY_SHOW_HIGHLIGHT)
    @RemoveMethod
    UserRelativePreference removeShowHighLight();

    @KeyByString(KEY_SHOW_BRIBERYMONEY)
    boolean getShowBriberyMoney(boolean mDefault);

    @KeyByString(KEY_SHOW_BRIBERYMONEY)
    UserRelativePreference setShowBriberyMoney(boolean showBriberyMoney);

    @KeyByString(KEY_SHOW_BRIBERYMONEY)
    @RemoveMethod
    UserRelativePreference removeShowBriberyMoney();

}
