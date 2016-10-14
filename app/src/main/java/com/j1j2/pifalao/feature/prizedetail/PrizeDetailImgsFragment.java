package com.j1j2.pifalao.feature.prizedetail;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.j1j2.data.model.ActivityProductImg;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.LazyFragment;
import com.j1j2.pifalao.databinding.FragmentPrizedetailImgsBinding;
import com.j1j2.pifalao.databinding.FragmentProductdetailImgBinding;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by alienzxh on 16-9-23.
 */

public class PrizeDetailImgsFragment extends LazyFragment {

    public interface PrizeDetailImgsFragmentListener {
        List<ActivityProductImg> getImgList();

        String getIntroduce();
    }

    PrizeDetailImgsFragmentListener listener;

    FragmentPrizedetailImgsBinding binding;

    WebView webView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (PrizeDetailImgsFragmentListener) activity;
    }

    @Override
    protected String getFragmentName() {
        return "PrizeDetailImgsFragment";
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prizedetail_imgs, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        webView = new WebView(getContext());
        webView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        binding.webviewContainer.addView(webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setUseWideViewPort(false);
        if (null != listener.getIntroduce())
            webView.loadDataWithBaseURL(BuildConfig.IMAGE_URL,
                    listener.getIntroduce()
                            .replaceAll("img", "img width=100%"), "text/html",
                    "utf-8", null);
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        binding.webviewContainer.removeAllViews();
        webView.destroy();
    }
}
