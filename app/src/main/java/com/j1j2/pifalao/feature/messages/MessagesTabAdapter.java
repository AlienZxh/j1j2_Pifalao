package com.j1j2.pifalao.feature.messages;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by alienzxh on 16-3-24.
 */
public class MessagesTabAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;


    public MessagesTabAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return null == fragmentList ? 0 : fragmentList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "订单消息";
            case 1:
                return "系统消息";
        }

        return super.getPageTitle(position);
    }
}
