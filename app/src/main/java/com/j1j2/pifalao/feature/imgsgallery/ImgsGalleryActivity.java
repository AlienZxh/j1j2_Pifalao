package com.j1j2.pifalao.feature.imgsgallery;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.j1j2.common.view.touchgallery.GalleryWidget.UrlPagerAdapter;
import com.j1j2.data.model.ImgUrl;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityImgsgalleryBinding;

import java.util.ArrayList;
import java.util.List;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.ParcelListSerializer;
import in.workarounds.bundler.annotations.RequireBundler;
import in.workarounds.bundler.annotations.Required;

/**
 * Created by alienzxh on 16-9-24.
 */
@RequireBundler
public class ImgsGalleryActivity extends BaseActivity {
    ActivityImgsgalleryBinding binding;


    @Arg(serializer = ParcelListSerializer.class)
    List<ImgUrl> imgUrls;

    @Arg
    @Required(false)
    int index;

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_imgsgallery);
    }

    @Override
    protected void initViews() {

        binding.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        List<String> strUrls = new ArrayList<>();
        for (ImgUrl imgUrl : imgUrls) {
            strUrls.add(imgUrl.getUrl());
        }

        UrlPagerAdapter pagerAdapter = new UrlPagerAdapter(this, strUrls);
        binding.gallery.setAdapter(pagerAdapter);
        binding.gallery.setCurrentItem(index);
    }
}
