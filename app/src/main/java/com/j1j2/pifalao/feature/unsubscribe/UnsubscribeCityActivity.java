package com.j1j2.pifalao.feature.unsubscribe;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityUnsubscribeCityBinding;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-4-19.
 */
@RequireBundler
public class UnsubscribeCityActivity extends BaseActivity implements View.OnClickListener {

    ActivityUnsubscribeCityBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_unsubscribe_city);
        binding.backBtn.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
