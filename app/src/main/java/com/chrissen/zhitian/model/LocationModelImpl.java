package com.chrissen.zhitian.model;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.chrissen.zhitian.MyApplication;
import com.chrissen.zhitian.model.bean.City;
import com.chrissen.zhitian.model.bean.SavedCity;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class LocationModelImpl implements LocationModel {
    private static final String TAG = "LocationModelImpl";

    private LocationClient locationClient;
    private MyLocationListener locationListener;

    @Override
    public void accessLocation() {
        locationClient = new LocationClient(MyApplication.getContext());
        locationListener = new MyLocationListener();
        locationClient.registerLocationListener(locationListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
        locationClient.requestHotSpotState();
        locationClient.start();
    }

    private class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation || bdLocation.getLocType() == BDLocation.TypeOffLineLocation){
                String cityFullName = bdLocation.getCity();
                String district = bdLocation.getDistrict();
                String cityName = cityFullName.substring(0,cityFullName.length()-1);
                List<City> districtList = DataSupport.where("cityname = ?",district).find(City.class);
                if (districtList.size() == 0) {
                    if(cityName.equals("吉林")){
                        String jilinCity = cityName + "市";
                        List<City> cityList = DataSupport.where("cityname = ?",jilinCity).find(City.class);
                        if(cityList != null && cityList.size() ==1){
                            City city = cityList.get(0);
                            SavedCity savedCity = new SavedCity(city.getCityId(),city.getParentId(),city.getCityCode(),city.getCityName());
                            if(DataSupport.count(SavedCity.class) == 0){
                                savedCity.save();
                            }else {
                                savedCity.update(1);
                            }
                            EventBus.getDefault().post(savedCity);
                            locationClient.stop();
                        }
                    }else {
                        List<City> cityList = DataSupport.where("cityname = ?",cityName).find(City.class);
                        if(cityList != null && cityList.size() ==1){
                            City city = cityList.get(0);
                            SavedCity savedCity = new SavedCity(city.getCityId(),city.getParentId(),city.getCityCode(),city.getCityName());
                            if(DataSupport.count(SavedCity.class) == 0){
                                savedCity.save();
                            }else {
                                savedCity.update(1);
                            }
                            EventBus.getDefault().post(savedCity);
                            locationClient.stop();
                        }
                    }
                }else if(districtList.size() == 1){
                    City districtCity = districtList.get(0);
                    SavedCity savedCity  = new SavedCity(districtCity.getCityId(),districtCity.getParentId(),districtCity.getCityCode(),districtCity.getCityName());
                    if(DataSupport.count(SavedCity.class) == 0){
                        savedCity.save();
                    }else {
                        savedCity.update(1);
                    }
                    EventBus.getDefault().post(savedCity);
                    locationClient.stop();
                }

            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

}
