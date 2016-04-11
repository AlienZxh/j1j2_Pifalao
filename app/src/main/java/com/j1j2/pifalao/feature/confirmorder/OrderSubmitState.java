package com.j1j2.pifalao.feature.confirmorder;

import android.databinding.ObservableDouble;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.j1j2.data.model.Address;
import com.j1j2.data.model.Coupon;
import com.j1j2.data.model.FreightType;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.pifalao.app.Constant;

/**
 * Created by alienzxh on 16-4-6.
 */
public class OrderSubmitState {

    public final int ModuleId;//服务模块

    public ObservableField<FreightType> FreightTypeDetail;// 选择的运费方式  2:自提  5：送货上门 通过接口查询

    public ObservableField<Address> AddressDetail;// 用户选择的地址的主键编号
    public ObservableField<ServicePoint> ServicePointDetail;// 用户选择的自提或者关联的服务点

    public ObservableDouble FreightValue;

    public ObservableField<Coupon> Coupon;// 选择的商品优惠券的字符串 编码 前台需要传递该参数

    public ObservableField<String> PredictSendTime;// 订单预计配送时间

    public String OrderMemo;// 订单其它备注

    public OrderSubmitState(int moduleId,ServicePoint servicePoint) {
        ModuleId = moduleId;
        this.FreightTypeDetail = new ObservableField<>();

        this.ServicePointDetail = new ObservableField<>(servicePoint);
        this.AddressDetail = new ObservableField<>();

        this.PredictSendTime = new ObservableField<>();
        this.Coupon = new ObservableField<>();
        this.FreightValue = new ObservableDouble(0);
        this.OrderMemo = "";
    }


}
