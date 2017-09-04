package com.chrissen.zhitian.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.chrissen.zhitian.MyApplication;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by Administrator on 2017/4/18.
 */

public class NetworkUtil {

    public static boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) MyApplication.getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    public static boolean isWifiConnected() {
        ConnectivityManager manager = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
            return networkInfo.isAvailable();
        }
        return false;
    }

    public static boolean isMobileConnected() {
        ConnectivityManager manager = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
            return networkInfo.isAvailable();
        }
        return false;
    }



}
