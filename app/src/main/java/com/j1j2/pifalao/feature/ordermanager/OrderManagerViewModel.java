package com.j1j2.pifalao.feature.ordermanager;

/**
 * Created by alienzxh on 16-3-22.
 */
public class OrderManagerViewModel {
    private OrderManagerActivity orderManagerActivity;

    public OrderManagerViewModel(OrderManagerActivity orderManagerActivity) {
        this.orderManagerActivity = orderManagerActivity;
    }

    public OrderManagerActivity getOrderManagerActivity() {
        return orderManagerActivity;
    }
}
