package com.j1j2.pifalao.app;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.j1j2.data.model.UnReadInfo;
import com.j1j2.pifalao.BR;

/**
 * Created by alienzxh on 16-3-28.
 */
public class UnReadInfoManager extends BaseObservable {
    private boolean hasUnRead;
    private int unReadOrderCount;
    private int unReadPushMessageCount;
    private int userFavoritesCount;

    private static UnReadInfoManager unReadInfoManager = null;

    public static UnReadInfoManager getInstance() {
        if (unReadInfoManager == null) {
            unReadInfoManager = new UnReadInfoManager();
        }
        return unReadInfoManager;
    }


    private UnReadInfoManager() {
        hasUnRead = false;
        unReadOrderCount = 0;
        unReadPushMessageCount = 0;
        userFavoritesCount = 0;
    }

    public void setUnReadInfo(UnReadInfo unReadInfo) {
        unReadOrderCount = unReadInfo.getUnReadOrderCount();
        unReadPushMessageCount = unReadInfo.getUnReadPushMessageCount();
        userFavoritesCount = unReadInfo.getUserFavoritesCount();
        if (unReadOrderCount + unReadPushMessageCount > 0) {
            hasUnRead = true;
        } else {
            hasUnRead = false;
        }
        notifyPropertyChanged(BR.hasUnRead);
        notifyPropertyChanged(BR.unReadInfoManager);
        notifyPropertyChanged(BR.unReadOrderCount);
        notifyPropertyChanged(BR.userFavoritesCount);
    }

    public void clear() {
        hasUnRead = false;
        unReadOrderCount = 0;
        unReadPushMessageCount = 0;
        userFavoritesCount = 0;
        notifyPropertyChanged(BR.hasUnRead);
        notifyPropertyChanged(BR.unReadInfoManager);
        notifyPropertyChanged(BR.unReadOrderCount);
        notifyPropertyChanged(BR.userFavoritesCount);
    }

    @Bindable
    public boolean isHasUnRead() {
        return hasUnRead;
    }

    @Bindable
    public int getUnReadOrderCount() {
        return unReadOrderCount;
    }

    @Bindable
    public int getUnReadPushMessageCount() {
        return unReadPushMessageCount;
    }

    @Bindable
    public int getUserFavoritesCount() {
        return userFavoritesCount;
    }
}
