package com.hankkin.hlibrarydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Bind;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hankkin.hlibrary.base.BaseParentActivity;
import com.hankkin.hlibrary.utils.statusbar.StatusBarUtil;
import java.util.Arrays;
import java.util.List;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class MainActivity extends BaseParentActivity implements BaseQuickAdapter.OnRecyclerViewItemClickListener {

    @Bind(R.id.rv_main) RecyclerView rvMain;

    private List<String> mainData;
    private QuickAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setHasBus(true);
        initToolBar("HLibraryDemo");
        StatusBarUtil.setColor(activity, getResources().getColor(R.color.colorPrimary), 0);

        mainData = Arrays.asList(getResources().getStringArray(R.array.main_data));
        rvMain.setLayoutManager(new LinearLayoutManager(activity));
        mAdapter = new QuickAdapter(R.layout.adapter_main_item, mainData);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnRecyclerViewItemClickListener(this);
        rvMain.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view, int i) {
        switch (i) {
            case 0://沉浸通知栏
                gotoActivity(StatusBarActivity.class);
                break;
            case 1://拍照相册
                Intent intent = new Intent(activity, MultiImageSelectorActivity.class);
                // 是否显示拍摄图片
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                // 最大可选择图片数量
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
                // 选择模式
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                startActivity(intent);

                break;
            case 5://统一dialog
                new MaterialDialog.Builder(activity).title("Github地址")
                    .content("https://github.com/Hankkin/material-dialogs/")
                    .positiveText("确定")
                    .show();
                break;
            case 7://自定义loading
                showLoadingDialog();
            default:
                break;
        }
    }

    private class QuickAdapter extends BaseQuickAdapter<String> {

        public QuickAdapter(int layoutResId, List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, String s) {
            holder.setText(R.id.tv_adapter_main, s);
        }
    }
}
