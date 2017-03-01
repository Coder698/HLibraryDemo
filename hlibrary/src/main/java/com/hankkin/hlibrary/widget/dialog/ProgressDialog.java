package com.hankkin.hlibrary.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import com.hankkin.hlibrary.R;
import com.hankkin.hlibrary.utils.DisplayUtils;

/**
 * Created by Hankkin on 2017/3/1.
 * 注释:
 */

public class ProgressDialog extends Dialog {



    private HLoadingView mLoadingView;

    public ProgressDialog(Context context) {
        super(context, R.style.progress);
        init(context);
    }

    public ProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    private void init(Context context) {
        requestWindowFeature(1);
        setContentView(R.layout.dialog_progress);

        mLoadingView = (HLoadingView) findViewById(R.id.my_progress);
        DisplayMetrics metric = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metric);
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.width = DisplayUtils.getScreenWidth(context);
        localLayoutParams.gravity = Gravity.CENTER;
        ColorDrawable drawable = new ColorDrawable(0x00000000);
        getWindow().setBackgroundDrawable(drawable);
        getWindow().setAttributes(localLayoutParams);
        setCanceledOnTouchOutside(true);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mLoadingView.endAnimation();
            }
        });
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                mLoadingView.startDegreeAnimation();
            }
        });
    }


}
