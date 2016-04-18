package com.j1j2.pifalao.feature.productdetail.unit;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
import com.j1j2.data.model.ProductUnit;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.databinding.FragmentProductdetailUnitBinding;
import com.j1j2.pifalao.feature.productdetail.ProductDetailActivity;

import java.util.List;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.ParcelListSerializer;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-21.
 */
@RequireBundler
public class ProductDetailUnitFragment extends BaseFragment implements ProductDetailUnitAdapter.OnUnitItemClickListener {

    public interface ProductDetailUnitFragmentListener {
        void setSelectUnit(ProductUnit unit);
    }

    private ProductDetailUnitFragmentListener productDetailUnitFragmentListener;

    FragmentProductdetailUnitBinding binding;

    @Arg(serializer = ParcelListSerializer.class)
    List<ProductUnit> productUnits;

    @Arg
    String baseUnit;

    @Arg
    int moduleType;

    SingleSelector singleSelector;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        productDetailUnitFragmentListener = (ProductDetailActivity) activity;
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_productdetail_unit, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        singleSelector = new SingleSelector();
        binding.unitList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        ProductDetailUnitAdapter productDetailUnitAdapter = new ProductDetailUnitAdapter(productUnits, singleSelector, baseUnit, moduleType);
        binding.unitList.setAdapter(productDetailUnitAdapter);
        productDetailUnitAdapter.setOnUnitItemClickListener(this);
        if (productDetailUnitFragmentListener != null)
            productDetailUnitFragmentListener.setSelectUnit(productUnits.get(0));
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
    }

    @Override
    public void OnUnitItemClickListener(View view, ProductUnit unit, int position) {

        if (productDetailUnitFragmentListener != null)
            productDetailUnitFragmentListener.setSelectUnit(unit);
    }



    public int getQuantity() {
        return binding.quantityview.getQuantity();
    }
}
