package com.j1j2.pifalao.app.event;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.sug.SuggestionResult;

/**
 * Created by alienzxh on 16-4-5.
 */
public class AddressSelectEvent {
    public static final int POIINFO = 0;
    public static final int DETAILINFO = 1;
    private int addressType;
    private PoiInfo poiInfo;
    private PoiDetailResult poiDetailResult;

    public AddressSelectEvent setAddressType(int addressType) {
        this.addressType = addressType;
        return this;
    }

    public AddressSelectEvent setPoiInfo(PoiInfo poiInfo) {
        this.poiInfo = poiInfo;
        return this;
    }

    public AddressSelectEvent setPoiDetailResult(PoiDetailResult poiDetailResult) {
        this.poiDetailResult = poiDetailResult;
        return this;
    }

    public int getAddressType() {
        return addressType;
    }

    public PoiInfo getPoiInfo() {
        return poiInfo;
    }

    public PoiDetailResult getPoiDetailResult() {
        return poiDetailResult;
    }
}
