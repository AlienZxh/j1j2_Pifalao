package com.j1j2.pifalao.feature.join;

import android.databinding.DataBindingUtil;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityJoinBinding;

/**
 * Created by alienzxh on 16-3-12.
 */
public class JoinActivity extends BaseActivity {

    ActivityJoinBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join);

    }

    @Override
    protected void initViews() {

    }
}
