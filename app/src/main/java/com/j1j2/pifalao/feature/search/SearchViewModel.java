package com.j1j2.pifalao.feature.search;

import com.j1j2.data.http.api.ProductApi;
import com.j1j2.data.model.HotKey;
import com.j1j2.data.model.WebReturn;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-29.
 */
public class SearchViewModel {
    private SearchActivity searchActivity;
    private ProductApi productApi;

    public SearchViewModel(SearchActivity searchActivity, ProductApi productApi) {
        this.searchActivity = searchActivity;
        this.productApi = productApi;
    }

    public void queryHotKey(int moduleId) {
        productApi.queryHotKeys(moduleId)
                .compose(searchActivity.<WebReturn<List<HotKey>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<List<HotKey>>() {
                    @Override
                    public void onWebReturnSucess(List<HotKey> hotKeys) {

                        List<String> strings = new ArrayList<String>();
                        for (HotKey hotKey : hotKeys) {
                            strings.add(hotKey.getHotKey());
                        }
                        SearchAdapter searchAdapter = new SearchAdapter(strings);
                        searchActivity.setHotKeyAdapter(searchAdapter);
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {

                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }
                });
    }
}
