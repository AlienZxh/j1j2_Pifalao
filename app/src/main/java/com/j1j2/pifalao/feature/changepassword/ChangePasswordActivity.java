package com.j1j2.pifalao.feature.changepassword;

import android.databinding.DataBindingUtil;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityChangepasswordBinding;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-24.
 */
@RequireBundler
public class ChangePasswordActivity extends BaseActivity {


    ActivityChangepasswordBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_changepassword);
    }

    @Override
    protected void initViews() {

    }
}
