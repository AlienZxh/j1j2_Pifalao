package com.j1j2.pifalao.feature.orderdetail.orderdetailparams;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.model.OrderDetail;
import com.j1j2.data.model.OrderSimple;
import com.j1j2.data.model.Shop;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.databinding.FragmentOrderdetailParamsBinding;
import com.j1j2.pifalao.feature.orderdetail.OrderDetailActivity;
import com.j1j2.pifalao.feature.orderdetail.OrderProductsAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-4-21.
 */
@RequireBundler
public class OrderDetailParamsFragment extends BaseFragment implements View.OnClickListener, OrderProductsAdapter.OnItemClickListener {

    @Override
    protected String getFragmentName() {
        return "OrderDetailParamsFragment";
    }

    public interface OrderDetailParamsFragmentListener {

        void navigateToProductDetailActivity(int mainId);

        void navigateToCatServicePoint();


    }

    private OrderDetailParamsFragmentListener listener;


    FragmentOrderdetailParamsBinding binding;

    public ObservableField<OrderDetail> orderDetailObservableField = new ObservableField<>();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OrderDetailActivity) activity;
    }


    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orderdetail_params, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.setOrderDetail(orderDetailObservableField);
        binding.setOnClick(this);
        binding.orderProductList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.orderProductList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).colorResId(R.color.colorGrayF0F0F0)
                .margin(AutoUtils.getPercentWidthSize(20), 0).build());
    }

    public void initParams(OrderDetail orderDetail) {
        switch (orderDetail.getOrderBaseInfo().getOrderType()) {
            case Constant.ModuleType.DELIVERY:
                binding.orderIcon.setText(getText(R.string.icon_delivery));
                binding.orderIcon.setTextColor(Constant.moduleColors.get(orderDetail.getOrderBaseInfo().getOrderType()));
                break;
            case Constant.ModuleType.VEGETABLE:
                binding.orderIcon.setText(getText(R.string.icon_vegetable));
                binding.orderIcon.setTextColor(Constant.moduleColors.get(orderDetail.getOrderBaseInfo().getOrderType()));
                break;
            case Constant.ModuleType.SHOPSERVICE:
                binding.orderIcon.setText(getText(R.string.icon_shopservice));
                binding.orderIcon.setTextColor(Constant.moduleColors.get(orderDetail.getOrderBaseInfo().getOrderType()));
                break;
        }
        //___________________________________________________________________________

        orderDetailObservableField.set(orderDetail);

        OrderProductsAdapter orderProductListAdapter = new OrderProductsAdapter(orderDetail.getOrderProductsInfo());
        binding.orderProductList.setAdapter(orderProductListAdapter);
        orderProductListAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClickListener(View v, OrderDetail.OrderProductDetail orderProductDetail, int position) {
        listener.navigateToProductDetailActivity(orderProductDetail.getProductId());
    }

    @Override
    public void onClick(View v) {
        if (v == binding.catservicepoint) {
            listener.navigateToCatServicePoint();
        }

    }
}
