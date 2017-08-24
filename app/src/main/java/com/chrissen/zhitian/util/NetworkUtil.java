package com.chrissen.zhitian.util;

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

}
