package com.mlg.obu.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogUtil {

    public static final String DIALOG_TITLE="提示信息";

    public static final String POSITIVE_BUTTON="确定";

    public static final String NEGATIVE_BUTTON="取消";

    public static AlertDialog alertDialog;

    public static synchronized void showDialog(Context context, String message, DialogInterface.OnClickListener OnClickListener){
        if(alertDialog!=null&&alertDialog.isShowing())
            return;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // 设置对话框标题
        builder.setTitle(DIALOG_TITLE);
        // 设置对话框消息
        builder.setMessage(""+message);

        // 设置确定按钮
        builder.setPositiveButton(POSITIVE_BUTTON, OnClickListener);

        // 创建一个消息对话框

        alertDialog= builder.create();
        //设置点击其他位置dialog不取消
        alertDialog.setCancelable(false) ;

        alertDialog.show();
    }

    public synchronized static void showDialog(Context context, String message
            ,DialogInterface.OnClickListener OnClickListenerPosition
            ,DialogInterface.OnClickListener OnClickListenerNegative){
        if(alertDialog!=null&&alertDialog.isShowing())
            return;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // 设置对话框标题
        builder.setTitle(DIALOG_TITLE);
        // 设置对话框消息
        builder.setMessage(""+message);

        // 设置确定按钮
        builder.setPositiveButton(POSITIVE_BUTTON, OnClickListenerPosition);
        //设置取消按钮
        builder.setNegativeButton(NEGATIVE_BUTTON,OnClickListenerNegative);
        // 创建一个消息对话框
        alertDialog= builder.create();
        //设置点击其他位置dialog不取消
        alertDialog.setCancelable(false) ;

        alertDialog.show();
    }

    public synchronized static boolean showDialog(Context context, String message
            ,String positionBtnMsg
            ,DialogInterface.OnClickListener OnClickListenerPosition
            ,String negativeBtnMsg
            ,DialogInterface.OnClickListener OnClickListenerNegative){
        if(alertDialog!=null&&alertDialog.isShowing())
            return false;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // 设置对话框标题
        builder.setTitle(DIALOG_TITLE);
        // 设置对话框消息
        builder.setMessage(""+message);

        // 设置确定按钮
        builder.setPositiveButton(positionBtnMsg, OnClickListenerPosition);
        //设置取消按钮
        builder.setNegativeButton(negativeBtnMsg,OnClickListenerNegative);
        // 创建一个消息对话框
        alertDialog= builder.create();
        //设置点击其他位置dialog不取消
        alertDialog.setCancelable(false) ;

        alertDialog.show();
        return true;
    }

}
