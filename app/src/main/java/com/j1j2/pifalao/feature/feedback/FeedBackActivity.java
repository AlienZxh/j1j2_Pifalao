package com.j1j2.pifalao.feature.feedback;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityFeedbackBinding;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-4-6.
 */
@RequireBundler
public class FeedBackActivity extends BaseActivity implements View.OnClickListener {

    ActivityFeedbackBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feedback);
        binding.backBtn.setOnClickListener(this);
        binding.commitBtn.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.commitBtn) {
            if (binding.adviceTitleEdit.getText().toString().length() <= 0) {
                toastor.showSingletonToast("标题不能为空");
                return;
            }
            if (binding.adviceContentEdit.getText().toString().length() <= 0) {
                toastor.showSingletonToast("内容不能为空");
                return;
            }
            toastor.showSingletonToast("提交成功，您的宝贵意见是我们的改进的方向");
            finish();
        }
    }
}
