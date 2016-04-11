package com.j1j2.pifalao.feature.home.deliveryhome;

/**
 * Created by alienzxh on 16-3-30.
 */
public class DeliveryHomeViewModel {

    private DeliveryHomeActivity deliveryHomeActivity;


    public DeliveryHomeViewModel(DeliveryHomeActivity deliveryHomeActivity) {
        this.deliveryHomeActivity = deliveryHomeActivity;
    }

    public DeliveryHomeActivity getDeliveryHomeActivity() {
        return deliveryHomeActivity;
    }
}
