package com.j1j2.pifalao.wxapi;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.view.View;

import com.j1j2.pifalao.R;
import com.j1j2.pifalao.app.Constant;
import com.j1j2.pifalao.app.base.BaseActivity;
import com.j1j2.pifalao.app.event.WeiXinPayReturnEvent;
import com.j1j2.pifalao.databinding.ActivityWxpayentryBinding;
import com.orhanobut.logger.Logger;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by alienzxh on 16-5-13.
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler, View.OnClickListener {

    ActivityWxpayentryBinding binding;

    private IWXAPI wxApi;

    private boolean isSuccess;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wxpayentry);
        binding.setOnClick(this);
        wxApi = WXAPIFactory.createWXAPI(this, Constant.WEIXIN_APP_ID);
        wxApi.handleIntent(getIntent(), this);
    }

    @Override
    protected void initViews() {
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backBtn || v == binding.cancelBtn || v == binding.confirmBtn) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        EventBus.getDefault().post(new WeiXinPayReturnEvent(isSuccess));
        super.onBackPressed();
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Logger.d("weixinresult " + baseResp.errCode + " " + baseResp.errStr + " " + baseResp.openId + " " + baseResp.openId + " " + baseResp.transaction);
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {
                isSuccess = true;
                binding.successIcon.setText(getResources().getText(R.string.icon_round_check_fill));
                binding.successIcon.setTextColor(0xff4ab134);
                binding.messageText.setText("微信支付成功");
                binding.messageText.setTextColor(0xff4ab134);
                binding.cancelBtn.setVisibility(View.GONE);
            } else {
                isSuccess = false;
                binding.successIcon.setText(getResources().getText(R.string.icon_close_fill));
                binding.successIcon.setTextColor(0xffff9900);
                binding.messageText.setText("微信支付失败");
                binding.messageText.setTextColor(0xffff9900);
                binding.confirmBtn.setVisibility(View.GONE);
            }
        }
    }
}
