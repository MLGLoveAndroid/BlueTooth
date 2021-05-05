package com.mlg.obu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import com.gyf.barlibrary.ImmersionBar;
import com.mlg.obu.Base.BaseActivity;
import com.mlg.obu.Utils.LEDView;

public class TestActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ImmersionBar.with(this)
                .transparentBar()
                .fullScreen(true)
                .statusBarDarkFont(true)
                .init();
        LEDView led = findViewById(R.id.led1);
        LEDView led2 = findViewById(R.id.led2);
        LEDView led3 = findViewById(R.id.led3);
        led.setLedView(60,"90");
        led2.setLedView(60,"120");
        led3.setLedView(60,"68");
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }


}
