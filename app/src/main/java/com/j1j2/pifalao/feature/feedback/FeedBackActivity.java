package com.j1j2.pifalao.feature.feedback;

import android.databinding.DataBindingUtil;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityFeedbackBinding;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-4-6.
 */
@RequireBundler
public class FeedBackActivity extends BaseActivity{

    ActivityFeedbackBinding binding;
    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feedback);
    }

    @Override
    protected void initViews() {

    }
}
