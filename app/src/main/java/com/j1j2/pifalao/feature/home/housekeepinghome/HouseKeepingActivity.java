package com.j1j2.pifalao.feature.home.housekeepinghome;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

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
    WebView webView;
    
    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_housekeeping);
        binding.backBtn.setOnClickListener(this);
        binding.individualBtn.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        webView = new WebView(this);
        webView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        binding.webviewContainer.addView(webView);

        WebSettings webSettings = webView.getSettings();
        if (Network.isAvailable(this)) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl(BuildConfig.IMAGE_URL + "/HouseManageService/Housekeeping.html");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
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
    protected void onDestroy() {
        super.onDestroy();
        binding.webviewContainer.removeAllViews();
        webView.destroy();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (webView.canGoBack())
            webView.goBack();
        else
            finish();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.individualBtn) {
            navigate.navigateToIndividualCenter(this, null, false);
        }
    }
}
