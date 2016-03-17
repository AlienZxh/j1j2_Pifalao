package com.j1j2.pifalao.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.pifalao.app.MainAplication;
import com.squareup.leakcanary.RefWatcher;
import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * Created by alienzxh on 16-3-16.
 */
public abstract class BaseFragment extends RxFragment {
    protected abstract View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void initViews();

    protected void setupActivityComponent() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivityComponent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initBinding(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MainAplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
