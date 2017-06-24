package com.j1j2.pifalao.feature.home.morehome;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.model.Shop;
import com.j1j2.data.model.ShopSubscribeService;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.Navigate;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.FragmentMorehomeBinding;
import com.j1j2.pifalao.feature.home.morehome.di.MoreHomeModule;
import com.j1j2.pifalao.feature.home.viphome.VipHomeActivity;
import com.j1j2.pifalao.feature.main.MainActivity;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-8-24.
 */
public class MoreHomeFragment extends BaseFragment implements MoreHomeAdapter.OnItemClickListener {


    FragmentMorehomeBinding binding;

    @Inject
    Navigate navigate;
    @Inject
    UserRelativePreference userRelativePreference;
    @Inject
    ServicePointApi servicePointApi;

    Shop shop;

    MoreHomeAdapter moreHomeAdapter;
    GridLayoutManager gridLayoutManager;

    @Override
    protected String getFragmentName() {
        return "MoreHomeFragment";
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(getContext()).getAppComponent().plus(new MoreHomeModule()).inject(this);
        shop = userRelativePreference.getSelectedServicePoint(null);
    }


    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_morehome, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {


        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        binding.moreList.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return moreHomeAdapter.isHeader(position) ? gridLayoutManager.getSpanCount() : 1;
            }
        });
        if (shop != null)
            queryServicePointServiceModules();
    }

    public void queryServicePointServiceModules() {

        servicePointApi.queryServicePointServiceModules(shop.getShopId())
                .compose(this.<WebReturn<List<ShopSubscribeService>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<ShopSubscribeService>>() {
                    @Override
                    public void onWebReturnSucess(List<ShopSubscribeService> shopSubscribeServices) {
                        moreHomeAdapter = new MoreHomeAdapter(shopSubscribeServices);
                        binding.moreList.setAdapter(moreHomeAdapter);
                        moreHomeAdapter.setOnItemClickListener(MoreHomeFragment.this);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });

    }


    @Override
    public void onItemClickListener(View view, ShopSubscribeService shopSubscribeService, int position) {
        if (shopSubscribeService.isSubscribed()) {
            if (shopSubscribeService.getServiceType() == Constant.ModuleType.DELIVERY) {
                navigate.navigateToDeliveryHomeActivity(getActivity(), null, false, userRelativePreference.getSelectedServicePoint(null), shopSubscribeService);
                userRelativePreference.setSelectedModule(shopSubscribeService);
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.SHOPSERVICE) {
                if (MainAplication.get(getContext()).isLogin()) {
                    if (MainAplication.get(getContext()).getUserComponent().user().getRoleId() == 10002) {
                        navigate.navigateToMainActivity(getActivity(), null, false, shopSubscribeService, MainActivity.STORESTYLE);
                        userRelativePreference.setSelectedModule(shopSubscribeService);
                    } else {
                        navigate.navigateToModulePermissionDeniedActivity(getActivity(), null, false, shopSubscribeService);
                    }
                } else {
                    navigate.navigateToLogin(getActivity(), null, false);
                }
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.VEGETABLE) {
                navigate.navigateToMainActivity(getActivity(), null, false, shopSubscribeService, MainActivity.VEGETABLE);
                userRelativePreference.setSelectedModule(shopSubscribeService);
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.VIP) {
                if (MainAplication.get(getActivity()).isLogin()) {
                    navigate.navigateToVipHome(getActivity(), null, false, VipHomeActivity.VIPHOME);
                    userRelativePreference.setSelectedModule(shopSubscribeService);
                } else {
                    navigate.navigateToLogin(getActivity(), null, false);
                }
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.HOUSEKEEPING) {
                navigate.navigateToHouseKeeping(getActivity(), null, false);
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.MOBILE
                    ||shopSubscribeService.getServiceType() == Constant.ModuleType.WATERBILL
                    ||shopSubscribeService.getServiceType() == Constant.ModuleType.ELECTRICBILL
                    ||shopSubscribeService.getServiceType() == Constant.ModuleType.GASBILL)
                navigate.navigateToOfflineModuleHome(getActivity(), null, false, shopSubscribeService, userRelativePreference.getSelectedServicePoint(null));
            else if (shopSubscribeService.getServiceType() == Constant.ModuleType.MEMBER) {
                navigate.navigateToMemberHomeActivity(getActivity(), null, false);
                userRelativePreference.setSelectedModule(shopSubscribeService);
            } else if (shopSubscribeService.getServiceType() == Constant.ModuleType.SPECIALOFFER) {
                navigate.navigateToSpecialOfferActivity(getActivity(), null, false);
                userRelativePreference.setSelectedModule(shopSubscribeService);
            }
        } else {
            navigate.navigateToUnsubscribeModule(getActivity(), null, false, shopSubscribeService);
        }
    }
}
