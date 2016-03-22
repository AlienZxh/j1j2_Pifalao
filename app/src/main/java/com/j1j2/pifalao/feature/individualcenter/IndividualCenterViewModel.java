package com.j1j2.pifalao.feature.individualcenter;

import android.databinding.ObservableField;

import com.j1j2.data.model.User;
import com.orhanobut.logger.Logger;

/**
 * Created by alienzxh on 16-3-21.
 */
public class IndividualCenterViewModel {

    private IndividualCenterFragment individualCenterFragment;
    public ObservableField<User> user;

    public IndividualCenterViewModel(User user, IndividualCenterFragment individualCenterFragment) {
        this.individualCenterFragment = individualCenterFragment;
        this.user = new ObservableField<>();
        this.user.set(user);
    }

    public IndividualCenterFragment getIndividualCenterFragment() {
        return individualCenterFragment;
    }
}
