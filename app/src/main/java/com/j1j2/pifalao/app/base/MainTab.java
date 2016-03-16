package com.j1j2.pifalao.app.base;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.databinding.ViewMaintabBinding;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

/**
 * Created by alienzxh on 16-3-15.
 */
public class MainTab implements SmartTabLayout.TabProvider {
    private Context context;
    private String[] icons;
    private String[] selectIcons;
    private String[] texts;


    public MainTab(Context context, String[] icons, String[] selectIcons, String[] texts) {
        this.context = context;
        this.icons = icons;
        this.selectIcons = selectIcons;
        this.texts = texts;
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        ViewGroup tab = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.view_maintab, container, false);
        ViewMaintabBinding binding = ViewMaintabBinding.inflate(LayoutInflater.from(context), tab, false);
        binding.setMainTab(MainTab.this);
        binding.setPosition(position);
        return binding.getRoot();
    }

    public String[] getSelectIcons() {
        return selectIcons;
    }

    public String[] getIcons() {
        return icons;
    }

    public String[] getTexts() {
        return texts;
    }
}
