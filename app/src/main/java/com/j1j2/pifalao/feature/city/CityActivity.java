package com.j1j2.pifalao.feature.city;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.j1j2.common.view.itemdecoration.CustomGridItemDecoration;
import com.j1j2.data.http.api.ServicePointApi;
import com.j1j2.data.model.City;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.ActivityCityBinding;
import com.j1j2.pifalao.feature.city.di.CityModule;

import javax.inject.Inject;

import in.workarounds.bundler.annotations.RequireBundler;
import jp.wasabeef.recyclerview.animators.ScaleInBottomAnimator;

/**
 * Created by alienzxh on 16-3-11.
 */
@RequireBundler
public class CityActivity extends BaseActivity implements CityAdapter.OnItemClickListener {

    ActivityCityBinding binding;

    @Inject
    ServicePointApi servicePointApi;

    @Inject
    CityViewModel cityViewModel;

    @Inject
    UserRelativePreference userRelativePreference;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(CityActivity.this, R.layout.activity_city);
    }

    @Override
    protected void initViews() {
        binding.cityList.setLayoutManager(new GridLayoutManager(this, 3));
        binding.cityList.addItemDecoration(new CustomGridItemDecoration(30, 30, 3));
        binding.cityList.setItemAnimator(new ScaleInBottomAnimator());
        binding.cityList.setAdapter(cityViewModel.getCityAdapter());
        cityViewModel.getCityAdapter().setOnItemClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cityViewModel.queryServiceCity();

    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        MainAplication.get(this).getAppComponent().plus(new CityModule(this)).inject(this);
    }

    @Override
    public void onItemClickListener(View view, City city) {
        if (city.isOpenState()) {
            userRelativePreference.setSelectedCity(city);
            navigate.navigateToLocationActivity(CityActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), true, city);
        } else
            navigate.navigateToUnsubscribeCity(CityActivity.this, ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, 0, 0), false);
    }
}
