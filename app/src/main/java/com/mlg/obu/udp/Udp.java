package com.mlg.obu.udp;

import org.apache.commons.codec.CharEncoding;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;

public class Udp {


    public static ExecutorService mThreadPool;
    // 单个CPU线程池大小

    private static final int POOL_SIZE = 5;
    public static DatagramSocket ds;
    public static DatagramPacket dp;
    private static final long HEARTBEAT_MESSAGE_DURATION = 1000;
    public static HeartbeatTimer timer;
    private static long lastReceiveTime = 0;

    private boolean isThreadRunning = false;

    private Thread clientThread;

    private static UDPSocket udpsocket = null;

    private static final String BROADCAST_IP = "192.168.43.103";

    // 端口号
    public static final int CLIENT_PORT = 10000;
    private byte[] bytes3;
    private byte[] a;

    public static void main(String[] args) throws IOException {
        ShowData();
    }
    public static byte[] ShowData() throws IOException {
        ds = new DatagramSocket(CLIENT_PORT);
        //创建接收端的Socket服务对象，并且指定端口号
        while(true){
            //创建一个数据包，用于接收数据
            byte[] bys = new byte[1024];
            dp = new DatagramPacket(bys, bys.length);
            //接收数据
            try {
                ds.receive(dp);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //解析数据
            //获取ip地址
            String ip = dp.getAddress().getHostAddress();


            byte[] a =  dp.getData();


            byte[] bytes3 = new byte[0];
            try {
                bytes3 = new String(a, CharEncoding.ISO_8859_1).getBytes(CharEncoding.ISO_8859_1);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            System.out.println("from ip :"+ip+" is "+ "data :"+ Arrays.toString(bytes3)+"长度= "+bytes3.length);


            /*
             * if(data.equals("abc")) {
             *
             * long duration = System.currentTimeMillis() - lastReceiveTime; if (duration >
             * TIME_OUT) {//若超过两分钟都没收到我的心跳包，则认为对方不在线。 // 刷新时间，重新进入下一个心跳周期 lastReceiveTime =
             * System.currentTimeMillis(); } else if (duration > HEARTBEAT_MESSAGE_DURATION)
             * {//若超过十秒他没收到我的心跳包，则重新发一个。 // System.out.println("from ip :"+ip+" is "+
             * "data :"+data); mThreadPool.execute(new Runnable() {
             *
             * @Override public void run() { try { InetAddress targetAddress =
             * InetAddress.getByName("192.168.0.100");
             *
             * DatagramPacket dp = new DatagramPacket(message.getBytes(),
             * message.length(),targetAddress,8080);
             *
             * ds.send(dp);
             *
             * } catch (UnknownHostException e) { e.printStackTrace(); } catch (IOException
             * e) { e.printStackTrace(); }
             *
             * } }); }
             *
             *
             *
             * }
             */
            return bytes3;

        }
    }
}
