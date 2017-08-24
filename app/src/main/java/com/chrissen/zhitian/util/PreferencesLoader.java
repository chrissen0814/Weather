package com.chrissen.zhitian.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.chrissen.zhitian.MyApplication;

/**
 * Created by Administrator on 2017/4/7.
 */

public class PreferencesLoader {

    public static final String IMPORT_DATA = "import_data";
    public static final String WEATHER_COLOR = "weather_color";


    public static void putBoolean(String name , boolean flag){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext()).edit();
        editor.putBoolean(name,flag);
        editor.apply();
    }

    public static Boolean getBoolean( String name , boolean strData){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        return sp.getBoolean(name,strData);
    }

    public static void putInt( String name , int flag){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext()).edit();
        editor.putInt(name,flag);
        editor.apply();
    }

    public static int getInt(String name , int strData){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        return sp.getInt(name,strData);
    }


}
