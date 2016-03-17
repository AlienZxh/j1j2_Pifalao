package com.j1j2.pifalao.feature.search;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.model.Module;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivitySearchBinding;
import com.j1j2.pifalao.databinding.ActivityServicesBinding;
import com.j1j2.pifalao.feature.search.di.SearchModule;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-12.
 */
@RequireBundler
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    ActivitySearchBinding binding;

    @Arg
    Module module;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.setOnClick(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new SearchModule()).inject(this);
    }

    @Override
    public void onClick(View v) {
        navigate.navigateToProductsActivityFromSearch(this, null, false, binding.searchview.getText().toString(), module);

    }
}
