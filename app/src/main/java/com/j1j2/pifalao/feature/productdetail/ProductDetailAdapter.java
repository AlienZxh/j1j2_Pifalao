package com.j1j2.pifalao.feature.productdetail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by alienzxh on 16-3-18.
 */
public class ProductDetailAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public ProductDetailAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragmentList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return null == fragmentList ? 0 : fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "图文详情";
            case 1:
                return "产品参数";
            case 2:
                return "成交记录";
            default:
                return super.getPageTitle(position);

        }

    }
}
