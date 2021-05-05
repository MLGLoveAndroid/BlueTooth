package com.mlg.obu.BlueTooTh.utils;

import android.widget.Toast;

import com.mlg.obu.Base.App;

/**
 * Created by dingjikerbo on 2016/9/6.
 */
public class CommonUtils {

    public static void toast(String text) {
        Toast.makeText(App.getInstance(), text, Toast.LENGTH_SHORT).show();
    }
}
