package com.j1j2.pifalao.feature.home.morehome;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.model.Module;
import com.j1j2.data.model.ServicePoint;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.Navigate;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.app.base.DefaultSubscriber;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.FragmentMorehomeBinding;
import com.j1j2.pifalao.feature.home.memberhome.MemberHomeActivity;
import com.j1j2.pifalao.feature.home.morehome.di.MoreHomeModule;
import com.j1j2.pifalao.feature.main.MainActivity;
import com.trello.rxlifecycle.FragmentLifecycleProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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

    ServicePoint servicePoint;

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
        servicePoint = userRelativePreference.getSelectedServicePoint(null);
        if (servicePoint != null)
            queryServicePointServiceModules();
    }

    public void queryServicePointServiceModules() {

        servicePointApi.queryServicePointServiceModules(servicePoint.getServicePointId())
                .compose(this.<WebReturn<List<Module>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<Module>>() {
                    @Override
                    public void onWebReturnSucess(List<Module> modules) {
                        moreHomeAdapter = new MoreHomeAdapter(modules);
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
    public void onItemClickListener(View view, Module module, int position) {
        if (module.isSubscribed()) {
            if (module.getModuleType() == Constant.ModuleType.DELIVERY && module.isSubscribed()) {
                navigate.navigateToDeliveryHomeActivity(getActivity(), ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, userRelativePreference.getSelectedServicePoint(null), module);
                userRelativePreference.setSelectedModule(module);
            } else if (module.getModuleType() == Constant.ModuleType.SHOPSERVICE && module.isSubscribed()) {
                navigate.navigateToMainActivity(getActivity(), ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, module, MainActivity.STORESTYLE);
                userRelativePreference.setSelectedModule(module);
            } else if (module.getModuleType() == Constant.ModuleType.VEGETABLE && module.isSubscribed()) {
                navigate.navigateToMainActivity(getActivity(), ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, module, MainActivity.VEGETABLE);
                userRelativePreference.setSelectedModule(module);
            } else if (module.getModuleType() == Constant.ModuleType.VIP) {
                if (MainAplication.get(getActivity()).isLogin()) {
                    navigate.navigateToVipHome(getActivity(), ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false);
                    userRelativePreference.setSelectedModule(module);
                } else {
                    navigate.navigateToLogin(getActivity(), null, false);
                }
            } else if (module.getModuleType() == Constant.ModuleType.HOUSEKEEPING) {
                navigate.navigateToHouseKeeping(getActivity(), ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false);
            } else if (module.getModuleType() == Constant.ModuleType.MOBILE)
                navigate.navigateToOfflineModuleHome(getActivity(), ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false, module, userRelativePreference.getSelectedServicePoint(null));

        } else {
            navigate.navigateToUnsubscribeModule(getActivity(), ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false);
        }
    }
}
