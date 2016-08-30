package com.j1j2.pifalao.feature.home.housekeepinghome;

import android.databinding.DataBindingUtil;
import android.view.View;
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
import com.j1j2.pifalao.databinding.ActivityHousekeepingBinding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-5-9.
 */
@RequireBundler
public class HouseKeepingActivity extends BaseActivity implements View.OnClickListener {
    ActivityHousekeepingBinding binding;
    UnReadInfoManager unReadInfoManager;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_housekeeping);
        binding.backBtn.setOnClickListener(this);
        binding.individualBtn.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
//        WebSettings webSettings = binding.webview.getSettings();
//        if (Network.isAvailable(this)) {
//            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        } else {
//            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
//        }
        binding.webview.loadUrl(BuildConfig.IMAGE_URL + "/HouseManageService/Housekeeping.html");
        binding.webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                binding.webview.loadUrl(url);
                return true;
            }
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
    public void onLogStateChangeEvent(LogStateEvent event) {
        if (event.isLogin()) {
            unReadInfoManager = MainAplication.get(this).getUserComponent().unReadInfoManager();
            binding.setUnReadInfoManager(unReadInfoManager);
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (binding.webview.canGoBack())
            binding.webview.goBack();
        else
            finish();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.individualBtn) {
            if (MainAplication.get(this).isLogin())
                navigate.navigateToIndividualCenter(this, null, false);
            else
                navigate.navigateToLogin(this, null, false);
        }
    }
}
