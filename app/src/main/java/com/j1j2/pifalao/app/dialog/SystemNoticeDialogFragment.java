package com.j1j2.pifalao.app.dialog;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
import com.zhy.autolayout.utils.AutoUtils;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-10-19.
 */
@RequireBundler
public class SystemNoticeDialogFragment extends BaseDialogFragment {

    View rootView;
    WebView webView;
    FrameLayout webviewContainer;

    @Arg
    String notifyText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundler.inject(this);

        rootView = getActivity().getLayoutInflater().inflate(
                R.layout.view_systemnotice, null, false);

        webviewContainer = (FrameLayout) rootView.findViewById(R.id.webviewContainer);

        if (webView == null) {
            webView = new WebView(getContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(AutoUtils.getPercentWidthSize(20), 0, AutoUtils.getPercentWidthSize(20), 0);
            webView.setLayoutParams(params);
            webviewContainer.addView(webView);
        }

        WebSettings webSettings = webView.getSettings();
        webSettings
                .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setUseWideViewPort(false);
        webView.loadDataWithBaseURL(BuildConfig.IMAGE_URL, notifyText, "text/html", "utf-8", null);

        setCancelable(true);
    }

    @Override
    protected AlertDialog.Builder getDialogBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("通知");
        builder.setPositiveButton("知道了", null);
        builder.setView(rootView);
        return builder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webviewContainer != null && webView != null) {
            webviewContainer.removeAllViews();
            webView.destroy();
        }
    }
}
