package com.j1j2.pifalao.feature.home.viphome;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j1j2.common.util.Network;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityViphomeBinding;
import com.j1j2.pifalao.feature.home.viphome.di.VipHomeModule;

import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import okhttp3.Cookie;

/**
 * Created by alienzxh on 16-4-13.
 */
@RequireBundler
public class VipHomeActivity extends BaseActivity implements View.OnClickListener {

    public static final int VIPHOME = 1;
    public static final int POINT = 2;

    ActivityViphomeBinding binding;

    @Inject
    UserLoginPreference userLoginPreference;
    @Inject
    UserRelativePreference userRelativePreference;
    @Inject
    Gson gson;
    @Inject
    UnReadInfoManager unReadInfoManager;

    @Arg
    int activityType;

    String privilegeUrl = BuildConfig.IMAGE_URL + "/VIPPrivilege/VIPPrivilege";
    String pointUrl = BuildConfig.IMAGE_URL + "/VIPPrivilege/QueryUserPointDetailsLayout";

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_viphome);
        binding.setUnReadInfoManager(unReadInfoManager);
        binding.backBtn.setOnClickListener(this);
        binding.individualBtn.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

        List<Cookie> cookies = gson.<List<Cookie>>fromJson(userLoginPreference
                .getLoginCookie(null), new TypeToken<List<Cookie>>() {
        }.getType());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(privilegeUrl, cookies.get(0).toString());
        cookieManager.setCookie(pointUrl, cookies.get(0).toString());


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

        if (activityType == VipHomeActivity.VIPHOME)
            binding.webview.loadUrl(privilegeUrl);
        if (activityType == VipHomeActivity.POINT)
            binding.webview.loadUrl(pointUrl);

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
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new VipHomeModule()).inject(this);
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
}
