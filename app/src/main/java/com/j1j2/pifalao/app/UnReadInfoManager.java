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
    private UnReadInfo unReadInfo;

    public UnReadInfoManager() {
        hasUnRead = false;
        UnReadInfo unReadInfo = new UnReadInfo();
    }

    public void setUnReadInfo(UnReadInfo unReadInfo) {
        this.unReadInfo = unReadInfo;
        if (unReadInfo.getUnReadOrderCount() + unReadInfo.getUnReadPushMessageCount() > 0) {
            hasUnRead = true;
        } else {
            hasUnRead = false;
        }
        notifyPropertyChanged(BR.hasUnRead);
        notifyPropertyChanged(BR.unReadInfo);
    }

    public void clear(){
        hasUnRead = false;
        UnReadInfo unReadInfo = new UnReadInfo();
        notifyPropertyChanged(BR.hasUnRead);
        notifyPropertyChanged(BR.unReadInfo);
    }

    @Bindable
    public boolean isHasUnRead() {
        return hasUnRead;
    }

    @Bindable
    public UnReadInfo getUnReadInfo() {
        return unReadInfo;
    }
}
