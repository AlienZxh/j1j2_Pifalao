package com.j1j2.pifalao.feature.search;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.j1j2.common.view.itemdecoration.CustomGridItemDecoration;
import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.model.Module;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.FinishLocationActivityEvent;
import com.j1j2.pifalao.app.event.HistoryKeyChangeEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivitySearchBinding;
import com.j1j2.pifalao.databinding.ActivityServicesBinding;
import com.j1j2.pifalao.feature.search.di.SearchModule;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-12.
 */
@RequireBundler
public class SearchActivity extends BaseActivity implements View.OnClickListener, SearchAdapter.OnKeyClickListener {

    ActivitySearchBinding binding;

    @Arg
    Module module;

    @Inject
    SearchViewModel searchViewModel;

    @Inject
    UserRelativePreference userRelativePreference;
    List<String> historys;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.setOnClick(this);
        binding.searchHotList.setLayoutManager(new GridLayoutManager(this, 4));
        binding.searchHotList.addItemDecoration(new CustomGridItemDecoration(AutoUtils.getPercentWidthSize(20), AutoUtils.getPercentWidthSize(20), 4));

        binding.searchHistoryList.setLayoutManager(new GridLayoutManager(this, 4));
        binding.searchHistoryList.addItemDecoration(new CustomGridItemDecoration(AutoUtils.getPercentWidthSize(20), AutoUtils.getPercentWidthSize(20), 4));

    }

    @Override
    protected void initViews() {
        searchViewModel.queryHotKey(module.getWareHouseModuleId());

        historys = userRelativePreference.getHistoryKey(new ArrayList<String>());
        setHistoryKey(new SearchAdapter(historys));
    }

    public void setHotKeyAdapter(SearchAdapter searchAdapter) {
        binding.searchHotList.setAdapter(searchAdapter);
        searchAdapter.setOnKeyClickListener(this);
    }

    public void setHistoryKey(SearchAdapter searchAdapter) {
        binding.searchHistoryList.setAdapter(searchAdapter);
        searchAdapter.setOnKeyClickListener(this);
    }


    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getAppComponent().plus(new SearchModule(this)).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.searchBtn) {
            String newHistoryKey = binding.searchview.getText().toString();
            if (TextUtils.isEmpty(newHistoryKey)) {
                Toast.makeText(this, "关键字不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            navigate.navigateToProductsActivityFromSearch(this, null, false, newHistoryKey, module);
            changeHistoryKey(newHistoryKey);
        }

    }

    @Override
    public void onKeyClickListener(View view, String key, int position) {
        navigate.navigateToProductsActivityFromSearch(this, null, false, key, module);
        changeHistoryKey(key);
    }

    private void changeHistoryKey(String key) {
        if (historys.size() < 20)
            historys.add(key);
        else {
            historys.remove(0);
            historys.add(19, key);
        }
        userRelativePreference.setHistoryKey(historys);
        EventBus.getDefault().post(new HistoryKeyChangeEvent());
    }

    @Subscribe
    public void onHistoryKeyChangeEvent(HistoryKeyChangeEvent event) {
        historys = userRelativePreference.getHistoryKey(new ArrayList<String>());
        setHistoryKey(new SearchAdapter(historys));
    }
}
