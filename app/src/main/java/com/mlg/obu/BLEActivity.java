package com.mlg.obu;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mlg.obu.Base.App;
import com.mlg.obu.Base.BaseActivity;
import com.mlg.obu.BlueTooTh.FirstActivity;

public class BLEActivity extends BaseActivity implements View.OnClickListener {

    private Button intent_BLE;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_ble;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        intent_BLE = (Button) findViewById(R.id.intent_BLE);
        intent_BLE.setOnClickListener(this);
        initBlueTooTh();

    }
    private void initBlueTooTh() {
        // 检查蓝牙开关
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            App.toast("本机没有找到蓝牙硬件或驱动！", 0);
            finish();
            return;
        } else {
            if (!adapter.isEnabled()) {
                //直接开启蓝牙
                adapter.enable();
                //跳转到设置界面
                //startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 112);
            }
        }
        // 检查是否支持BLE蓝牙
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            App.toast("本机不支持低功耗蓝牙！", 0);
            finish();
            return;
        }

        // Android 6.0动态请求权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.ACCESS_COARSE_LOCATION};
            for (String str : permissions) {
                if (checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(permissions, 111);
                    break;
                }
            }
        }
    }
    @Override
    protected void loadData() {

    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    public void onClick(View v) {
        /*蓝牙*/
        if (v.getId() == R.id.intent_BLE) {
            Intent intent = new Intent(
                    BLEActivity.this, FirstActivity.class);
            startActivity(intent);
        }
    }
}
