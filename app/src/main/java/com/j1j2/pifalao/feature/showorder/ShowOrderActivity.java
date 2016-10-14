package com.j1j2.pifalao.feature.showorder;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j1j2.common.util.EmptyUtils;
import com.j1j2.common.util.PhotoUtils;
import com.j1j2.data.http.api.ActivityApi;
import com.j1j2.data.model.AcceptanceSpeechImg;
import com.j1j2.data.model.ActivityProcessStateProductInfo;
import com.j1j2.data.model.WebReturn;
import com.j1j2.data.model.requestbody.AcceptanceSpeechSubmit;
import com.j1j2.pifalao.BuildConfig;
import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.MainAplication;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.base.WebReturnSubscriber;
import com.j1j2.pifalao.app.event.PrizeOrderStateChangeEvent;
import com.j1j2.pifalao.databinding.ActivityShoworderBinding;
import com.j1j2.pifalao.feature.individualcenter.IndividualCenterFragment;
import com.j1j2.pifalao.feature.prizedetail.di.PrizeDetailModule;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import in.workarounds.bundler.Bundler;
import in.workarounds.bundler.annotations.Arg;
import in.workarounds.bundler.annotations.RequireBundler;
import okhttp3.Call;
import okhttp3.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-9-5.
 */
@RequireBundler
public class ShowOrderActivity extends BaseActivity implements View.OnClickListener, PrizeImgAdapter.PrizeImgAdapterListener {

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;

    ActivityShoworderBinding binding;

    PrizeImgAdapter adapter;

    @Arg
    int orderId;
    @Arg
    ActivityProcessStateProductInfo activityProcessStateProductInfo;
    @Arg
    String orderNO;

    @Inject
    ActivityApi activityApi;
    @Inject
    Gson gson;

    int selectPosition = 0;

    List<AcceptanceSpeechImg> acceptanceSpeechImgs = new ArrayList<>();

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (EmptyUtils.isEmpty(resultList)) {

            } else {
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
                        postAcceptanceSpeechImg(selectPosition, new File(dirPath, fileName));
                    }
                });
                ShowOrderActivity.this.showProgress("图片上传中");
                thread.start();
            }


        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            toastor.showSingletonToast(errorMsg);
        }
    };

    @Override
    protected void setupActivityComponent() {
        super.setupActivityComponent();
        Bundler.inject(this);
        MainAplication.get(this).getUserComponent().plus(new PrizeDetailModule(this)).inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_showorder);
    }

    @Override
    protected void initViews() {
        binding.backBtn.setOnClickListener(this);
        binding.submit.setOnClickListener(this);

        binding.setProduct(activityProcessStateProductInfo);
        binding.setOrderNO(orderNO);


        binding.imgList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        binding.list.addItemDecoration(new HorizontalDividerItemDecoration
//                .Builder(getContext())
//                .color(0xffd2d2d2)
//                .size(1)
//                .build());
        binding.imgList.setAdapter(adapter = new PrizeImgAdapter(acceptanceSpeechImgs));
        adapter.setListener(this);
    }

    private void fillAcceptanceSpeechText() {
        showProgress("晒单提交中");
        AcceptanceSpeechSubmit acceptanceSpeechSubmit = new AcceptanceSpeechSubmit();
        acceptanceSpeechSubmit.setOrderId(orderId);
        acceptanceSpeechSubmit.setMsg(binding.msg.getText().toString());
        acceptanceSpeechSubmit.setImgs(acceptanceSpeechImgs);
        activityApi.fillAcceptanceSpeech(acceptanceSpeechSubmit)
                .compose(this.<WebReturn<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WebReturnSubscriber<String>() {
                    @Override
                    public void onWebReturnSucess(String s) {
                        EventBus.getDefault().post(new PrizeOrderStateChangeEvent());
                        dismissProgress();
                        toastor.showSingletonToast(s);
                        finish();
                    }

                    @Override
                    public void onWebReturnFailure(String errorMessage) {
                        dismissProgress();
                        toastor.showSingletonToast(errorMessage);
                    }

                    @Override
                    public void onWebReturnCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissProgress();
                        toastor.showSingletonToast("连接失败，请重试");
                    }
                });
    }

    private void postAcceptanceSpeechImg(final int position, File file) {
//        showProgress("图片上传中");
        OkHttpUtils.post()
                .url(BuildConfig.IMAGE_URL + "/v5/Activity/PostAcceptanceSpeechImg?orderId=" + orderId)
                .addFile("imgFile", file.getName(), file)
                .build()
                .execute(new Callback<WebReturn<String>>() {
                    @Override
                    public WebReturn<String> parseNetworkResponse(Response response) throws Exception {
                        return gson.fromJson(response.body().string(), new TypeToken<WebReturn<String>>() {
                        }.getType());
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        dismissProgress();
                    }

                    @Override
                    public void onResponse(WebReturn<String> response) {
                        dismissProgress();
                        if (response.isValue()) {
                            toastor.showSingletonToast("图片提交成功");
                            String[] imgs = response.getDetail().split("&");
                            AcceptanceSpeechImg acceptanceSpeechImg = new AcceptanceSpeechImg();
                            acceptanceSpeechImg.setNormalImg(imgs[0]);
                            acceptanceSpeechImg.setS320X320(imgs[1]);

                            Logger.d("zxh position " + position + "  size " + acceptanceSpeechImgs.size());

                            if (acceptanceSpeechImgs.size() <= position) {
                                acceptanceSpeechImgs.add(acceptanceSpeechImg);
                                adapter.notifyDataSetChanged();
                            } else {
                                acceptanceSpeechImgs.set(position, acceptanceSpeechImg);
                                adapter.notifyItemRangeChanged(position, 1);
                            }


                        } else {
                            toastor.showSingletonToast(response.getErrorMessage());
                        }
                    }
                });
    }

    @Override
    public void onImgClick(int position) {
        selectPosition = position;
        showImgChooseDialog();
    }

    public void showImgChooseDialog() {
        if (messageDialog != null && messageDialog.isShowing())
            messageDialog.dismiss();
        messageDialog = new AlertDialog.Builder(this)
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
        messageDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn)
            onBackPressed();
        if (v == binding.submit) {
            if (TextUtils.isEmpty(binding.msg.getText().toString())) {
                toastor.showSingletonToast("评论不能为空");
                return;
            }
            fillAcceptanceSpeechText();
        }
    }
}
