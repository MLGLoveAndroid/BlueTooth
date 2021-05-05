package com.mlg.obu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.inuker.bluetooth.library.Code;
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.mlg.obu.BlueTooTh.ControlBLEActivity;
import com.mlg.obu.BlueTooTh.MainBLEActivity;
import com.mlg.obu.BlueTooTh.utils.BaseVolume;
import com.mlg.obu.BlueTooTh.utils.BlueWindowHint;
import com.mlg.obu.BlueTooTh.utils.ClientManager;
import com.mlg.obu.BlueTooTh.utils.CommonUtils;
import com.mlg.obu.Utils.Util;
import com.mlg.obu.udp.UDPSocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.inuker.bluetooth.library.Constants.STATUS_DISCONNECTED;

public class Main2Activity extends AppCompatActivity {

    private ImageView image;
    private byte[] b;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
       // b = new byte[]{1, 2, 3, 4, 5, 6, 7};
    }

    private void initView() {

/*


        byte[] b = new  byte[] {1,2,3};

        byte[] b1 = new  byte[] {4,5,6};


        ArrayList list  = new ArrayList<> ();

        for (int i = 0; i < b1.length; i++) {
            list.add(b1[i]);
        }

        for (int i = 0; i < b.length; i++) {
            list.add(i,b[i]);

        }

        for (int i = 0; i < list.size(); i++) {
            Log.e("TAG", "initView: " + list.get(i) );
        }



        System.out.println(Arrays.toString(b));

        System.out.println(Arrays.toString(b1));

*/


        /*try {

            UDPSocket.getInstance().sendUdpV2X(1000,b);
        } catch (IOException e) {
            e.printStackTrace();
        }*/




         image = (ImageView) findViewById(R.id.image);

        //创建一个AnimationDrawable
        AnimationDrawable animationDrawable1 = new AnimationDrawable();
        //准备好资源图片
        int[] ids = {R.drawable.h1,R.drawable.h2,R.drawable.h3,R.drawable.h4,R.drawable.h5};
        //通过for循环添加每一帧动画
        for(int i = 0 ; i < 5 ; i ++){
            Drawable frame = getResources().getDrawable(ids[i]);
            //设定时长
            animationDrawable1.addFrame(frame,100);
        }
        animationDrawable1.setOneShot(false);
        //将动画设置到背景上
        image.setBackground(animationDrawable1);
        //开启帧动画
        animationDrawable1.start();
      /* MainFragment mainFragment = new MainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.aaaas,mainFragment).commit();
*/
    }
  /*
    package com.mlg.obu.BlueTooTh;

import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.inuker.bluetooth.library.Code;
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.utils.ByteUtils;
import com.mlg.obu.Base.App;
import com.mlg.obu.BlueTooTh.utils.BaseVolume;
import com.mlg.obu.BlueTooTh.utils.BlueWindowHint;
import com.mlg.obu.BlueTooTh.utils.ClientManager;
import com.mlg.obu.BlueTooTh.utils.CommonUtils;
import com.mlg.obu.Net.MyOkhttpUtils;
import com.mlg.obu.R;
import com.mlg.obu.Utils.Util;
import com.mlg.obu.udp.UDPSocket;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

import static com.inuker.bluetooth.library.Constants.STATUS_DISCONNECTED;

    public class ControlBLEActivity extends AppCompatActivity implements View.OnClickListener {
        // [1,6,1,1,2,3,3,1,3,3,3,34,4,4,4,4,5,5,6,67,7,7,7,,4,4,4,4,4,5,5,5,5,5,5,5,5,5,7,7,7,7,7,7,67,66,6,6,6,64,4,5,566,5,2,2,3,4,5,6,7,8,33,9,0,1,1,1,12,2,3,333,2,1,2,1,2,3,4,5,6,7,8,2,3,4,2,2,3,4,5,5,55,44,33,22,22,11,22,1,2,2,2,2,3,3,1,6,1,1,2,3,3,1,3,3,3,34,4,4,4,4,5,5,6,67,7,7,7,,4,4,4,4,4,5,5,5,5,5,5,5,5,5,7,7,7,7,7,7,67,66,6,6,6,64,4,5,566,5,2,2,3,4,5,6,7,8,33,9,0,1,1,1,12,2,3,333,2,1,2,1,2,3,4,5,6,7,8,2,3,4,2,2,3,4,5,5,55,44,33,22,22,11,22,1,2,2,2,2,3,3,1,6,1,1,2,3,3,1,3,3,3,34,4,4,4,4,5,5,6,67,7,7,7,4,4,4,4,44,22,1,2,2,2,2,3,3]
        //a[1,6,1,1,2,3,3,1,3,3,3,34,4,4,4,4,5,5,6,67,7,7,7,,4,4,4,4,4,5,5,5,5,5,5,5,5,5,7,7,7,7,7,7,67,66,6,6,6,64,4,5,566,5,2,2,3,4,5,6,7,8,33,9,0,1,1,1,12,2,3,333,2,1,2,1,2,3,4,5,6,7,8,2,3,4,2,2,3,4,5,5,55,44,33,22,22,11,22,1,2,2,2,2,3,3,1,6,1,1,2,3,3,1,3,3,3,34,4,4,4,4,5,5,6,67,7,7,7,,4,4,4,4,4,5,5,5,5,5,5,5,5,5,7,7,7,7,7,7,67,66,6,6,6,64,4,5,566,5,2,2,3,4,5,6,7,8,33,9,0,1,1,1,12,2,3,333,2,1,2,1,2,3,4,5,6,7,8,2,3,4,2,2,3,4,5,5,55,44,33,22,22,11,22,1,2,2,2,2,3,3,1,6,1,1,2,3,3,1,3,3,3,34,4,4,4,4,5,5,6,67,7,7,7,4,4,4,4,44,22,1,2,2,2,2,3,3]b
        private EditText edSendView, edTime;
        private ScrollView scrollView;
        private TextView tvLogView,
                tvSendCount, tvSendSuccess,
                tvSendError, tvReceiveCountByNotify;
        private CheckBox cbAutoSend;
        private int iSendCount = 0;
        private int iSendSuccess = 0;
        private int iSendError = 0;
        private int iReceiveCountByNotify = 0;
        private StringBuilder stringBuilder = new StringBuilder();
        private RadioButton rbZiFu, rbShiLiu;
        private String s;
        private byte[] sea = new byte[]{9};
        private TextView receive;
        private final String file ="file:///android_asset/a.txt";
        private String cachePath;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            UDPSocket.getInstance().ShowData();
            UDPSocket.getInstance().setUdpReceiveCallback(new UDPSocket.OnUDPReceiveCallbackBlock() {
                @Override
                public void OnParserComplete(byte[] data) {
                    Log.e("aaaaa", "OnParserComplete: ==" + Arrays.toString(data));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());
                    final String str = formatter.format(curDate);
                    Message message = new Message();
                    message.obj = str + "===" + Arrays.toString(data);
                    message.what = 1;   //标志消息的标志
                    handler.sendMessage(message);
                }
            });

            setContentView(R.layout.activity_controlble);

            // 校验和
            //  String strCheck = NetworkUtils.makeCheckSum("1234567890");

            initUI();
            bindReceiver();
        }

        private void initUI() {
            receive = findViewById(R.id.receive_textView);
            edSendView = (EditText) findViewById(R.id.send_value);
            edTime = (EditText) findViewById(R.id.edTime);
            scrollView = (ScrollView) findViewById(R.id.scrollView);
            tvLogView = (TextView) findViewById(R.id.tvLog);
            tvSendCount = (TextView) findViewById(R.id.tvSendCount);
            tvSendSuccess = (TextView) findViewById(R.id.tvSendSuccess);
            tvSendError = (TextView) findViewById(R.id.tvSendError);
            tvReceiveCountByNotify = (TextView) findViewById(R.id.tvReceiveCountByNotify);

            rbZiFu = findViewById(R.id.rbZiFu);
            rbShiLiu = findViewById(R.id.rbShiLiu);

            rbZiFu.setChecked(true);
            rbShiLiu.setChecked(false);

            findViewById(R.id.button_Send).setOnClickListener(this);
            findViewById(R.id.button_File).setOnClickListener(this);
            findViewById(R.id.button_ClearLog).setOnClickListener(this);
            findViewById(R.id.tvPeiWang).setOnClickListener(this);

            cbAutoSend = (CheckBox) findViewById(R.id.cbAutoSend);

            cbAutoSend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (edSendView.getText().equals("") && edTime.getText().equals("")) {
                            cbAutoSend.setChecked(false);
                            return;
                        }
                        edSendView.setEnabled(false);
                        edTime.setEnabled(false);
                        handler.post(runnableSend);
                    } else {
                        edSendView.setEnabled(true);
                        edTime.setEnabled(true);
                        handler.removeCallbacks(runnableSend);
                    }
                }
            });

        }

        private void bindReceiver() {
            // 注册连接成功的回调
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(BaseVolume.BROADCAST_RECVPIPE);
            intentFilter.addAction(BaseVolume.BROADCAST_SEND_TIMEOUT);
            intentFilter.addAction(BaseVolume.BROADCAST_SEND_RESULT);
            intentFilter.addAction("AutoDisconnectDevice");
            com.mlg.obu.BlueTooTh.ControlBLEActivity.this.registerReceiver(broadcastReceiver, intentFilter);

        }

        @Override
        protected void onStop() {
            super.onStop();
            UDPSocket.getInstance().StopSocket();

        }

        Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {      //判断标志位
                    case 1:
                        String text = msg.obj.toString();
                        receive.setText(text);
                        break;
                }
            }
        };

        Runnable runnableSend = new Runnable() {
            @Override
            public void run() {
                sendData();
                if (edTime.getText().toString().equals("")) {
                    return;
                }
                int iTime = Integer.parseInt(edTime.getText().toString());
                handler.postDelayed(this, iTime);
            }
        };

        // private String hex = "";
        // private byte[] sendByte = null;
        private byte[] sendByte3 = new byte[]{1,2,3,4,5,6,-7,8,9,0,1,22,11,22,33,44,-127,0,0,-1,2,3,67,56,19};

        private void sendData() {

           *//*
       hex = edSendView.getText().toString();

        if (TextUtils.isEmpty(hex)) {
            return;
        }
        // 字符
        if (rbZiFu.isChecked()) {
            sendByte = hex.getBytes();
        }
        // 十六进制
        else {
            sendByte = ByteUtils.toByteArray(hex);
        }
        iSendCount = iSendCount + sendByte.length;
*//*

            String sendStr = new String(sendByte3, Charset.forName("ISO-8859-1"));


            if (sendStr.length() > 16) {
                final StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(sendStr);

                Log.e("TAG", "sendData: "+ stringBuffer.toString());

                for (int i = 0; i < stringBuffer.length(); i++) {

                    if (stringBuffer.length() > 16) {
//                  发送消息标识 0 为数据  1 为文件
                        byte    instructions = 0;
//                    发送数据分包总数

                        int totalnumber  =  stringBuffer.length()/16+1;

                        byte  totalnumberinstructions = (byte) totalnumber;
//                  当前包数
                        int currentinnumber  =  i+1;

                        byte currentinstructions = (byte) currentinnumber;
//                    当前包数据长度
//
                        byte packetdatalength = 16;

                        byte[] bytes = new byte[]{instructions,totalnumberinstructions,
                                currentinstructions,packetdatalength};

                        final String d = stringBuffer.substring(0, 16);

                        byte[] bytes1 = d.getBytes(Charset.forName("ISO-8859-1"));

                        // Log.e("bytes1sss", "111sendDatasss: " +Arrays.toString(bytes) );

                        byte[] listbyte = byteMarge(bytes1, bytes);

                        Log.e("bytes1sss", "sendDatasss: " +Arrays.toString(listbyte) );

                        MainBLEActivity.getInstance().startSendData(listbyte);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvSendCount.setText("发送总长度：" + iSendCount);
                                addText("Send success"
                                        + "，Write: " + d
                                        + "，Length：" + d.length());
                            }
                        });
                        // System.out.println(d);
                        stringBuffer.delete(0, 16);

                        if (stringBuffer.length() < 16) {
                            *//*最后的包数据个数*//*
                            int sbl = stringBuffer.length();
                            byte  sbtotalnumberinstructions = (byte) sbl;
                            //                  当前包数
                            int sbcurrentinnumber  =  currentinnumber +1;

                            byte sbcurrentinstructions = (byte) sbcurrentinnumber;


                            byte[] sbbytes = new byte[]{instructions,totalnumberinstructions,sbcurrentinstructions,sbtotalnumberinstructions};

                            String sbdata = stringBuffer.toString();

                            byte[] sbbytes2 = sbdata.getBytes(Charset.forName("ISO-8859-1"));

                            byte[] listbytesb = byteMarge(sbbytes2, sbbytes);

                            Log.e("bytes1sss", "sendDatasssss222: " +Arrays.toString(listbytesb) );

                            MainBLEActivity.getInstance().
                                    startSendData(listbytesb);

                            //  System.out.println(stringBuffer.toString());

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvSendCount.setText("发送总长度：" + iSendCount);
                                    addText("Send success"
                                            + "，Write: " + stringBuffer.toString()
                                            + "，Length：" + stringBuffer.toString().length());
                                }
                            });
                        }
                    }
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvSendCount.setText("发送总长度：" + iSendCount);
                        addText("Send success"
                                + "，Write: " + sendByte3
                                + "，Length：" + sendByte3.length);
                    }
                });

                MainBLEActivity.getInstance().startSendData(sendByte3);

            }
        }

        private byte[] byteMarge(byte[] a ,byte[] b) {
            ArrayList list  = new ArrayList<> ();

            for (int f = 0; f < a.length; f++) {
                list.add(a[f]);
            }

            for (int x = 0; x < b.length; x++) {
                list.add(x,b[x]);
            }

            byte[]  listbytes = new byte[list.size()];

            for (int x = 0; x < list.size(); x++) {

                listbytes[x] = (byte) list.get(x);
            }
            return listbytes;
        }

        private void addText(String content) {
            Calendar c = Calendar.getInstance();
            int HH = c.get(Calendar.HOUR_OF_DAY);
            int MM = c.get(Calendar.MINUTE);
            int SS = c.get(Calendar.SECOND);
            int MS = c.get(Calendar.MILLISECOND);
            String strTime = String.format("[%02d:%02d:%02d:%02d] %s\n", HH, MM, SS, MS, content);
            tvLogView.append(strTime);
            tvLogView.append("\n");

            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    int offset = tvLogView.getMeasuredHeight() - scrollView.getMeasuredHeight();
                    if (offset > 0) {
                        scrollView.scrollTo(0, offset);
                    }
                }
            });

        }

        int a = 0;
        int b = -1;
        private List listb = null;

        ArrayList list = new ArrayList();
        ArrayList listd = new ArrayList();

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 接收数据
                if (intent.getAction().equalsIgnoreCase(BaseVolume.BROADCAST_RECVPIPE)) {

                    byte[] value = intent.getByteArrayExtra(BaseVolume.BROADCAST_RECVPIPE);
                    //String strNewData = "";
                    // stringBuilder.append((char) Integer.parseInt(value[i] + ""));
                    // 字符
                    // if (rbZiFu.isChecked()) {
                    // Log.d("aaaa1111 === ", stringBuilder.toString());

                  *//*  ArrayList lists = new ArrayList<>();
                    for (int i = 0; i < value.length; i++) {
                        lists.add(value[i]);
                    }
                    for (int i = 0; i <lists.size(); i++) {
                        shouinstructions = (byte) lists.get(0);
                        showtotalnumberinstructions =  (byte) lists.get(1);
                        showcurrentinstructions =  (byte) lists.get(2);
                        showpacketdatalength =  (byte) lists.get(3);
                        lists.remove(0);
                        lists.remove(1);
                        lists.remove(2);
                        lists.remove(3);
                        stringBuilder.append((char) Integer.parseInt(value[i] + ""));
                    }
                    for (int i = 0; i < stringBuilder.length(); i++) {
//                        a = stringBuilder.indexOf("[");
//                        b = stringBuilder.indexOf("]");
                    }
*//*

                    int shouinstructions  = -1;
                    int showtotalnumberinstructions = -1;
                    int showcurrentinstructions  = -1;
                    int showpacketdatalength = -1;

                    list.clear();
                    for (int i = 0; i < value.length; i++) {
                        list.add(value[i]);
                    }
                    // Log.d("ssssss === ",list.size()+"");

                    shouinstructions = (byte) list.get(0);
                    showtotalnumberinstructions =  (byte) list.get(1);
                    showcurrentinstructions = (byte) list.get(2);
                    showpacketdatalength =  (byte) list.get(3);

                    listb =  list.subList(4, value.length);

                    listd.addAll(listb);
                    //00 02 01 10 01 02 03 04 05 06 F9 08 09 00 01 16 0B 16 21 2C
                    //00 02 02 09 81 00 00 FF 02 03 43 38 13
                    byte[] bs = new byte[listd.size()];

                    for (int i = 0; i < listd.size(); i++) {

                        bs[i] =  (byte) listd.get(i);
                    }
                    // Log.d("ssssss === ", Arrays.toString(bs));
                    addText("Receive by Notify:" +  Arrays.toString(bs) + "，Length：" + listd.size());

                    if(showcurrentinstructions == showtotalnumberinstructions ){
                        list.clear();
//                    listb.clear();
                        listd.clear();
                    }
             *//*   if (a == 0 && b != -1) {
                    s = stringBuilder.substring(a, b+1);
                        addText("Receive by Notify:" +  s + "，Length：" + s.length());
                        stringBuilder.delete(0, b + 1);
                    }
               // }
                  //  strNewData= new String(value);
                 //  }
                // 十六进制
                else {
                       //strNewData = ByteUtils.byteToString(value);
                }*//*

             *//*   iReceiveCountByNotify = iReceiveCountByNotify + value.length;
                tvReceiveCountByNotify.setText("接收总长度：" + iReceiveCountByNotify);
                *//*
               *//*    Log.e("Receive", "onReceive:   == " +
                         strNewData + "--" + iReceiveCountByNotify + "value.length==" + value.length);
                 addText("Receive by Notify:" +  strNewData + "，Length：" + value.length);*//*
                }
                // 发送超时
                else if (intent.getAction().equalsIgnoreCase(BaseVolume.BROADCAST_SEND_TIMEOUT)) {

                }
                //发送结果
                else if (intent.getAction().equalsIgnoreCase(BaseVolume.BROADCAST_SEND_RESULT)) {
                    boolean isSuccess = intent.getBooleanExtra(BaseVolume.BROADCAST_SEND_RESULT, false);
                    int iSendLength = intent.getIntExtra(BaseVolume.BROADCAST_SEND_LENGTH, 0);
                    final int iCode = intent.getIntExtra(BaseVolume.BROADCAST_SEND_CODE, 0);
                    if (isSuccess) {
                        iSendSuccess = iSendSuccess + iSendLength;
                        tvSendSuccess.setText("成功：" + iSendSuccess);
                    } else {
                        iSendError = iSendError + iSendLength;
                        tvSendError.setText("失败：" + iSendError);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                addText("Send Error:" + Code.toString(iCode));
                            }
                        });
                    }
                } else if (intent.getAction().equalsIgnoreCase("AutoDisconnectDevice")) {
                    finish();
                }
                // Log.d("aaaaaaaaa", "onReceive: "+stringBuilder.toString());
            }
        };


        public void onPause() {
            super.onPause();
            ClientManager.getClient().unregisterConnectStatusListener(MainBLEActivity.getInstance().nowSelectDevice.getAddress(), mConnectStatusListener);
        }

        private final BleConnectStatusListener mConnectStatusListener = new BleConnectStatusListener() {
            @Override
            public void onConnectStatusChanged(String mac, int status) {
                Log.e("BlueDeviceListActivity", String.format("CharacterActivity.onConnectStatusChanged status = %d", status));
                if (status == STATUS_DISCONNECTED) {

                    CommonUtils.toast("disconnected");
                    BlueWindowHint hintDialog = new BlueWindowHint(com.mlg.obu.BlueTooTh.ControlBLEActivity.this, R.style.dialog_style, "系统提示", new BlueWindowHint.PeriodListener() {
                        public void confirmListener() {
                            onBackPressed();
                        }

                        public void cancelListener() {
                            onBackPressed();
                        }
                    }, "连接异常，请重试！", true);
                    hintDialog.show();
                }
            }
        };

        public void onResume() {
            super.onResume();
            // 注册蓝牙连接状态监听
            ClientManager.getClient().registerConnectStatusListener(MainBLEActivity.getInstance().nowSelectDevice.getAddress(), mConnectStatusListener);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_ClearLog:
                    tvLogView.setText("");
                    tvSendCount.setText("发送总长度：0");
                    tvSendSuccess.setText("成功：0");
                    tvSendError.setText("失败：0");
                    tvReceiveCountByNotify.setText("接收总长度：0");
                    iSendCount = 0;
                    iReceiveCountByNotify = 0;
                    iSendSuccess = 0;
                    iSendError = 0;
                    break;
                case R.id.button_Send:
                    if (edSendView.getText().toString().equals("")) {
                        return;
                    }
                    sendData();
                    break;
                case R.id.button_File:

                    //initUpData("");

                    sendFile(file);
                    break;
                case R.id.tvPeiWang:
                    //   Intent intent = new Intent(ControlBLEActivity.this, SmartLinkActivity.class);
                    //  startActivity(intent);
                    break;
            }
        }

        *//*  private void initUpData(String  url) {

              if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
                      cachePath =ControlBLEActivity.this.getExternalCacheDir().getPath();
              } else {
                  cachePath = ControlBLEActivity.this.getFilesDir().getAbsolutePath();
              }

              MyOkhttpUtils.downNewApp(url, new FileCallBack(cachePath,"blueTooth.") {
                  @Override
                  public void onError(Call call, Exception e, int id) {
                      Toast.makeText(ControlBLEActivity.this, "", Toast.LENGTH_SHORT).show();
                  }
                  @Override
                  public void onResponse(File response, int id) {
                      Log.e("aasassdsdx", "onResponse: "+ id );
                      //initApk(response);
                      App.showToast("下载成功");
                      sendFile(cachePath);
                  }
                  @Override
                  public void inProgress(float progress, long total, int id) {
                      super.inProgress(progress, total, id);
                      double d = Double.parseDouble(String.valueOf(progress * 100));
                      int s = new Double(d).intValue();
                    //  pro.setText(s + "%");
                  }
              });
          }*//*
        public void sendFile(final String filePath) {

            Util.EXECUTOR.execute(new Runnable() {
                @Override
                public void run() {
                    int byte_size = 4080;
                    byte[] b = new byte[byte_size];
                    try {
                        Log.e("aaaaaaafile", "sendFile: 1qq"+  b.length);
                        InputStream fileInputStream =
                                com.mlg.obu.BlueTooTh.ControlBLEActivity.this.getClass().getResourceAsStream("/assets/a.txt");
                        Log.e("aaaaaaafile", "sendFile: 2qq"+  b.length);
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(byte_size);
                        Log.e("aaaaaaafile", "sendFile: 7qq"+  b.length);
                        for (int length; (length = fileInputStream.read(b)) != -1;) {
                            outputStream.write(b, 0, length);
                            Log.e("aaaaaaafile", "sendFile: 3qq"+  b.length);
                        }
                        Log.e("aaaaaaafile", "sendFile:4qq"+  b.length);
                        fileInputStream.close();
                        outputStream.close();
                        byte[] fileBytes = outputStream.toByteArray();
                        Log.e("aaaaaaafile", "sendFile: "+ outputStream.toByteArray().length);
                        //MainBLEActivity.getInstance().startSendData(outputStream.toByteArray());
                        if (fileBytes.length>244){
                            String s =  new String(fileBytes) ;
                            final StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append(s);
                            for (int i = 0; i < stringBuffer.length(); i++) {
                                if (stringBuffer.length() > 244) {
                                    final String d = stringBuffer.substring(0, 244);
                                    MainBLEActivity.getInstance().startSendData(d.getBytes());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tvSendCount.setText("发送总长度：" + iSendCount);
                                            addText("Send success"
                                                    + "，Write: " + d
                                                    + "，Length：" + d.length());
                                        }
                                    });
                                    // System.out.println(d);
                                    stringBuffer.delete(0, 244);
                                    if (stringBuffer.length() < 244) {
                                        MainBLEActivity.getInstance().
                                                startSendData(stringBuffer.toString().getBytes());
                                        //  System.out.println(stringBuffer.toString());
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                tvSendCount.setText("发送总长度：" + iSendCount);
                                                addText("Send success"
                                                        + "，Write: " + stringBuffer.toString()
                                                        + "，Length：" + stringBuffer.toString().length());
                                            }
                                        });
                                    }
                                }
                            }
                        }



                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


        }
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(runnableSend);
            com.mlg.obu.BlueTooTh.ControlBLEActivity.this.unregisterReceiver(broadcastReceiver);
            ClientManager.getClient().unregisterConnectStatusListener(MainBLEActivity.getInstance().nowSelectDevice.getAddress(), mConnectStatusListener);
            ClientManager.getClient().disconnect(MainBLEActivity.getInstance().nowSelectDevice.getAddress());
        }


    }*/

}
