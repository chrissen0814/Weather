package com.chrissen.zhitian.presenter;

import com.chrissen.zhitian.model.WeatherModel;
import com.chrissen.zhitian.model.WeatherModelImpl;
import com.chrissen.zhitian.model.bean.DefaultCity;
import com.chrissen.zhitian.model.bean.SavedCity;
import com.chrissen.zhitian.model.bean.Weather;
import com.chrissen.zhitian.view.WeatherView;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class WeatherPresenterImpl implements WeatherPresenter , OnWeatherListener{

    private WeatherModel weatherModel;
    private WeatherView weatherView;

    public WeatherPresenterImpl(WeatherView weatherView){
        this.weatherView = weatherView;
        weatherModel = new WeatherModelImpl();
    }

    @Override
    public void getWeather(SavedCity savedCity) {
        weatherModel.loadCityWeather(savedCity,this);
    }

    @Override
    public void getLocationWeather(DefaultCity defaultCity) {
        weatherModel.loadLocationWeather(defaultCity,this);
    }

    @Override
    public void loadSuccess(Weather weather) {
        weatherView.showWeather(weather);
    }

    @Override
    public void loadFailure(String msg) {
        weatherView.showErrorInfo(msg);
    }

}
