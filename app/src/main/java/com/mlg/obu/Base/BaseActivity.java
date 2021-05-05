package com.mlg.obu.Base;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;
import com.mlg.obu.R;

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 获取布局ID
     *
     * @return  布局id
     */
    protected abstract int getContentViewLayoutID();
    /**
     * 初始化布局以及View控件
     */
    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void loadData();
    protected abstract boolean isImmersionBarEnabled();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
            initView(savedInstanceState);
            loadData();
            if (isImmersionBarEnabled()) {
             initImmersionBar();
            }
        }
    }
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bai)
                .statusBarDarkFont(true) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                .init();
    }


}
