package com.mlg.obu.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mlg.obu.Base.BaseActivity;
import com.mlg.obu.R;
import com.mlg.obu.Utils.aes.AESUtils;

public class AESECBActivity extends BaseActivity implements View.OnClickListener {
    private EditText encryptionContext;
    private Button encryption;
    private TextView tvEncryption;
    private Button decode;
    private TextView tvDecode;
    private Activity mActivity;
    private Context mContext;
    private String key = "mengliguo1234567";//必须16位
    private byte[] encrypt;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_aesecb;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        encryptionContext = (EditText) findViewById(R.id.et_encryption_context);
        encryption = (Button) findViewById(R.id.btn_encryption);
        tvEncryption = (TextView) findViewById(R.id.tv_encryption);
        decode = (Button) findViewById(R.id.btn_decode);
        tvDecode = (TextView) findViewById(R.id.tv_decode);
        encryption.setOnClickListener(this);
        decode.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_encryption://加密
                String encryptionString = encryptionContext.getText().toString().trim();
                if (TextUtils.isEmpty(encryptionString)) {
                    Toast.makeText(mContext, "请输入加密内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                encrypt = AESUtils.encrypt(encryptionString.getBytes(), key.getBytes());
                tvEncryption.setText(new String(encrypt));
                break;
            case R.id.btn_decode://解密
                String decodeString = tvEncryption.getText().toString().trim();
                if (TextUtils.isEmpty(decodeString)) {
                    Toast.makeText(mContext, "请先加密", Toast.LENGTH_SHORT).show();
                    return;
                }
                byte[] decrypt = AESUtils.decrypt(encrypt, key.getBytes());
                tvDecode.setText(new String(decrypt));
                break;
        }
    }
}
