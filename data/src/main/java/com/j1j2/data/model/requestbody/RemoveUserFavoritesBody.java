package com.j1j2.data.model.requestbody;

import java.util.List;

/**
 * Created by alienzxh on 16-3-10.
 */
public class RemoveUserFavoritesBody {

    private List<Integer> MainIdList;

    public List<Integer> getMainIdList() {
        return MainIdList;
    }

    public void setMainIdList(List<Integer> mainIdList) {
        MainIdList = mainIdList;
    }
}
