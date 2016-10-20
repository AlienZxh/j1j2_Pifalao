package com.j1j2.pifalao.app.dialog;

import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by alienzxh on 16-10-18.
 */

public abstract class BaseBottomDialogFragment extends BaseDialogFragment {


    @Override
    public void onStart() {
        super.onStart();
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();

        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        window.setAttributes(lp);
    }
}
