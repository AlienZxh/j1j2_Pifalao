package com.j1j2.pifalao.feature.home.viphome;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityViphomeBinding;
import com.j1j2.pifalao.feature.home.viphome.di.VipHomeModule;
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
    UserRelativePreference userRelativePreference;
    @Inject
    Gson gson;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_viphome);
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
        cookieManager.setCookie(BuildConfig.IMAGE_URL + "/VIPPrivilege/Index", cookies.get(0).toString());

        WebSettings webSettings = binding.webview.getSettings();
        if (Network.isAvailable(this)) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }

        binding.webview.loadUrl(BuildConfig.IMAGE_URL + "/VIPPrivilege/Index");
        binding.webview.registerHandler("goToUserCoupon", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                navigate.navigateToCoupons(VipHomeActivity.this, null, false, userRelativePreference.getSelectedModule(null), Constant.CouponType.COUPON_NORMAL);
                function.onCallBack("submitFromWeb exe, response data from Java");
            }
        });
        binding.webview.registerHandler("goBackToFrame", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                onBackPressed();
                function.onCallBack("submitFromWeb exe, response data from Java");
            }
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
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.individualBtn)
            navigate.navigateToIndividualCenter(this, null, false);
    }
}
