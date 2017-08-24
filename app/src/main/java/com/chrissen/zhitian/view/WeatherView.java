package com.chrissen.zhitian.view;

import com.chrissen.zhitian.model.bean.Weather;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public interface WeatherView {
    void showWeather(Weather weather);
    void showErrorInfo(String error);
}
