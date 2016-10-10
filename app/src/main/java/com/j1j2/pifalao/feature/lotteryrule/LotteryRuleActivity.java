package com.j1j2.pifalao.feature.lotteryrule;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.j1j2.common.util.Network;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityLotteryRuleBinding;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-9-23.
 */
@RequireBundler
public class LotteryRuleActivity extends BaseActivity implements View.OnClickListener{

    ActivityLotteryRuleBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lottery_rule);
    }

    @Override
    protected void initViews() {
        binding.backBtn.setOnClickListener(this);

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

        binding.webview.loadUrl(BuildConfig.IMAGE_URL + "/ActivityDescription/Index");

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

    }
}
