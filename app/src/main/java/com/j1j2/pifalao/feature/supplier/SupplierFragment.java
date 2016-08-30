package com.j1j2.pifalao.feature.supplier;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.j1j2.data.model.City;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.databinding.FragmentSupplierBinding;
import com.j1j2.common.util.Network;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-23.
 */
@RequireBundler
public class SupplierFragment extends BaseFragment {

    @Override
    protected String getFragmentName() {
        return "SupplierFragment";
    }

    FragmentSupplierBinding binding;

    @Arg
    City city;

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_supplier, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        WebSettings webSettings = binding.webview.getSettings();
        if (Network.isAvailable(getContext())) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setUseWideViewPort(false);
        if (null != city)
            binding.webview.loadDataWithBaseURL(BuildConfig.IMAGE_URL,
                    city.getPlatformDescription()
                            .replaceAll("img", "img width=100%"), "text/html",
                    "utf-8", null);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
    }
}
