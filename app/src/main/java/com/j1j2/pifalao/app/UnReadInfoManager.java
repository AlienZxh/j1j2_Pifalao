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
    private int foldRedPacketCount;

    private static UnReadInfoManager mInstance = null;

    public static UnReadInfoManager getInstance() {
        if (mInstance == null) {
            synchronized (UnReadInfoManager.class) {
                if (mInstance == null) {
                    mInstance = new UnReadInfoManager();
                }
            }
        }
        return mInstance;
    }

    private UnReadInfoManager() {
        hasUnRead = false;
        unReadOrderCount = 0;
        unReadPushMessageCount = 0;
        userFavoritesCount = 0;
        foldRedPacketCount = 0;
    }

    public void setUnReadInfo(UnReadInfo unReadInfo) {
        unReadOrderCount = unReadInfo.getUnReadOrderCount();
        unReadPushMessageCount = unReadInfo.getUnReadPushMessageCount();
        userFavoritesCount = unReadInfo.getUserFavoritesCount();
        foldRedPacketCount = unReadInfo.getFoldRedPacketCount();
        if (unReadOrderCount + unReadPushMessageCount + foldRedPacketCount > 0) {
            hasUnRead = true;
        } else {
            hasUnRead = false;
        }
        notifyPropertyChanged(BR.hasUnRead);
        notifyPropertyChanged(BR.unReadPushMessageCount);
        notifyPropertyChanged(BR.unReadOrderCount);
        notifyPropertyChanged(BR.userFavoritesCount);
        notifyPropertyChanged(BR.foldRedPacketCount);
    }

    public void clear() {
        hasUnRead = false;
        unReadOrderCount = 0;
        unReadPushMessageCount = 0;
        userFavoritesCount = 0;
        foldRedPacketCount = 0;
        notifyPropertyChanged(BR.hasUnRead);
        notifyPropertyChanged(BR.unReadPushMessageCount);
        notifyPropertyChanged(BR.unReadOrderCount);
        notifyPropertyChanged(BR.userFavoritesCount);
        notifyPropertyChanged(BR.foldRedPacketCount);
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

    @Bindable
    public int getFoldRedPacketCount() {
        return foldRedPacketCount;
    }
}
