package com.chrissen.zhitian.presenter;

import com.chrissen.zhitian.model.bean.DefaultCity;
import com.chrissen.zhitian.model.bean.SavedCity;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public interface WeatherPresenter {
    void getWeather(SavedCity savedCity);

    void getLocationWeather(DefaultCity defaultCity);
}
