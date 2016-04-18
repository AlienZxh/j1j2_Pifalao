package com.j1j2.pifalao.feature.individualcenter;

import android.app.Activity;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.j1j2.data.model.User;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.app.event.VipUpdateSuccessEvent;
import com.j1j2.pifalao.app.sharedpreferences.UserLoginPreference;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.FragmentIndividualcenterBinding;
import com.j1j2.pifalao.feature.individualcenter.di.IndividualCenterModule;
import com.j1j2.pifalao.feature.main.MainActivity;
import com.litesuits.common.io.FilenameUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.toolsfinal.io.FileUtils;
import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

/**
 * Created by alienzxh on 16-3-18.
 */
@RequireBundler
public class IndividualCenterFragment extends BaseFragment implements View.OnClickListener {
    public static final int FROM_INDIVIDUALCENTERACTIVITY = 0;

    public static final int FROM_MAINACTIVITY = 1;


    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;

    public interface IndividualCenterFragmentListener {
        void navigateToOrderManager();

        void navigateToQRCode();

        void navigateToAddressManager();

        void navigateToWalletManager();

        void navigateToMessages();

        void navigateToCollects();

        void navigateToAccount();

        void navigateToVipUpdate();

        void navigateToSetting();
    }

    private IndividualCenterFragmentListener listener;

    FragmentIndividualcenterBinding binding;

    @Inject
    User user;

    @Inject
    IndividualCenterViewModel individualCenterViewModel;

    @Inject
    UserRelativePreference userRelativePreference;

    @Arg
    int fragmentType;

    @Inject
    UnReadInfoManager unReadInfoManager;

    AlertDialog chooseImgDialog;

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            userRelativePreference.setUserImg(resultList.get(0).getPhotoPath());
            binding.userImg.setImageURI(Uri.parse("file://" + resultList.get(0).getPhotoPath()));
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(IndividualCenterFragment.this.getContext().getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (IndividualCenterFragmentListener) activity;
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_individualcenter, container, false);
        binding.setIndividualCenterViewModel(individualCenterViewModel);
        binding.setUnReadInfoManager(unReadInfoManager);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        individualCenterViewModel.queryUser();
        //___________________________________________________________
        if (userRelativePreference.getUserImg(null) != null) {
            binding.userImg.setImageURI(Uri.parse("file://" + userRelativePreference.getUserImg(null)));
        }
        //__________________________________________________
        if (fragmentType != FROM_INDIVIDUALCENTERACTIVITY)
            binding.backBtn.setVisibility(View.GONE);
        //_______________________________________________________________
        chooseImgDialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .setTitle("请选择")
                .setItems(new CharSequence[]{"拍摄照片", "本地图片"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0)
                            GalleryFinal.openCamera(REQUEST_CODE_CAMERA, mOnHanlderResultCallback);
                        else
                            GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, mOnHanlderResultCallback);
                    }
                })
                .create();
    }


    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(getActivity()).getUserComponent().plus(new IndividualCenterModule(this)).inject(this);
    }


    @Subscribe
    public void onVipUpdateSuccessEvent(VipUpdateSuccessEvent event) {
        individualCenterViewModel.queryUser();
    }


    @Override
    public void onStop() {
        super.onStop();
        if (chooseImgDialog != null && chooseImgDialog.isShowing())
            chooseImgDialog.cancel();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.orderManager) {
            listener.navigateToOrderManager();
        }
        if (v == binding.qrCode) {
            listener.navigateToQRCode();
        }
        if (v == binding.adderssManager) {
            listener.navigateToAddressManager();
        }
        if (v == binding.walletManager) {
            listener.navigateToWalletManager();
        }
        if (v == binding.massageManager) {
            listener.navigateToMessages();
        }
        if (v == binding.collectManager) {
            listener.navigateToCollects();
        }
        if (v == binding.accountManager) {
            listener.navigateToAccount();
        }
        if (v == binding.updateVIP) {
            listener.navigateToVipUpdate();
        }
        if (v == binding.setting) {
            listener.navigateToSetting();
        }
        if (v == binding.backBtn)
            getActivity().onBackPressed();
        if (v == binding.userImg)
            chooseImgDialog.show();
    }
}
