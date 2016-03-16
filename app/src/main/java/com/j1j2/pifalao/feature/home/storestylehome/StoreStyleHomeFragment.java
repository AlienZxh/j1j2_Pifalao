package com.j1j2.pifalao.feature.home.storestylehome;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ProductSort;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.databinding.FragmentStoreStyleHomeBinding;
import com.j1j2.pifalao.feature.main.MainActivity;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-16.
 */
@RequireBundler
public class StoreStyleHomeFragment extends BaseFragment implements StoreStyleHomeAdapter.OnSortItemClickListener {

    FragmentStoreStyleHomeBinding binding;

    @Inject
    ProductApi productApi;

    @Arg
    Module module;

    @Inject
    StoreStyleHomeFragmentViewModel storeStyleHomeViewModel;

    GridLayoutManager manager;

    MainActivity mainActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store_style_home, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        manager = new GridLayoutManager(getContext(), 3);
        binding.sortList.setLayoutManager(manager);
        storeStyleHomeViewModel.onCreate();
    }


    public void setListAdapter(final StoreStyleHomeAdapter storeStyleHomeAdapter) {
        binding.sortList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                outRect.set(storeStyleHomeAdapter.geItemRect(position));

            }
        });
        binding.sortList.setAdapter(storeStyleHomeAdapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return storeStyleHomeAdapter.isItemHeader(position) ? manager.getSpanCount() : 1;
            }
        });
        storeStyleHomeViewModel.getStoreStyleHomeAdapter().setOnSortItemClickListener(this);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        mainActivity.getComponent().inject(this);
    }

    @Override
    public void onSortItemClickListener(View view, ProductSort productSort, int position) {
        mainActivity.getNavigate().navigateToProductsActivity(mainActivity, null, false, productSort, module);
    }
}
