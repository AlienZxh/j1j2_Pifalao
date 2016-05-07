package com.j1j2.pifalao.feature.unsubscribe;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.app.service.BackGroundService;
import com.j1j2.pifalao.databinding.ActivityUnsubscribeModuleBinding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.jpush.android.api.JPushInterface;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-4-16.
 */
@RequireBundler
public class UnsubscribeModuleActivity extends BaseActivity implements View.OnClickListener {

    ActivityUnsubscribeModuleBinding binding;

    UnReadInfoManager unReadInfoManager = null;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_unsubscribe_module);
        binding.backBtn.setOnClickListener(this);
        binding.individualBtn.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.individualBtn)
            navigate.navigateToIndividualCenter(this, null, false);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            unReadInfoManager = MainAplication.get(this).getUserComponent().unReadInfoManager();
            binding.setUnReadInfoManager(unReadInfoManager);
        }
    }
}
