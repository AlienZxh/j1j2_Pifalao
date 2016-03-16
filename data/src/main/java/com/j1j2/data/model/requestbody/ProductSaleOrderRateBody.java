package com.j1j2.data.model.requestbody;

/**
 * Created by alienzxh on 16-3-10.
 */
public class ProductSaleOrderRateBody {
    private int OrderId;
    private byte FoodTaste;
    private byte DeliverSpeed;
    private byte ServiceAttitude;
    private String Comment;

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public byte getFoodTaste() {
        return FoodTaste;
    }

    public void setFoodTaste(byte foodTaste) {
        FoodTaste = foodTaste;
    }

    public byte getDeliverSpeed() {
        return DeliverSpeed;
    }

    public void setDeliverSpeed(byte deliverSpeed) {
        DeliverSpeed = deliverSpeed;
    }

    public byte getServiceAttitude() {
        return ServiceAttitude;
    }

    public void setServiceAttitude(byte serviceAttitude) {
        ServiceAttitude = serviceAttitude;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    @Override
    public String toString() {
        return "ProductSaleOrderRateBody{" +
                "OrderId=" + OrderId +
                ", FoodTaste=" + FoodTaste +
                ", DeliverSpeed=" + DeliverSpeed +
                ", ServiceAttitude=" + ServiceAttitude +
                ", Comment='" + Comment + '\'' +
                '}';
    }
}
