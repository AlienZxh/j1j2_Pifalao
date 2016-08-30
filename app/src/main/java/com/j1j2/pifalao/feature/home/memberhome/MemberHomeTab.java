package com.j1j2.pifalao.feature.home.memberhome;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j1j2.common.view.smarttablayout.SmartTabLayout;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.databinding.ViewMaintabBinding;

/**
 * Created by alienzxh on 16-8-23.
 */
public class MemberHomeTab implements SmartTabLayout.TabProvider {

    private Context context;
    private String[] icons;
    private String[] selectIcons;
    private String[] texts;


    public MemberHomeTab(Context context, String[] icons, String[] selectIcons, String[] texts) {
        this.context = context;
        this.icons = icons;
        this.selectIcons = selectIcons;
        this.texts = texts;
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        ViewGroup tab;
        tab = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.view_membertab_normal, container, false);
        TextView icon = (TextView) tab.findViewById(R.id.icon);
        TextView selsectIcon = (TextView) tab.findViewById(R.id.selsectIcon);
        TextView text = (TextView) tab.findViewById(R.id.text);

        icon.setText(icons[position]);
        selsectIcon.setText(selectIcons[position]);
        text.setText(texts[position]);

        return tab;
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
