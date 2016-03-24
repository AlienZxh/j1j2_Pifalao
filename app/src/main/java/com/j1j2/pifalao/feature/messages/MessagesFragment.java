package com.j1j2.pifalao.feature.messages;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.j1j2.data.http.api.UserMessageApi;
import com.j1j2.data.model.Message;
import com.j1j2.data.model.PagerManager;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.UserPushSearcherBody;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.HasComponent;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.databinding.FragmentMessagesBinding;
import com.j1j2.pifalao.feature.messages.di.MessagesComponent;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import javax.inject.Inject;

import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-3-24.
 */
@RequireBundler
public class MessagesFragment extends BaseFragment {

    public interface MessagesFragmentListener extends HasComponent<MessagesComponent> {

    }

    public static final int MESSAGE_USER = 1;
    public static final int MESSAGE_SYSTEM = 2;

    FragmentMessagesBinding binding;
    private MessagesFragmentListener listener;
    @Arg
    int messageType;

    @Inject
    UserMessageApi userMessageApi;

    MessagesAdapter adapter;

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_messages, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.messageList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.messageList.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(getContext())
                .colorResId(R.color.colorTransparent)
                .size(AutoUtils.getPercentHeightSize(10))
                .showLastDivider()
                .build());
        binding.messageList.getRecyclerView().setClipToPadding(false);
        binding.messageList.getRecyclerView().setClipChildren(false);
        binding.messageList.getRecyclerView().setPadding(0, AutoUtils.getPercentHeightSize(10), 0, 0);
        queryMessages();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (MessagesActivity) activity;
    }

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        listener.getComponent().inject(this);
    }

    public void queryMessages() {
        UserPushSearcherBody userPushSearcherBody = new UserPushSearcherBody();
        userPushSearcherBody.setMessageType(messageType);
        userMessageApi.queryPushMessageByUserId(userPushSearcherBody)
                .compose(this.<WebReturn<PagerManager<Message>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<PagerManager<Message>>() {
                    @Override
                    public void onWebReturnSucess(PagerManager<Message> messagePagerManager) {
                        adapter = new MessagesAdapter(messagePagerManager.getList());
                        binding.messageList.setAdapter(adapter);
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
