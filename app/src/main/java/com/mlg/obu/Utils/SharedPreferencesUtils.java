package com.mlg.obu.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {

    private static SharedPreferences sp;

    private static final String SP_NAME = "future_rich";

    public static void saveBoolean(Context context, String key, boolean value){
        if(sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static boolean getBoolean(Context context,String key,boolean value){
        if(sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getBoolean(key, value);
    }
    public static void saveString(Context context,String key,String value){
        if(sp == null)
            sp = context.getSharedPreferences(SP_NAME,0);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getString(key, defValue);
    }
    public static void saveInt(Context context, String key, int value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putInt(key, value).commit();
    }
    public static int getInt(Context context, String key, int defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getInt(key, defValue);
    }
    public static void clear(Context context) {
      //  Log.e("qqqaasssddddd", "onClick:111 " );
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
      //  Log.e("qqqaasssddddd", "onClick:aaa" );

        //editor.clear();
        sp.edit().clear().commit();
      //  Log.e("qqqaasssddddd", "onClick:aaacc" );
    }
    public static void remove(Context context,String name) {

        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        //editor.clear();
        sp.edit().remove(name).commit();
    }
}
