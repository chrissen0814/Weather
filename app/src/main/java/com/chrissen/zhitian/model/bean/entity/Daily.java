package com.chrissen.zhitian.model.bean.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class Daily{
    private String date;
    private String week;
    private String sunrise;
    private String sunset;
    @SerializedName("night")
    private Day night;
    @SerializedName("day")
    private Day day;

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

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public Day getNight() {
        return night;
    }

    public void setNight(Day night) {
        this.night = night;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public class Day{
        private String weather;
        @SerializedName("temphigh")
        private String tempHigh;
        private String img;
        @SerializedName("winddirect")
        private String windDirect;
        @SerializedName("windpower")
        private String windPower;

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getTempHigh() {
            return tempHigh;
        }

        public void setTempHigh(String tempHigh) {
            this.tempHigh = tempHigh;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
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
    }
}
