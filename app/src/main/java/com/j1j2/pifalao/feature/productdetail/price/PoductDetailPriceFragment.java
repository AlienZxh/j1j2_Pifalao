package com.j1j2.pifalao.feature.productdetail.price;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.view.recyclerviewchoicemode.SingleSelector;
import com.j1j2.data.model.ProductUnit;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.databinding.FragmentProductdetailPriceBinding;
import com.j1j2.pifalao.feature.productdetail.ProductDetailActivity;

import java.util.List;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.ParcelListSerializer;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-4-17.
 */
@RequireBundler
public class PoductDetailPriceFragment extends BaseFragment {

    @Override
    protected String getFragmentName() {
        return "PoductDetailPriceFragment";
    }

    public interface PoductDetailPriceFragmentListener {
        void setSelectUnit(ProductUnit unit);
    }

    private PoductDetailPriceFragmentListener poductDetailPriceFragmentListener;

    FragmentProductdetailPriceBinding binding;

    @Arg(serializer = ParcelListSerializer.class)
    List<ProductUnit> productUnits;

    @Arg
    String baseUnit;

    @Arg
    int moduleType;

    ObservableField<ProductUnit> productUnitObservableField = new ObservableField<>();

    SingleSelector singleSelector;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        poductDetailPriceFragmentListener = (ProductDetailActivity) activity;
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_productdetail_price, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.setServiceType(moduleType);
        binding.setPrice(productUnitObservableField);

    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
    }



    public void setSelectUnit(ProductUnit unit) {
        productUnitObservableField.set(unit);
    }


}
