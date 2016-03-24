package com.j1j2.pifalao.feature.messages;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.databinding.ActivityMessagesBinding;
import com.j1j2.pifalao.feature.messages.di.MessagesComponent;
import com.j1j2.pifalao.feature.messages.di.MessagesModule;

import java.util.ArrayList;
import java.util.List;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-24.
 */
@RequireBundler
public class MessagesActivity extends BaseActivity implements MessagesFragment.MessagesFragmentListener {

    ActivityMessagesBinding binding;

    private MessagesComponent messagesComponent;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_messages);

    }

    @Override
    protected void initViews() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(Bundler.messagesFragment(MessagesFragment.MESSAGE_USER).create());
        fragments.add(Bundler.messagesFragment(MessagesFragment.MESSAGE_SYSTEM).create());
        MessagesTabAdapter messagesTabAdapter = new MessagesTabAdapter(getSupportFragmentManager(), fragments);
        binding.viewpager.setAdapter(messagesTabAdapter);
        binding.tab.setViewPager(binding.viewpager);
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        messagesComponent = MainAplication.get(this).getUserComponent().plus(new MessagesModule(this));
        messagesComponent.inject(this);
    }

    @Override
    public MessagesComponent getComponent() {
        return messagesComponent;
    }
}
