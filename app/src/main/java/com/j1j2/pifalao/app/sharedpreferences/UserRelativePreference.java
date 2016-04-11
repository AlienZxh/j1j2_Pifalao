package com.j1j2.pifalao.app.sharedpreferences;

import com.j1j2.data.model.City;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.app.sharedpreferences.TypeAdapter.CityAdapter;
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
    public static final String KEY_ISFIRST = "key_isfirst";
    public static final String KEY_SELECTED_CITY = "key_selected_city";
    public static final String KEY_SELECTED_SERVICEPOINT = "key_selected_servicepoint";
    public static final String KEY_HISTORY_KEY = "key_history_key";

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
    ServicePoint getSelectedServicePoint(ServicePoint mDefault);

    @KeyByString(KEY_SELECTED_SERVICEPOINT)
    @TypeAdapter(ServicePointAdapter.class)
    UserRelativePreference setSelectedServicePoint(ServicePoint servicePoint);

    @KeyByString(KEY_SELECTED_SERVICEPOINT)
    @RemoveMethod
    UserRelativePreference removeSelectedServicePoint();


    @KeyByString(KEY_HISTORY_KEY)
    @TypeAdapter(StringListTypeAdapter.class)
    List<String> getHistoryKey(List<String> mDefault);

    @KeyByString(KEY_HISTORY_KEY)
    @TypeAdapter(StringListTypeAdapter.class)
    UserRelativePreference setHistoryKey(List<String> keys);

    @KeyByString(KEY_HISTORY_KEY)
    @RemoveMethod
    UserRelativePreference removeHistoryKey();

}
