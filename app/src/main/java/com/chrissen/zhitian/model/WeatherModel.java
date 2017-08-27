package com.chrissen.zhitian.model;

import com.chrissen.zhitian.model.bean.DefaultCity;
import com.chrissen.zhitian.model.bean.SavedCity;
import com.chrissen.zhitian.presenter.OnWeatherListener;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public interface WeatherModel {

    void loadCityWeather(SavedCity savedCity ,OnWeatherListener listener);

    void loadLocationWeather(DefaultCity defaultCity , OnWeatherListener listener);

}
