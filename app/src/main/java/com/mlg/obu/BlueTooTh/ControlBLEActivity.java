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
        ControlBLEActivity.this.registerReceiver(broadcastReceiver, intentFilter);

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

           String sendStr = new String(sendByte3, Charset.forName("ISO-8859-1"));

        if (sendStr.length() > 16) {
            final StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(sendStr);

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

                    byte[] listbyte = byteMarge(bytes1, bytes);

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
                        /*最后的包数据个数*/
                         int sbl = stringBuffer.length();
                        byte  sbtotalnumberinstructions = (byte) sbl;
                        //当前包数
                        int sbcurrentinnumber  =  currentinnumber +1;

                        byte sbcurrentinstructions = (byte) sbcurrentinnumber;

                        byte[] sbbytes = new byte[]{instructions,totalnumberinstructions,sbcurrentinstructions,sbtotalnumberinstructions};

                        String sbdata = stringBuffer.toString();

                        byte[] sbbytes2 = sbdata.getBytes(Charset.forName("ISO-8859-1"));

                        byte[] listbytesb = byteMarge(sbbytes2, sbbytes);

                        Log.e("bytes1sss", "sendDatasssss222: " +Arrays.toString(listbytesb) );

                        MainBLEActivity.getInstance().startSendData(listbytesb);

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

    private List listb = null;
    ArrayList list = new ArrayList();
    ArrayList listd = new ArrayList();

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 接收数据
            if (intent.getAction().equalsIgnoreCase(BaseVolume.BROADCAST_RECVPIPE)) {

                byte[] value = intent.getByteArrayExtra(BaseVolume.BROADCAST_RECVPIPE);

                int shouinstructions  = -1;
                int showtotalnumberinstructions = -1;
                int showcurrentinstructions  = -1;
                int showpacketdatalength = -1;

                list.clear();
                for (int i = 0; i < value.length; i++) {
                    list.add(value[i]);
                }

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

                addText("Receive by Notify:" +  Arrays.toString(bs) + "，Length：" + listd.size());

                if(showcurrentinstructions == showtotalnumberinstructions ){
                    list.clear();
//                    listb.clear();
                    listd.clear();
                }
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
                BlueWindowHint hintDialog = new BlueWindowHint(ControlBLEActivity.this, R.style.dialog_style, "系统提示", new BlueWindowHint.PeriodListener() {
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

               sendFile();
                break;
            case R.id.tvPeiWang:
                //   Intent intent = new Intent(ControlBLEActivity.this, SmartLinkActivity.class);
                //  startActivity(intent);
                break;
        }
    }

  /*  private void initUpData(String  url) {

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
    }*/
  /*  public void sendFile(final String filePath) {

        Util.EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                int byte_size = 4080;
                byte[] fileBytes = new byte[byte_size];
                try {
                    Log.e("aaaaaaafile", "sendFile: 1qq"+  fileBytes.length);
                   InputStream fileInputStream =
                            ControlBLEActivity.this.getClass().getResourceAsStream("/assets/a.txt");
                    Log.e("aaaaaaafile", "sendFile: 2qq"+  fileBytes.length);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(byte_size);
                    Log.e("aaaaaaafile", "sendFile: 7qq"+  fileBytes.length);
                    for (int length; (length = fileInputStream.read(fileBytes)) != -1;) {
                        outputStream.write(fileBytes, 0, length);
                        Log.e("aaaaaaafile", "sendFile: 3qq"+  fileBytes.length);
                    }
                    Log.e("aaaaaaafile", "sendFile:4qq"+  fileBytes.length);
                    fileInputStream.close();
                    outputStream.close();
                    //byte[] fileBytes = outputStream.toByteArray();
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


    }*/


    public void sendFile() {
        Util.EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                int byte_size = 1024000;
                 byte[] b = new byte[byte_size];
                try {
                       Log.e("aaaaaaafile", "sendFile: 1qq"+ byte_size);
                    InputStream fileInputStream = ControlBLEActivity.this.getClass().getResourceAsStream("/assets/a.txt");
                       Log.e("aaaaaaafile", "sendFile: 2qq"+ byte_size);
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
                    Log.e("aaaaaaafile", "sendFile: " + outputStream.toByteArray().length);
                    //MainBLEActivity.getInstance().startSendData(outputStream.toByteArray());

                    String sendStr = new String(fileBytes, Charset.forName("ISO-8859-1"));


                    if (sendStr.length() > 16 * 255) {

                        StringBuffer buffer = new StringBuffer();

                        StringBuffer buffersb = new StringBuffer();

                        StringBuffer buffersbx = new StringBuffer();

                        buffer.append(sendStr);

                        for (int i = 0; i < buffer.length(); i++) {


                            if (buffer.length() > 16 * 255) {

                                byte instructions = 111;

                                byte[] bytes = new byte[]{};

                                final String d = buffer.substring(0, 16 * 255);

                                byte[] bytes1 = d.getBytes(Charset.forName("ISO-8859-1"));

                                byte[] listbyte = byteMarge(bytes1, bytes);

                                System.out.println("aaaa == " + Arrays.toString(listbyte));

                                String sendStrsb = new String(listbyte, Charset.forName("ISO-8859-1"));

                                buffersb.append(sendStrsb);

                                for (int j = 0; j < buffersb.length(); j++) {

                                    if (buffersb.length() >= 16) {
                                        //     发送消息标识 0 为数据  1 为文件

                                        byte instructionssb = 1;
                                        //   发送数据分包总数

                                        int totalnumber = buffersb.length() / 16 + 1;
                                        byte totalnumberinstructionssb = (byte) totalnumber;
                                        //   当前包数
                                        int currentinnumber = i + 1;
                                        byte currentinstructionssb = (byte) currentinnumber;
                                        //                    当前包数据长度
                                        byte packetdatalengthsb = 16;

                                        final String dsb = buffersb.substring(0, 16);

                                        byte[] bytessb = new byte[]{instructionssb, totalnumberinstructionssb,
                                                currentinstructionssb, packetdatalengthsb};

                                        byte[] bytes1sb = dsb.getBytes(Charset.forName("ISO-8859-1"));

                                        byte[] listbytesb = byteMarge(bytes1sb, bytessb);

                                        System.out.println("bbbbbb = " + Arrays.toString(listbytesb));

                                        buffersb.delete(0, 16);
                                    }
                                }


                                buffer.delete(0, 16 * 255);

                                if (buffer.length() < 16 * 255) {

                                    String sbdata = buffer.toString();

                                    byte[] sbbytes2 = sbdata.getBytes(Charset.forName("ISO-8859-1"));

                                    byte[] sbbytes = new byte[]{};

                                    byte[] listbytesb = byteMarge(sbbytes2, sbbytes);

                                    System.out.println("cccc = " + Arrays.toString(listbytesb));

                                    String sendStrsbx = new String(listbytesb, Charset.forName("ISO-8859-1"));

                                    buffersbx.append(sendStrsbx);

                                    for (int x = 0; x < buffersbx.length(); x++) {

                                        if (buffersbx.length() > 16) {

                                            //     发送消息标识 0 为数据  1 为文件

                                            byte instructionssbx = 1;
                                            //   发送数据分包总数

                                            int totalnumber = buffersb.length() / 16 + 1;
                                            byte totalnumberinstructionssbx = (byte) totalnumber;
                                            //   当前包数
                                            int currentinnumber = i + 1;
                                            byte currentinstructionssbx = (byte) currentinnumber;
                                            //                    当前包数据长度
                                            byte packetdatalengthsbx = 16;


                                            final String dsbx = buffersbx.substring(0, 16);

                                            byte[] bytessbx = new byte[]{instructionssbx,
                                                    totalnumberinstructionssbx, currentinstructionssbx, packetdatalengthsbx};

                                            byte[] bytes1sbx = dsbx.getBytes(Charset.forName("ISO-8859-1"));

                                            byte[] listbytesbx = byteMarge(bytes1sbx, bytessbx);

                                            System.out.println("ddddd = " + Arrays.toString(listbytesbx));

                                            buffersbx.delete(0, 16);

                                            if (buffersbx.length() < 16) {

                                                String sbdataxsb = buffersbx.toString();

                                                byte[] sbbytes2xsb = sbdataxsb.getBytes(Charset.forName("ISO-8859-1"));
                                                /*最后的包数据个数*/
                                                int sbl = buffersbx.length();
                                                byte sbtotalnumberinstructions = (byte) sbl;

                                                int currentinnumbers = currentinnumber + 1;
                                                byte currentinstructionssbxs = (byte) currentinnumbers;

                                                byte[] sbbytesxsb = new byte[]{instructionssbx,
                                                        totalnumberinstructionssbx, currentinstructionssbxs, sbtotalnumberinstructions};
                                                byte[] listbytesbxsb = byteMarge(sbbytes2xsb, sbbytesxsb);

                                                System.out.println("eeee = " + Arrays.toString(listbytesbxsb));

                                            }

                                        }
                                    }

                                }

                            }


                        }

                    } else {


                        Log.e("aaaaaaafile", "run: === " +  sendStr.length()  );

                        if (sendStr.length() > 16) {

                            final StringBuffer stringBuffer = new StringBuffer();

                            stringBuffer.append(sendStr);

                            for (int i = 0; i < stringBuffer.length(); i++) {

                                if (stringBuffer.length() > 16) {
//                  发送消息标识 0 为数据  1 为文件
                                    byte instructions = 1;
//                    发送数据分包总数

                                    int totalnumber = stringBuffer.length() / 16 + 1;

                                    byte totalnumberinstructions = (byte) totalnumber;
//                  当前包数
                                    int currentinnumber = i + 1;

                                    byte currentinstructions = (byte) currentinnumber;
//                    当前包数据长度
//
                                    byte packetdatalength = 16;

                                    byte[] bytes = new byte[]{instructions, totalnumberinstructions,
                                            currentinstructions, packetdatalength};

                                    final String d = stringBuffer.substring(0, 16);

                                    byte[] bytes1 = d.getBytes(Charset.forName("ISO-8859-1"));

                                    byte[] listbyte = byteMarge(bytes1, bytes);

                                    MainBLEActivity.getInstance().startSendData(listbyte);

                  /*  runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvSendCount.setText("发送总长度：" + iSendCount);
                            addText("Send success"
                                    + "，Write: " + d
                                    + "，Length：" + d.length());
                        }
                    });*/
                                    // System.out.println(d);
                                    stringBuffer.delete(0, 16);
                                    Log.e("bytes1sss", "sendDatasssss222: " + stringBuffer.length());

                                    if (stringBuffer.length() <= 16) {
                                        /*最后的包数据个数*/
                                        int sbl = stringBuffer.length();
                                        byte sbtotalnumberinstructions = (byte) sbl;
                                        //当前包数
                                        int sbcurrentinnumber = currentinnumber + 1;

                                        byte sbcurrentinstructions = (byte) sbcurrentinnumber;

                                        byte[] sbbytes = new byte[]{instructions, totalnumberinstructions, sbcurrentinstructions, sbtotalnumberinstructions};

                                        String sbdata = stringBuffer.toString();

                                        byte[] sbbytes2 = sbdata.getBytes(Charset.forName("ISO-8859-1"));

                                        byte[] listbytesb = byteMarge(sbbytes2, sbbytes);

                                        Log.e("bytes1sss", "sendDatasssss222: " + Arrays.toString(listbytesb));

                                        MainBLEActivity.getInstance().startSendData(listbytesb);

                       /* runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvSendCount.setText("发送总长度：" + iSendCount);
                                addText("Send success"
                                        + "，Write: " + stringBuffer.toString()
                                        + "，Length：" + stringBuffer.toString().length());
                            }
                        });*/
                                    }
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
        ControlBLEActivity.this.unregisterReceiver(broadcastReceiver);
        ClientManager.getClient().unregisterConnectStatusListener(MainBLEActivity.getInstance().nowSelectDevice.getAddress(), mConnectStatusListener);
        ClientManager.getClient().disconnect(MainBLEActivity.getInstance().nowSelectDevice.getAddress());
    }


}
