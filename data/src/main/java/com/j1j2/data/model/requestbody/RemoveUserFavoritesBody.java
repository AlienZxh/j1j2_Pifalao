package com.j1j2.data.model.requestbody;

import java.util.List;

/**
 * Created by alienzxh on 16-3-10.
 */
public class RemoveUserFavoritesBody {

    private List<Integer> ProductIdList;

    public List<Integer> getProductIdList() {
        return ProductIdList;
    }

    public void setProductIdList(List<Integer> productIdList) {
        ProductIdList = productIdList;
    }

    @Override
    public String toString() {
        return "RemoveUserFavoritesBody{" +
                "ProductIdList=" + ProductIdList +
                '}';
    }
}
