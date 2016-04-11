package com.j1j2.pifalao.feature.collects;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.j1j2.common.view.recyclerviewchoicemode.MultiSelector;
import com.j1j2.data.model.CollectedProduct;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityCollectsBinding;
import com.j1j2.pifalao.feature.collects.di.CollectsModule;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-24.
 */
@RequireBundler
public class CollectsActivity extends BaseActivity implements View.OnClickListener {

    ActivityCollectsBinding binding;

    @Inject
    CollectsViewModel collectsViewModel;

    public ObservableBoolean isModifyMode = new ObservableBoolean(false);

    private MultiSelector multiSelector = new MultiSelector();

    CollectsAdapter collectsAdapter;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collects);
        binding.setCollectsViewModel(collectsViewModel);
    }

    @Override
    protected void initViews() {
        binding.productList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.productList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .drawable(R.drawable.item_products_divider)
                .size(AutoUtils.getPercentHeightSize(8))
                .build());
        collectsViewModel.queryCollects();
    }

    public void setAdapter(List<CollectedProduct> collectedProducts) {
        collectsAdapter = new CollectsAdapter(collectedProducts, multiSelector, isModifyMode);
        binding.productList.setAdapter(collectsAdapter);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getUserComponent().plus(new CollectsModule(this)).inject(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.modifyBtn) {
            if (isModifyMode.get()) {
                if (multiSelector.getSelectedPositions().size() > 0) {
                    List<Integer> mainIds = new ArrayList<>();
                    for (Integer integer : multiSelector.getSelectedPositions()) {
                        if (collectsAdapter.getCollectedProducts().get(integer) != null) {
                            mainIds.add(collectsAdapter.getCollectedProducts().get(integer).getMainId());
                        }
                    }
                    collectsViewModel.removeItemFromUserFavorites(mainIds);
                    multiSelector.clearSelections();
                }

            } else {

            }
            isModifyMode.set(!isModifyMode.get());
        }
    }
}
