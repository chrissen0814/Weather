package com.chrissen.zhitian.model.bean;

import com.chrissen.zhitian.model.bean.entity.AQI;
import com.chrissen.zhitian.model.bean.entity.Daily;
import com.chrissen.zhitian.model.bean.entity.Hourly;
import com.chrissen.zhitian.model.bean.entity.Index;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class Weather {
    private int status;
    private String msg;
    @SerializedName("result")
    private Info info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public class Info{
        @SerializedName("city")
        private String cityName;
        private String date;
        private String week;
        private String weather;
        private String temp;
        @SerializedName("temphigh")
        private String tempHigh;
        @SerializedName("templow")
        private String tempLow;
        private String img;
        private String humidity;
        private String pressure;
        @SerializedName("windspeed")
        private String windSpeed;
        @SerializedName("winddirect")
        private String windDirect;
        @SerializedName("windpower")
        private String windPower;
        @SerializedName("updatetime")
        private String updateTime;
        @SerializedName("index")
        private List<Index> indexList;
        @SerializedName("aqi")
        private AQI aqi;
        @SerializedName("daily")
        private List<Daily> dailyList;
        @SerializedName("hourly")
        private List<Hourly> hourlyList;

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getTempHigh() {
            return tempHigh;
        }

        public void setTempHigh(String tempHigh) {
            this.tempHigh = tempHigh;
        }

        public String getTempLow() {
            return tempLow;
        }

        public void setTempLow(String tempLow) {
            this.tempLow = tempLow;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }

        public String getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(String windSpeed) {
            this.windSpeed = windSpeed;
        }

        public String getWindDirect() {
            return windDirect;
        }

        public void setWindDirect(String windDirect) {
            this.windDirect = windDirect;
        }

        public String getWindPower() {
            return windPower;
        }

        public void setWindPower(String windPower) {
            this.windPower = windPower;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<Index> getIndexList() {
            return indexList;
        }

        public void setIndexList(List<Index> indexList) {
            this.indexList = indexList;
        }

        public AQI getAqi() {
            return aqi;
        }

        public void setAqi(AQI aqi) {
            this.aqi = aqi;
        }

        public List<Daily> getDailyList() {
            return dailyList;
        }

        public void setDailyList(List<Daily> dailyList) {
            this.dailyList = dailyList;
        }

        public List<Hourly> getHourlyList() {
            return hourlyList;
        }

        public void setHourlyList(List<Hourly> hourlyList) {
            this.hourlyList = hourlyList;
        }

    }

}
