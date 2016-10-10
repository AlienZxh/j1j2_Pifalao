package com.j1j2.pifalao.feature.individualcenter;

import android.app.Activity;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.common.util.EmptyUtils;
import com.j1j2.common.util.Toastor;
import com.j1j2.data.model.User;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.UnReadInfoManager;
import com.j1j2.pifalao.app.base.BaseFragment;
import com.j1j2.pifalao.app.sharedpreferences.UserRelativePreference;
import com.j1j2.pifalao.databinding.FragmentIndividualcenterBinding;
import com.j1j2.pifalao.feature.individualcenter.di.IndividualCenterModule;
import com.j1j2.pifalao.feature.participationrecord.ParticipationRecordActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.toolsfinal.io.FileUtils;
import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;

import com.j1j2.common.util.PhotoUtils;
import com.orhanobut.logger.Logger;

/**
 * Created by alienzxh on 16-3-18.
 */
@RequireBundler
public class IndividualCenterFragment extends BaseFragment implements View.OnClickListener {


    @Override
    protected String getFragmentName() {
        return "IndividualCenterFragment";
    }

    public static final int FROM_INDIVIDUALCENTERACTIVITY = 0;

    public static final int FROM_MAINACTIVITY = 1;

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;

    public interface IndividualCenterFragmentListener {

        void showFragmentProgress(String msg);

        void dismissFragmentProgress();

        void navigateToOrderManager();

        void navigateToQRCode();

        void navigateToAddressManager();

        void navigateToWalletManager();

        void navigateToMessages();

        void navigateToCollects();

        void navigateToBriberymonerys();

        void navigateToVipUpdate();

        void navigateToSetting();

        void navigateToOrders(int orderType);

        void navigateToParticipationRecord(int activityType);
    }

    private IndividualCenterFragmentListener listener;

    FragmentIndividualcenterBinding binding;

    @Inject
    User user;

    @Inject
    IndividualCenterViewModel individualCenterViewModel;

    @Inject
    UserRelativePreference userRelativePreference;

    @Inject
    Toastor toastor;

    @Arg
    int fragmentType;

    @Inject
    UnReadInfoManager unReadInfoManager;


    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {

            if (!EmptyUtils.isEmpty(resultList)) {
                final String path = resultList.get(0).getPhotoPath();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        int orientation = PhotoUtils.readPictureDegree(path);//获取旋转角度
                        Bitmap bitmap = PhotoUtils.getimage(path);//压缩图片
                        if (Math.abs(orientation) > 0) {
                            bitmap = PhotoUtils.rotaingImageView(orientation, bitmap);//旋转图片
                        }
                        bitmap = PhotoUtils.imageCropSquare(bitmap);
                        String dirPath = Constant.FilePath.compressPhotoFolder;
                        String fileName = System.currentTimeMillis() + "_compress.jpg";
                        PhotoUtils.saveBitmap(dirPath, fileName, bitmap);
                        individualCenterViewModel.upLoadUserImg(new File(dirPath, fileName));
                    }
                });
                IndividualCenterFragment.this.getListener().showFragmentProgress("头像上传中");
                thread.start();

            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            toastor.showSingletonToast(errorMsg);
        }
    };


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (IndividualCenterFragmentListener) activity;
    }


    @Override
    public void onResume() {
        super.onResume();
        individualCenterViewModel.queryUser(false);
        individualCenterViewModel.queryOrderStatistics();
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

        SpannableString spannableString = new SpannableString(binding.updateVIP.getText().toString());
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.updateVIP.setText(spannableString);
        //__________________________________________________
        if (fragmentType != FROM_INDIVIDUALCENTERACTIVITY)
            binding.backBtn.setVisibility(View.GONE);
        //_______________________________________________________________
        messageDialog = new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .setTitle("请选择")
                .setItems(new CharSequence[]{"　　拍摄照片", "　　本地图片"}, new DialogInterface.OnClickListener() {
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mOnHanlderResultCallback != null) {
            mOnHanlderResultCallback = null;
        }
    }

    public IndividualCenterFragmentListener getListener() {
        return listener;
    }

    @Override
    public void onClick(View v) {
        if (v == binding.briberymonery) {
            listener.navigateToBriberymonerys();
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

        if (v == binding.updateVIP) {
            listener.navigateToVipUpdate();
        }
        if (v == binding.setting) {
            listener.navigateToSetting();
        }
        if (v == binding.backBtn)
            getActivity().onBackPressed();
        if (v == binding.userImg)
            messageDialog.show();

        if (v == binding.SubmitOrder) {
            listener.navigateToOrders(Constant.OrderType.ORDERTYPE_SUBMIT);
        } else if (v == binding.UnPayOrder) {
            listener.navigateToOrders(Constant.OrderType.ORDERTYPE_UNPAY);
        } else if (v == binding.ExcutingOrder) {
            listener.navigateToOrders(Constant.OrderType.ORDERTYPE_EXECUTING);
        } else if (v == binding.DeliveryOrder) {
            listener.navigateToOrders(Constant.OrderType.ORDERTYPE_DELIVERY);
        } else if (v == binding.unRateOrder) {
            listener.navigateToOrders(Constant.OrderType.ORDERTYPE_WAITFORRATE);
        } else if (v == binding.allOrder) {
            listener.navigateToOrders(Constant.OrderType.ORDERTYPE_ALL);
        }

        if (v == binding.allParticipationBtn) {
            listener.navigateToParticipationRecord(ParticipationRecordActivity.RECORD_ONGOING);
        }

    }
}
