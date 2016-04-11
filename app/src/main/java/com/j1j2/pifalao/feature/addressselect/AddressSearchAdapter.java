package com.j1j2.pifalao.feature.addressselect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.j1j2.pifalao.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by alienzxh on 16-4-3.
 */
public class AddressSearchAdapter extends BaseAdapter implements Filterable {

    private List<SuggestionResult.SuggestionInfo> suggestionInfos;
    private SearchFilter searchFilter;

    public AddressSearchAdapter(List<SuggestionResult.SuggestionInfo> suggestionInfos) {
        this.suggestionInfos = suggestionInfos;
    }

    @Override
    public int getCount() {
        return null == suggestionInfos ? 0 : suggestionInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return suggestionInfos.get(position).key;
    }


    public SuggestionResult.SuggestionInfo getItemSuggestionInfo(int position) {
        return suggestionInfos.get(position);
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_addresssearch, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.district = (TextView) convertView.findViewById(R.id.district);
            convertView.setTag(holder);
            //对于listview，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(suggestionInfos.get(position).key);
        holder.district.setText(suggestionInfos.get(position).city + suggestionInfos.get(position).district);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (searchFilter == null) {
            searchFilter = new SearchFilter();
        }
        return searchFilter;
    }

    class SearchFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    }

    public final class ViewHolder {
        public TextView name;
        public TextView district;
    }
}
