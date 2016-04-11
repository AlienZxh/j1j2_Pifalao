package com.j1j2.pifalao.feature.coupons;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.j1j2.data.model.Coupon;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import in.workarounds.bundler.Bundler;

/**
 * Created by alienzxh on 16-3-23.
 */
public class CouponsTabAdapter extends FragmentPagerAdapter {


    private List<Fragment> fragmentList = new ArrayList<>();
    private List<Coupon> coupons1 = new ArrayList<>();
    private List<Coupon> coupons2 = new ArrayList<>();
    private List<Coupon> coupons3 = new ArrayList<>();

    public CouponsTabAdapter(FragmentManager fm, List<Coupon> couponList) {
        super(fm);
        for (Coupon coupon : couponList) {
            if (coupon.getState() == 2) {
                coupons2.add(coupon);
            } else if (coupon.getState() == 1 && !coupon.isExpired()) {
                coupons1.add(coupon);
            } else if (coupon.isExpired() || coupon.getState() == 3) {
                coupons3.add(coupon);
            }
        }

        fragmentList.add(Bundler.couponsFragment(coupons1).create());
        fragmentList.add(Bundler.couponsFragment(coupons2).create());
        fragmentList.add(Bundler.couponsFragment(coupons3).create());
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "未使用(" + coupons1.size() + ")";
            case 1:
                return "已使用(" + coupons2.size() + ")";
            case 2:
                return "已过期(" + coupons3.size() + ")";
        }
        return super.getPageTitle(position);
    }
}
