package com.j1j2.data.model.requestbody;

/**
 * Created by alienzxh on 16-9-7.
 */
public class ActivityOrderSubmitBody {

    /// 活动商品ID
    private int ProductId;

    /// 数量
    private int Quantity;

    /// 领奖人 如果有
    private String UserContacter;

    /// 联系电话 如果有
    private String UserPhone;

    /// 地址信息 如果有
    private String Address;

    private int ServiceId;

    private int ShopId;

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getUserContacter() {
        return UserContacter;
    }

    public void setUserContacter(String userContacter) {
        UserContacter = userContacter;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getServiceId() {
        return ServiceId;
    }

    public void setServiceId(int serviceId) {
        ServiceId = serviceId;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }
}
