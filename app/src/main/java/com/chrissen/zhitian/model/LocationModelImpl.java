package com.chrissen.zhitian.model;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.chrissen.zhitian.MyApplication;
import com.chrissen.zhitian.model.bean.DefaultCity;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class LocationModelImpl implements LocationModel {
    private static final String TAG = "LocationModelImpl";
    private Context context;
    private LocationClient locationClient;
    private MyLocationListener locationListener;

    @Override
    public void accessLocation(Context context) {
        this.context = context;
        locationClient = new LocationClient(MyApplication.getContext());
        locationListener = new MyLocationListener();
        locationClient.registerLocationListener(locationListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
        locationClient.requestHotSpotState();
        locationClient.start();
        Log.i(TAG, "accessLocation: ");
    }

    private class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation || bdLocation.getLocType() == BDLocation.TypeOffLineLocation){
                String cityName = bdLocation.getCity();
                String districtName = bdLocation.getDistrict();
                String longitude = String.valueOf(bdLocation.getLongitude());
                String latitude = String.valueOf(bdLocation.getLatitude());
                Log.i(TAG, "onReceiveLocation: " + districtName);
                Log.i(TAG, "onReceiveLocation: " + cityName);
                DefaultCity defaultCity = new DefaultCity(districtName,cityName,longitude,latitude);
                if(DataSupport.count(DefaultCity.class) == 0){
                    defaultCity.save();
                }else {
                    defaultCity.update(1);
                }
                EventBus.getDefault().post(defaultCity);
                locationClient.stop();
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

}
