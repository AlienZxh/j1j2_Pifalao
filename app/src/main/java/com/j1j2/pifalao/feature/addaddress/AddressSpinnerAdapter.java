package com.j1j2.pifalao.feature.addaddress;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.j1j2.pifalao.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by alienzxh on 16-4-3.
 */
public class AddressSpinnerAdapter extends BaseAdapter {

    private List<String> strings;

    public AddressSpinnerAdapter(List<String> strings) {
        this.strings = strings;
    }

    public int getPosition(String str) {
        int position = 0;
        if (strings != null)
            for (int i = 0; i < strings.size(); i++) {
                if (strings.get(i).contains(str)) {
                    position = i;
                    break;
                }
            }
        return position;
    }

    @Override
    public int getCount() {
        return null == strings ? 0 : strings.size();
    }

    @Override
    public Object getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_addressspinner, parent, false);
            holder.textView = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
            //对于listview，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(strings.get(position));
        return convertView;
    }

    public final class ViewHolder {
        public TextView textView;
    }
}
