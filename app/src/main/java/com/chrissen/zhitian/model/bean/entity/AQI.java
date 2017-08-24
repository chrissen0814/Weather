package com.chrissen.zhitian.model.bean.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class AQI{
    private String so2;
    private String so224;
    private String no2;
    private String no224;
    private String co;
    private String co24;
    private String o3;
    private String o38;
    private String o34;
    private String pm10;
    private String pm1024;
    private String pm2_5;
    private String pm2_524;
    private String iso2;
    private String ino2;
    private String ico;
    private String io3;
    private String io38;
    private String ipm10;
    private String ipm2_5;
    private String aqi;
    private String primarypollutant;
    private String quality;
    @SerializedName("timepoint")
    private String timePoint;
    @SerializedName("aqiinfo")
    private AQIInfo aqiInfo;

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }

    public String getSo224() {
        return so224;
    }

    public void setSo224(String so224) {
        this.so224 = so224;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getNo224() {
        return no224;
    }

    public void setNo224(String no224) {
        this.no224 = no224;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getCo24() {
        return co24;
    }

    public void setCo24(String co24) {
        this.co24 = co24;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getO38() {
        return o38;
    }

    public void setO38(String o38) {
        this.o38 = o38;
    }

    public String getO34() {
        return o34;
    }

    public void setO34(String o34) {
        this.o34 = o34;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getPm1024() {
        return pm1024;
    }

    public void setPm1024(String pm1024) {
        this.pm1024 = pm1024;
    }

    public String getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(String pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public String getPm2_524() {
        return pm2_524;
    }

    public void setPm2_524(String pm2_524) {
        this.pm2_524 = pm2_524;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getIno2() {
        return ino2;
    }

    public void setIno2(String ino2) {
        this.ino2 = ino2;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getIo3() {
        return io3;
    }

    public void setIo3(String io3) {
        this.io3 = io3;
    }

    public String getIo38() {
        return io38;
    }

    public void setIo38(String io38) {
        this.io38 = io38;
    }

    public String getIpm10() {
        return ipm10;
    }

    public void setIpm10(String ipm10) {
        this.ipm10 = ipm10;
    }

    public String getIpm2_5() {
        return ipm2_5;
    }

    public void setIpm2_5(String ipm2_5) {
        this.ipm2_5 = ipm2_5;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getPrimarypollutant() {
        return primarypollutant;
    }

    public void setPrimarypollutant(String primarypollutant) {
        this.primarypollutant = primarypollutant;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(String timePoint) {
        this.timePoint = timePoint;
    }

    public AQIInfo getAqiInfo() {
        return aqiInfo;
    }

    public void setAqiInfo(AQIInfo aqiInfo) {
        this.aqiInfo = aqiInfo;
    }

    public class AQIInfo implements Serializable{
        private String level;
        private String color;
        private String affect;
        private String measure;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getAffect() {
            return affect;
        }

        public void setAffect(String affect) {
            this.affect = affect;
        }

        public String getMeasure() {
            return measure;
        }

        public void setMeasure(String measure) {
            this.measure = measure;
        }
    }
}
