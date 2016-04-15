package com.j1j2.pifalao.feature.home.viphome;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.j1j2.pifalao.databinding.ActivityViphomeBinding;
import com.j1j2.pifalao.feature.home.viphome.di.VipHomeModule;
import com.litesuits.common.assist.Check;
import com.litesuits.common.assist.Network;

import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;
import okhttp3.Cookie;

/**
 * Created by alienzxh on 16-4-13.
 */
@RequireBundler
public class VipHomeActivity extends BaseActivity implements View.OnClickListener {

    ActivityViphomeBinding binding;

    @Inject
    UserLoginPreference userLoginPreference;
    @Inject
    Gson gson;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_viphome);
        binding.backBtn.setOnClickListener(this);
    }

    @Override
    protected void initViews() {

        List<Cookie> cookies = gson.<List<Cookie>>fromJson(userLoginPreference
                .getLoginCookie(null), new TypeToken<List<Cookie>>() {
        }.getType());

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(BuildConfig.IMAGE_URL + "/VIPPrivilege/Index", cookies.get(0).toString());

        WebSettings webSettings = binding.webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        if (Network.isAvailable(this)) {
//            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        } else {
//            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
//        }

        binding.webview.loadUrl(BuildConfig.IMAGE_URL + "/VIPPrivilege/Index");
        binding.webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                binding.webview.loadUrl(url);
                return true;
            }



        });
        binding.webview.setWebChromeClient(new WebChromeClient() {

        });

    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getUserComponent().plus(new VipHomeModule()).inject(this);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (binding.webview.canGoBack()) {
            binding.webview.goBack();
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}