package com.chrissen.zhitian.model.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/8/27 0027.
 */

public class DefaultCity extends DataSupport {

    private String cityName;
    private String parentCityName;
    private String longitude;
    private String latitude;


    public DefaultCity() {
    }

    public DefaultCity(String cityName, String parentCityName, String longitude, String latitude) {
        this.cityName = cityName;
        this.parentCityName = parentCityName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getParentCityName() {
        return parentCityName;
    }

    public void setParentCityName(String parentCityName) {
        this.parentCityName = parentCityName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
