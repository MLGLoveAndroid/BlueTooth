package com.mlg.obu.ui;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mlg.obu.Base.BaseActivity;
import com.mlg.obu.R;
import com.mlg.obu.udp.UDPSocket;
import com.mlg.obu.udp.UDPSocketUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public class UdpActivity extends BaseActivity implements View.OnClickListener {

    private UDPSocketUtils socket;
    private String message;
    private TextView sendTextView;
    private TextView receiveTextView;
    private EditText sendEditText;
    private Button sendBtn;
    private byte[] sea = new byte[]{1};
    private byte[] seb = new byte[]{2};
    private byte[] sec = new byte[]{3};
    private byte[] sed = new byte[]{4};
    private byte[] see = new byte[]{5};
    private byte[] sef = new byte[]{6};
    private byte[] seg = new byte[]{7};
    private byte[] seh = new byte[]{8};
    private byte[] sei = new byte[]{9};

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位
                case 1:
                    String text = msg.obj.toString();
                    receive.setText(text);
                    break;
            }

        }
    };
    private TextView receive;
    private byte[] bytes;
    private byte[] bytes1;
    private Button a;
    private Button b;
    private Button c;
    private Button d;
    private Button e;
    private Button f;
    private Button g;
    private Button h;
    private TextView send;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_udp;

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

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

        sendTextView = (TextView) findViewById(R.id.send_textView);
        receiveTextView = (TextView) findViewById(R.id.receive_textView);
        sendEditText = (EditText) findViewById(R.id.send_editText);
        sendBtn = (Button) findViewById(R.id.send_btn);
        receive = findViewById(R.id.receive_textView);
        send = findViewById(R.id.send_textView);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UDPSocket.getInstance().StopSocket();
                try {
                    UDPSocket.getInstance().sendUdpV2X(1000, sei);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                send.append(Arrays.toString(sea) + '\n');
            }
        });


        a = (Button) findViewById(R.id.a);
        b = (Button) findViewById(R.id.b);
        c = (Button) findViewById(R.id.c);
        d = (Button) findViewById(R.id.d);
        e = (Button) findViewById(R.id.e);
        f = (Button) findViewById(R.id.f);
        g = (Button) findViewById(R.id.g);
        h = (Button) findViewById(R.id.h);

        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        d.setOnClickListener(this);
        e.setOnClickListener(this);
        f.setOnClickListener(this);
        g.setOnClickListener(this);
        h.setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.a:
                UDPSocket.getInstance().StopSocket();
                    try {
                        UDPSocket.getInstance().sendUdpV2X(13000, sea);
                    } catch (IOException e) {
                        e.printStackTrace();
                }
                send.append(Arrays.toString(sea) + '\n');
                break;
            case R.id.b:
                UDPSocket.getInstance().StopSocket();
                try {
                    UDPSocket.getInstance().sendUdpV2X(1000, seb);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                send.append(Arrays.toString(seb) + '\n');
                break;
            case R.id.c:
                UDPSocket.getInstance().StopSocket();
                try {
                    UDPSocket.getInstance().sendUdpV2X(1000, sec);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                send.append(Arrays.toString(sec) + '\n');
                break;
            case R.id.d:
                UDPSocket.getInstance().StopSocket();
                try {
                    UDPSocket.getInstance().sendUdpV2X(1000, sed);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                send.append(Arrays.toString(sed) + '\n');
                break;
            case R.id.e:
                UDPSocket.getInstance().StopSocket();
                try {
                    UDPSocket.getInstance().sendUdpV2X(1000, see);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                send.append(Arrays.toString(see) + '\n');
                break;
            case R.id.f:
                UDPSocket.getInstance().StopSocket();
                try {
                    UDPSocket.getInstance().sendUdpV2X(1000, sef);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                send.append(Arrays.toString(sef) + '\n');
                break;
            case R.id.g:
                UDPSocket.getInstance().StopSocket();
                try {
                    UDPSocket.getInstance().sendUdpV2X(1000, seg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                send.append(Arrays.toString(seg) + '\n');
                break;
            case R.id.h:
                UDPSocket.getInstance().StopSocket();
                try {
                    UDPSocket.getInstance().sendUdpV2X(1000, seh);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                send.append(Arrays.toString(seh) + '\n');
                break;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        UDPSocket.getInstance().StopSocket();
    }
    //   socket.startUDPSocket(message,1000);
            /*    socket.setUdpReceiveCallback(new UDPSocketUtils.OnUDPReceiveCallbackBlock() {
                    @Override
                    public void OnParserComplete(final DatagramPacket data) {
                        // String output = new String(input.getBytes("iso-8859-1"),"utf-8");
                        final String strReceive = new String(data.getData(), 0, data.getLength());
                        Log.e("aaaaa", "OnParserComplete: =="+strReceive );
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                        Date curDate = new Date(System.currentTimeMillis());
                        final String str = formatter.format(curDate);
                        Message message = new Message() ;
                        message.obj = strReceive+":"+str;
                        message.what=1;   //标志消息的标志
                        handler.sendMessage(message);
                    }
                });*/


}

