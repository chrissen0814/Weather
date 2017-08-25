package com.chrissen.zhitian.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
                String cityFullName = bdLocation.getCity();
                String district = bdLocation.getDistrict();
                String cityName = cityFullName.substring(0,cityFullName.length()-1);
                List<City> districtList = DataSupport.where("cityname = ?",district).find(City.class);
                SavedCity defaultCity = DataSupport.find(SavedCity.class,1);
                if(defaultCity != null){
                    if(defaultCity.getCityName().equals(district) || defaultCity.getCityName().equals(cityName)){
                        EventBus.getDefault().post(defaultCity);
                    }else {
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
                                }else {
                                    Toast.makeText(context, "请手动搜索城市(︶︹︺)", Toast.LENGTH_SHORT).show();
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
                        } else{
                            List<City> parentCityList = DataSupport.where("cityname = ?",cityName).find(City.class);
                            if(parentCityList.size() == 1){
                                City parentCity = parentCityList.get(0);
                                String parentCityId = parentCity.getCityId();
                                for(City city : districtList){
                                    if(city.getParentId().equals(parentCityId)){
                                        SavedCity savedCity = new SavedCity(city.getCityId(),city.getParentId(),city.getCityCode(),city.getCityName());
                                        if(DataSupport.count(SavedCity.class) == 0){
                                            savedCity.save();
                                        }else {
                                            savedCity.update(1);
                                        }
                                        EventBus.getDefault().post(savedCity);
                                        break;
                                    }
                                }
                            }else {
                                Toast.makeText(context, "请手动搜索城市(︶︹︺)", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }else {
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
                            }else {
                                Toast.makeText(context, "请手动搜索城市(︶︹︺)", Toast.LENGTH_SHORT).show();
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
                    } else{
                        List<City> parentCityList = DataSupport.where("cityname = ?",cityName).find(City.class);
                        if(parentCityList.size() == 1){
                            City parentCity = parentCityList.get(0);
                            String parentCityId = parentCity.getCityId();
                            for(City city : districtList){
                                if(city.getParentId().equals(parentCityId)){
                                    SavedCity savedCity = new SavedCity(city.getCityId(),city.getParentId(),city.getCityCode(),city.getCityName());
                                    if(DataSupport.count(SavedCity.class) == 0){
                                        savedCity.save();
                                    }else {
                                        savedCity.update(1);
                                    }
                                    EventBus.getDefault().post(savedCity);
                                    break;
                                }
                            }
                        }else {
                            Toast.makeText(context, "请手动搜索城市(︶︹︺)", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                locationClient.stop();
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

}
