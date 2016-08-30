package com.j1j2.pifalao.feature.unsubscribe;

import android.databinding.DataBindingUtil;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityUnsubscribeDeliveryBinding;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-8-30.
 */
@RequireBundler
public class UnsubscribeDeliveryActivity extends BaseActivity {

    ActivityUnsubscribeDeliveryBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_unsubscribe_delivery);
    }

    @Override
    protected void initViews() {

    }
}
