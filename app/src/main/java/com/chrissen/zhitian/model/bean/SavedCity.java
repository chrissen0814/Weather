package com.chrissen.zhitian.model.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/8/20 0020.
 */

public class SavedCity extends DataSupport {

    private String cityId;
    private String parentId;
    private String cityCode;
    private String cityName;

    public SavedCity() {
    }

    public SavedCity(String cityId, String parentId, String cityCode, String cityName) {
        this.cityId = cityId;
        this.parentId = parentId;
        this.cityCode = cityCode;
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
