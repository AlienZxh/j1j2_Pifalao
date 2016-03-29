package com.j1j2.pifalao.app;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.SparseArray;

import com.j1j2.data.model.ProductUnit;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.pifalao.BR;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by alienzxh on 16-3-26.
 */
public class ShopCart extends BaseObservable {
    private SparseArray<Integer> shopCartItemBaseUnitNum;
    private int AllUnitNum;
    private double AllRetailPrice;
    private double AllMemberPrice;
    private double AllSave;

    public ShopCart() {
        AllUnitNum = 0;
        shopCartItemBaseUnitNum = new SparseArray<>();
    }

    public void setShopCartItemList(List<ShopCartItem> shopCartItemList) {
        if (null == shopCartItemList || shopCartItemList.size() <= 0) {
            clear();
            return;
        }
        shopCartItemBaseUnitNum.clear();
        int allUnitNum = 0;
        double allRetailPrice = 0;
        double allMemberPrice = 0;
        double allSave = 0;
        int key = 0;
        int value = 0;
        for (ShopCartItem shopCartItem : shopCartItemList) {
            key = shopCartItem.getProductMainId();
            value = (shopCartItemBaseUnitNum.get(key) == null ? 0 : shopCartItemBaseUnitNum.get(key)) + shopCartItem.getQuantity() * shopCartItem.getScalingFactor();
            shopCartItemBaseUnitNum.put(key, value);
            allUnitNum += shopCartItem.getQuantity();
            allRetailPrice += shopCartItem.getQuantity() * shopCartItem.getRetailPrice();
            allMemberPrice += shopCartItem.getQuantity() * shopCartItem.getMemberPrice();
        }
        allSave = allRetailPrice - allMemberPrice;
        this.AllUnitNum = allUnitNum;
        this.AllMemberPrice = allMemberPrice;
        this.AllRetailPrice = allRetailPrice;
        this.AllSave = allSave;
        notifyPropertyChanged(BR.allUnitNum);
        notifyPropertyChanged(BR.shopCartItemBaseUnitNum);
        notifyPropertyChanged(BR.allMemberPrice);
        notifyPropertyChanged(BR.allRetailPrice);
        notifyPropertyChanged(BR.allSave);
    }

    public void addUnitWitQuantity(ProductUnit unit, int quantity) {
        int key = 0;
        int value = 0;
        key = unit.getProductMainId();
        value = (shopCartItemBaseUnitNum.get(key) == null ? 0 : shopCartItemBaseUnitNum.get(key)) + quantity * unit.getFactor();
        shopCartItemBaseUnitNum.put(key, value);
        AllUnitNum += quantity;
        AllRetailPrice += unit.getRetialPrice() * quantity;
        AllMemberPrice += unit.getMemberPrice() * quantity;
        AllSave = AllRetailPrice - AllMemberPrice;
        notifyPropertyChanged(BR.allUnitNum);
        notifyPropertyChanged(BR.shopCartItemBaseUnitNum);
        notifyPropertyChanged(BR.allMemberPrice);
        notifyPropertyChanged(BR.allRetailPrice);
        notifyPropertyChanged(BR.allSave);
    }

    public void clear() {
        shopCartItemBaseUnitNum.clear();
        AllUnitNum = 0;
        AllRetailPrice = 0;
        AllMemberPrice = 0;
        AllSave = 0;
        notifyPropertyChanged(BR.allUnitNum);
        notifyPropertyChanged(BR.shopCartItemBaseUnitNum);
        notifyPropertyChanged(BR.allMemberPrice);
        notifyPropertyChanged(BR.allRetailPrice);
        notifyPropertyChanged(BR.allSave);
    }

    @Bindable
    public int getAllUnitNum() {
        return AllUnitNum;
    }

    @Bindable
    public SparseArray<Integer> getShopCartItemBaseUnitNum() {
        return shopCartItemBaseUnitNum;
    }

    @Bindable
    public double getAllRetailPrice() {
        return AllRetailPrice;
    }

    @Bindable
    public double getAllMemberPrice() {
        return AllMemberPrice;
    }

    @Bindable
    public double getAllSave() {
        return AllSave;
    }
}
