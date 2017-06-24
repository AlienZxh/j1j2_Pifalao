package com.j1j2.pifalao.feature.show;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityShowBinding;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-4-24.
 */
@RequireBundler
public class ShowActivity extends BaseActivity implements View.OnClickListener {

    public final static int SERVICEPOINT = 0;
    public final static int STORE = 1;

    ActivityShowBinding binding;

    @Arg
    int activityType;

    String[] servicepoints = {
            "/ResoruceFiles/PifalaoIntroduce/servicepoint_show_activity_img1.jpg",
            "/ResoruceFiles/PifalaoIntroduce/servicepoint_show_activity_img2.jpg",
            "/ResoruceFiles/PifalaoIntroduce/servicepoint_show_activity_img3.jpg",
            "/ResoruceFiles/PifalaoIntroduce/servicepoint_show_activity_img4.jpg",
            "/ResoruceFiles/PifalaoIntroduce/servicepoint_show_activity_img5.jpg",
            "/ResoruceFiles/PifalaoIntroduce/servicepoint_show_activity_img6.jpg",
            "/ResoruceFiles/PifalaoIntroduce/servicepoint_show_activity_img7.jpg",
            "/ResoruceFiles/PifalaoIntroduce/servicepoint_show_activity_img8.jpg",
            "/ResoruceFiles/PifalaoIntroduce/servicepoint_show_activity_img9.jpg",
            "/ResoruceFiles/PifalaoIntroduce/servicepoint_show_activity_img10.jpg"};
    String[] stores = {"/ResoruceFiles/PifalaoIntroduce/China.png",
            "/ResoruceFiles/PifalaoIntroduce/guide_activity_img_1.jpg",
            "/ResoruceFiles/PifalaoIntroduce/guide_activity_img_2.jpg",
            "/ResoruceFiles/PifalaoIntroduce/guide_activity_img_3.jpg",
            "/ResoruceFiles/PifalaoIntroduce/guide_activity_img_4.jpg",
            "/ResoruceFiles/PifalaoIntroduce/guide_activity_img_5.jpg",
            "/ResoruceFiles/PifalaoIntroduce/guide_activity_img_6.jpg",
            "/ResoruceFiles/PifalaoIntroduce/guide_activity_img_7.jpg"};

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show);
        binding.backBtn.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        binding.title.setText(activityType == SERVICEPOINT ? "批发佬服务点展示" : "批发佬仓库展示");

        binding.showList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.showList.setAdapter(new ShowAdapter(activityType == SERVICEPOINT ? servicepoints : stores, activityType));
        binding.showList.setClipToPadding(false);
        binding.showList.setPadding(AutoUtils.getPercentHeightSize(20),
                AutoUtils.getPercentHeightSize(20),
                AutoUtils.getPercentHeightSize(20),
                AutoUtils.getPercentHeightSize(20));
        binding.showList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.colorTransparent)
                .size(AutoUtils.getPercentHeightSize(20))
                .build());
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
