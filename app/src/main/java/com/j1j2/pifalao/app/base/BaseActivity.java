package com.j1j2.pifalao.app.base;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.Navigate;
import com.j1j2.pifalao.app.di.ActivityComponent;
import com.j1j2.pifalao.app.di.ActivityModule;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by alienzxh on 16-3-4.
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    @Inject
    protected Navigate navigate;

    protected Fragment currentFragment;

    protected void initActionBar() {
    }

    protected abstract void initBinding();

    protected abstract void initViews();

    protected void setupActivityComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupActivityComponent();
        initBinding();
        initActionBar();
        initViews();

    }

    protected void changeFragment(int resView, Fragment targetFragment) {
        if (targetFragment.equals(currentFragment)) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(resView, targetFragment, targetFragment.getClass()
                    .getName());
        }
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
        }
        if (currentFragment != null
                && currentFragment.isVisible()) {
            transaction.hide(currentFragment);
        }
        currentFragment = targetFragment;
        transaction.commit();
    }

    protected ActivityComponent getActivityComponent() {
        return MainAplication.get(this).getAppComponent().plus(getActivityModule());
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

}
