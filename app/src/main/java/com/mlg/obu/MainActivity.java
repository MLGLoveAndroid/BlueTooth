package com.mlg.obu;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mlg.obu.Base.App;
import com.mlg.obu.Base.BaseActivity;
import com.mlg.obu.Base.BaseRepository;
import com.mlg.obu.Bean.TokenBean;
import com.mlg.obu.BlueTooTh.FirstActivity;
import com.mlg.obu.Repository.ShowRepository;
import com.mlg.obu.ui.AESECBActivity;
import com.mlg.obu.ui.UdpActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private ShowRepository showRepository;
    private android.widget.TextView titlemessage;
    private android.widget.Button intent_udp;
    private Button intent_AES_ECB;
    private Button intent_BLE;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        /*检测蓝牙 */
        initBlueTooTh();
        /*授权接口调用*/
        showRepository = new ShowRepository(MainActivity.this, true);

        titlemessage = (TextView) findViewById(R.id.titlemessage);
        intent_udp = (Button) findViewById(R.id.intent_udp);
        intent_AES_ECB = (Button) findViewById(R.id.intent_AES_ECB);
        intent_BLE = (Button) findViewById(R.id.intent_BLE);
        intent_udp.setOnClickListener(this);
        intent_AES_ECB.setOnClickListener(this);
        intent_BLE.setOnClickListener(this);
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


        showRepository.getEveryData("0012345678901234",
                0, "ADF345EWETSDWRSS",
                "20180330094056621",
                new BaseRepository.HttpCallListener<TokenBean>() {
                    @Override
                    public void onHttpCallFaile(int code, String msg) {
                        Log.e("aaaTAG", "onHttpCallFaile: " + code);
                    }

                    @Override
                    public void onHttpCallSuccess(TokenBean data) {
                        Log.e("aaaTAG", "onHttpCallSuccess: " + data.getResult());
                    }
                });
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.intent_udp) {
            /*UDP测试通过ip发送接收*/
            Intent intent = new Intent(
                    MainActivity.this, UdpActivity.class);
            startActivity(intent);
        }
        /*AES加密*/
        if (v.getId() == R.id.intent_AES_ECB) {
            Intent intent = new Intent(
                    MainActivity.this, AESECBActivity.class);
            startActivity(intent);
        }
        /*蓝牙*/
        if (v.getId() == R.id.intent_BLE) {
            Intent intent = new Intent(
                    MainActivity.this, FirstActivity.class);
            startActivity(intent);
        }
    }
}
