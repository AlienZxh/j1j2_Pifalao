package com.j1j2.pifalao.app;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.SparseArray;

import com.j1j2.data.model.ProductUnit;
import com.j1j2.data.model.ShopCartItem;
import com.j1j2.pifalao.BR;

import java.util.List;

/**
 * Created by alienzxh on 16-3-26.
 */
public class ShopCart extends BaseObservable {
    private SparseArray<Integer> shopCartItemBaseUnitNum;
    private SparseArray<Integer> shopCartItemAllUnitNum;
    private int AllUnitNum;
    private double AllRetailPrice;
    private double AllMemberPrice;
    private double AllSave;

    public ShopCart() {
        AllUnitNum = 0;
        shopCartItemBaseUnitNum = new SparseArray<>();
        shopCartItemAllUnitNum = new SparseArray<>();
    }

    public void setShopCartItemList(List<ShopCartItem> shopCartItemList) {
        if (null == shopCartItemList || shopCartItemList.size() <= 0) {
            clear();
            return;
        }
        shopCartItemBaseUnitNum.clear();
        shopCartItemAllUnitNum.clear();
        int allUnitNum = 0;
        double allRetailPrice = 0;
        double allMemberPrice = 0;
        double allSave = 0;
        int key = 0;
        int value = 0;
        int allUnitKey = 0;
        int allUnitValue = 0;
        for (ShopCartItem shopCartItem : shopCartItemList) {
            key = shopCartItem.getProductMainId();
//            value = (shopCartItemBaseUnitNum.get(key) == null ? 0 : shopCartItemBaseUnitNum.get(key)) + shopCartItem.getQuantity() * shopCartItem.getScalingFactor();
            value = (shopCartItemBaseUnitNum.get(key) == null ? 0 : shopCartItemBaseUnitNum.get(key)) + shopCartItem.getQuantity();
            shopCartItemBaseUnitNum.put(key, value);

            allUnitKey = shopCartItem.getProductId();
            allUnitValue = (shopCartItemAllUnitNum.get(allUnitKey) == null ? 0 : shopCartItemAllUnitNum.get(allUnitKey)) + shopCartItem.getQuantity();
            shopCartItemAllUnitNum.put(allUnitKey, allUnitValue);

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
        notifyPropertyChanged(BR.shopCartItemAllUnitNum);
        notifyPropertyChanged(BR.shopCartItemBaseUnitNum);
        notifyPropertyChanged(BR.allMemberPrice);
        notifyPropertyChanged(BR.allRetailPrice);
        notifyPropertyChanged(BR.allSave);
    }

    public void addUnitWitQuantity(ProductUnit unit, int quantity) {
        int key = 0;
        int value = 0;
        key = unit.getProductMainId();
//        value = (shopCartItemBaseUnitNum.get(key) == null ? 0 : shopCartItemBaseUnitNum.get(key)) + quantity * unit.getFactor();
        value = (shopCartItemBaseUnitNum.get(key) == null ? 0 : shopCartItemBaseUnitNum.get(key)) + quantity;

        int allUnitKey = 0;
        int allUnitValue = 0;
        allUnitKey = unit.getProductId();
        allUnitValue = (shopCartItemAllUnitNum.get(allUnitKey) == null ? 0 : shopCartItemAllUnitNum.get(allUnitKey)) + quantity;

        shopCartItemBaseUnitNum.put(key, value);
        shopCartItemAllUnitNum.put(allUnitKey, allUnitValue);
        AllUnitNum += quantity;
        AllRetailPrice += unit.getRetialPrice() * quantity;
        AllMemberPrice += unit.getMemberPrice() * quantity;
        AllSave = AllRetailPrice - AllMemberPrice;
        notifyPropertyChanged(BR.allUnitNum);
        notifyPropertyChanged(BR.shopCartItemAllUnitNum);
        notifyPropertyChanged(BR.shopCartItemBaseUnitNum);
        notifyPropertyChanged(BR.allMemberPrice);
        notifyPropertyChanged(BR.allRetailPrice);
        notifyPropertyChanged(BR.allSave);
    }

    public void clear() {
        shopCartItemBaseUnitNum.clear();
        shopCartItemAllUnitNum.clear();
        AllUnitNum = 0;
        AllRetailPrice = 0;
        AllMemberPrice = 0;
        AllSave = 0;
        notifyPropertyChanged(BR.allUnitNum);
        notifyPropertyChanged(BR.shopCartItemAllUnitNum);
        notifyPropertyChanged(BR.shopCartItemBaseUnitNum);
        notifyPropertyChanged(BR.allMemberPrice);
        notifyPropertyChanged(BR.allRetailPrice);
        notifyPropertyChanged(BR.allSave);
    }

    @Bindable
    public SparseArray<Integer> getShopCartItemAllUnitNum() {
        return shopCartItemAllUnitNum;
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

    @Override
    public String toString() {
        return "ShopCart{" +
                "shopCartItemBaseUnitNum=" + shopCartItemBaseUnitNum +
                ", shopCartItemAllUnitNum=" + shopCartItemAllUnitNum +
                ", AllUnitNum=" + AllUnitNum +
                ", AllRetailPrice=" + AllRetailPrice +
                ", AllMemberPrice=" + AllMemberPrice +
                ", AllSave=" + AllSave +
                '}';
    }
}
