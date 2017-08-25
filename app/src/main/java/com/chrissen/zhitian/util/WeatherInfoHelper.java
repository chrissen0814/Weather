package com.chrissen.zhitian.util;

import android.content.res.Resources;

import com.chrissen.zhitian.MyApplication;
import com.chrissen.zhitian.R;
import com.chrissen.zhitian.model.bean.entity.Daily;
import com.chrissen.zhitian.model.bean.entity.Hourly;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class WeatherInfoHelper {


    public static String getHourlyWeatherTipsInfo(List<Hourly> hourlyList){
        String info = "24小时\n无雨雪天气";
        for (Hourly hourly : hourlyList){
            if(!hourly.getImg().isEmpty()){
                int imgCode = Integer.valueOf(hourly.getImg());
                if(imgCode>= 3){
                    info = hourly.getTime() + "\n" + hourly.getWeather();
                    return info;
                }
            }
        }
        return info;
    }

    public static String getDayWeatherTipsInfo(List<Daily> dailyList){
        String weatherInfo = "未来7天\n无雨雪天气";
        for(Daily daily : dailyList){
            if(!daily.getDay().getImg().isEmpty()){
                int dayImgCode = Integer.valueOf(daily.getDay().getImg());
                int nightImgCode = Integer.valueOf(daily.getNight().getImg());
                if(dayImgCode >= 3){
                    if(getDay(daily.getDate()).equals("今天")){
                        weatherInfo = getDay(daily.getDate()) + "白天\n" + daily.getDay().getWeather();
                    }else {
                        weatherInfo = getDay(daily.getDate()) + "日白天\n" + daily.getDay().getWeather();
                    }
                    return weatherInfo;
                }
                if(nightImgCode >= 3){
                    if(getDay(daily.getDate()).equals("今天")){
                        weatherInfo = getDay(daily.getDate()) + "夜\n" + daily.getDay().getWeather();
                    }else {
                        weatherInfo = getDay(daily.getDate()) + "日夜\n" + daily.getDay().getWeather();
                    }
                    return weatherInfo;
                }
            }
        }
        return weatherInfo;
    }

    public static String getDay(String date){
        String[] dates = date.split("-");
        String today = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        if(dates[2].equals(today)){
            return "今天";
        }
        return dates[2];
    }

    public static String getUpdateTime(String time){
        String[] times = time.split(" ");
        return times[1];
    }


    public static int getWeatherImagePath(String img){
        Resources resources = MyApplication.getContext().getResources();
        String packageName = MyApplication.getContext().getPackageName();
        String category = "drawable";
        int weatherImagePath = 0;
        if (!img.isEmpty()) {
            int imgCode = Integer.valueOf(img);
            if(imgCode == 0){
                weatherImagePath = resources.getIdentifier("weather_sunny",category,packageName);
            }else if(imgCode == 1){
                weatherImagePath = resources.getIdentifier("weather_cloudy",category,packageName);
            }else if(imgCode == 2){
                weatherImagePath = resources.getIdentifier("weather_overcast",category,packageName);
            }else if(imgCode == 3){
                weatherImagePath = resources.getIdentifier("weather_rain_shower",category,packageName);
            }else if(imgCode == 4){
                weatherImagePath = resources.getIdentifier("weather_shower",category,packageName);
            }else if(imgCode == 5){
                weatherImagePath = resources.getIdentifier("weather_hail",category,packageName);
            }else if(imgCode == 6 || imgCode == 7){
                weatherImagePath = resources.getIdentifier("weather_light_rain",category,packageName);
            }else if(imgCode == 8 || imgCode == 21 || imgCode == 22 ){
                weatherImagePath = resources.getIdentifier("weather_moderate_rain",category,packageName);
            }else if(imgCode == 9 || imgCode == 301|| imgCode == 23){
                weatherImagePath = resources.getIdentifier("weather_heavy_rain",category,packageName);
            }else if(imgCode == 10 || imgCode == 11 || imgCode == 12 || imgCode == 24 || imgCode == 25){
                weatherImagePath = resources.getIdentifier("weather_rainstorm",category,packageName);
            }else if(imgCode == 14 || imgCode == 15 || imgCode == 26){
                weatherImagePath = resources.getIdentifier("weather_light_snow",category,packageName);
            }else if(imgCode == 13 || imgCode == 16 || imgCode == 17 || imgCode == 27 || imgCode == 28 || imgCode == 302){
                weatherImagePath = resources.getIdentifier("weather_moderate_snow",category,packageName);
            }else if(imgCode == 18 || imgCode == 32 || imgCode == 49 || imgCode == 57 || imgCode == 58 ){
                weatherImagePath = resources.getIdentifier("weather_fog",category,packageName);
            }else if(imgCode == 19){
                weatherImagePath = resources.getIdentifier("weather_icerain",category,packageName);
            }else if(imgCode == 20 || imgCode == 29 || imgCode == 30 || imgCode == 31){
                weatherImagePath = resources.getIdentifier("weather_sand",category,packageName);
            }else if(imgCode >= 53 && imgCode <= 56){
                weatherImagePath = resources.getIdentifier("weather_haze",category,packageName);
            }
            return weatherImagePath;
        }
        return weatherImagePath;
    }

    public static int getWeatherColor(String img){
        Resources resources = MyApplication.getContext().getResources();
        int weatherColor = 0;
        if (!img.isEmpty()) {
            int imgCode = Integer.parseInt(img);
            if(imgCode == 0){
                weatherColor = resources.getColor(R.color.sunny);
            }else if(imgCode == 1 || imgCode == 2){
                weatherColor = resources.getColor(R.color.cloudy_overcast);
            }else if(imgCode >= 3 && imgCode <= 12 || imgCode == 19 || imgCode>= 21 && imgCode <= 25 || imgCode == 301){
                weatherColor = resources.getColor(R.color.rain);
            }else if(imgCode >= 13 && imgCode <= 17 || imgCode >= 26 && imgCode <= 28 || imgCode == 302){
                weatherColor = resources.getColor(R.color.snow);
            }else if(imgCode == 18 || imgCode == 32 || imgCode == 49 || imgCode == 57 || imgCode == 58){
                weatherColor = resources.getColor(R.color.fog);
            }else if(imgCode == 20 || imgCode == 29 || imgCode == 30 || imgCode == 31){
                weatherColor = resources.getColor(R.color.sand);
            }else if(imgCode >= 53 && imgCode <= 56){
                weatherColor = resources.getColor(R.color.haze);
            }
            return weatherColor;
        }
        return weatherColor;
    }

    public static int getAirqualityColor(String airquality){
        Resources resources = MyApplication.getContext().getResources();
        int airqualityColor = 0;
        if (!airquality.isEmpty()) {
            switch (airquality){
                case "优":
                    airqualityColor = resources.getColor(R.color.airquality_good);
                    break;
                case "良":
                    airqualityColor = resources.getColor(R.color.airquality_miderate);
                    break;
                case "轻度污染":
                    airqualityColor = resources.getColor(R.color.airquality_lightly_polltue);
                    break;
                case "中度污染":
                    airqualityColor = resources.getColor(R.color.airquality_miderate_pollute);
                    break;
                case "重度污染":
                    airqualityColor = resources.getColor(R.color.airquality_heavy_pollute);
                    break;
                case "严重污染":
                    airqualityColor = resources.getColor(R.color.airquality_deep_plooute);
                    break;
            }
            return airqualityColor;
        }
        return airqualityColor;
    }

    public static int getWeatherType(String img){

        int weatherType = 0;

        if (!img.isEmpty()) {
            int imgCode = Integer.parseInt(img);
            if(imgCode == 0){
                weatherType = R.string.weatherview_sunny;
            }else if(imgCode == 1 || imgCode == 2){
                weatherType = R.string.weatherview_cloudy;
            }else if(imgCode >= 3 && imgCode <= 12 || imgCode == 19 || imgCode>= 21 && imgCode <= 25 || imgCode == 301){
               weatherType = R.string.weatherview_rainy;
            }else if(imgCode >= 13 && imgCode <= 17 || imgCode >= 26 && imgCode <= 28 || imgCode == 302){
                weatherType = R.string.weatherview_snowy;
            }else if(imgCode == 18 || imgCode == 32 || imgCode == 49 || imgCode == 57 || imgCode == 58){
                weatherType = R.string.weatherview_foggy;
            }else if(imgCode == 20 || imgCode == 29 || imgCode == 30 || imgCode == 31){
                weatherType = R.string.weatherview_sand;
            }else if(imgCode >= 53 && imgCode <= 56){
                weatherType = R.string.weatherview_hazy;
            }
            return weatherType;
        }
        return weatherType;
    }


}
