package com.j1j2.pifalao.feature.home.deliveryhome;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.j1j2.data.model.Coupon;

import java.util.ArrayList;
import java.util.List;

import in.workarounds.bundler.Bundler;

/**
 * Created by alienzxh on 16-3-30.
 */
public class DeliveryHomeTabAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public DeliveryHomeTabAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "商品";
            case 1:
                return "商家";
        }
        return super.getPageTitle(position);
    }
}
