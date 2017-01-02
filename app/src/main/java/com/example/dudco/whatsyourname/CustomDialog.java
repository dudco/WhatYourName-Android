package com.example.dudco.whatsyourname;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

/**
 * Created by dudco on 2017. 1. 3..
 */

public class CustomDialog extends AlertDialog{
    String mTitle;
    protected CustomDialog(@NonNull Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }

    protected CustomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }

    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount = 0.8f;
        getWindow().setAttributes(params);

        setContentView(R.layout.custom_dialog);
    }
}
