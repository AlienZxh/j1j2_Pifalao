package com.j1j2.pifalao.app.sharedpreferences;

import com.j1j2.data.model.FreightType;
import com.j1j2.pifalao.app.sharedpreferences.TypeAdapter.FreightTypeAdapter;

import net.orange_box.storebox.annotations.method.KeyByString;
import net.orange_box.storebox.annotations.method.RemoveMethod;
import net.orange_box.storebox.annotations.method.TypeAdapter;

/**
 * Created by alienzxh on 16-4-11.
 */
public interface FreightTypePrefrence {
    String KEY_DELIVERY_FREIGHT = "key_delivery_freight";
    String KEY_PICKBYSELF_FREIGHT = "key_pickbyself_freight";


    @KeyByString(KEY_DELIVERY_FREIGHT)
    @TypeAdapter(FreightTypeAdapter.class)
    FreightType getDeliveryFreightType(FreightType mDefault);

    @KeyByString(KEY_DELIVERY_FREIGHT)
    @TypeAdapter(FreightTypeAdapter.class)
    FreightTypePrefrence setDeliveryFreightType(FreightType freightType);

    @KeyByString(KEY_DELIVERY_FREIGHT)
    @RemoveMethod
    FreightTypePrefrence removeDeliveryFreightType();


    @KeyByString(KEY_PICKBYSELF_FREIGHT)
    @TypeAdapter(FreightTypeAdapter.class)
    FreightType getPickbySelfFreightType(FreightType mDefault);

    @KeyByString(KEY_PICKBYSELF_FREIGHT)
    @TypeAdapter(FreightTypeAdapter.class)
    FreightTypePrefrence setPickbySelfFreightType(FreightType freightType);

    @KeyByString(KEY_PICKBYSELF_FREIGHT)
    @RemoveMethod
    FreightTypePrefrence removePickbySelfFreightType();
}
