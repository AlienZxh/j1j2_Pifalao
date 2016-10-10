package com.j1j2.pifalao.feature.home.specialofferhome;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.j1j2.common.util.Network;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.LogStateEvent;
import com.j1j2.pifalao.databinding.ActivitySpecialofferBinding;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-9-20.
 */
@RequireBundler
public class SpecialOfferActivity extends BaseActivity implements View.OnClickListener {

    ActivitySpecialofferBinding binding;

    UnReadInfoManager unReadInfoManager = null;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_specialoffer);
    }

    @Override
    protected void initViews() {
//        binding.productList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        binding.productList.setPadding(AutoUtils.getPercentHeightSize(20), AutoUtils.getPercentHeightSize(20), AutoUtils.getPercentHeightSize(20), AutoUtils.getPercentHeightSize(20));
//        binding.productList.setClipToPadding(false);
//        binding.productList.addItemDecoration(new HorizontalDividerItemDecoration
//                .Builder(this)
//                .color(0x00ffffff)
//                .size(AutoUtils.getPercentHeightSize(20))
//                .build());
//        binding.productList.setAdapter(new SpecialOfferProductAdapter(Arrays.asList("", "", "", "")));

        binding.backBtn.setOnClickListener(this);
        binding.individualBtn.setOnClickListener(this);

        WebSettings webSettings = binding.webview.getSettings();
        if (Network.isAvailable(this)) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }
        webSettings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        // /VIPPrivilege/Index
        binding.webview.loadUrl(BuildConfig.IMAGE_URL + "/WeeklySpecials/Index");
        binding.webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                binding.webview.loadUrl(url);
                binding.progress.setProgress(0);
                binding.progress.setVisibility(View.VISIBLE);
                return false;
            }
        });
        binding.webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                binding.progress.setProgress(newProgress);
                if (newProgress == 100) {
                    binding.progress.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (binding.webview.canGoBack()) {
            binding.webview.goBack();
        } else {
            finish();
        }
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
