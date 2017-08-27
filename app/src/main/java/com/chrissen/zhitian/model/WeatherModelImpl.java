package com.chrissen.zhitian.model;

import com.chrissen.zhitian.model.bean.DefaultCity;
import com.chrissen.zhitian.model.bean.SavedCity;
import com.chrissen.zhitian.model.bean.Weather;
import com.chrissen.zhitian.presenter.OnWeatherListener;
import com.chrissen.zhitian.util.Api;
import com.chrissen.zhitian.util.PreferencesLoader;
import com.chrissen.zhitian.util.RetrofitFactory;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class WeatherModelImpl implements WeatherModel {

    @Override
    public void loadCityWeather(SavedCity savedCity,final OnWeatherListener listener) {

        new RetrofitFactory(Api.JISU_URL).getApiInterface()
                .getWeather(Api.JISU_APP_KEY,savedCity.getCityCode())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Weather weather) {
                        listener.loadSuccess(weather);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void loadLocationWeather(DefaultCity defaultCity, final OnWeatherListener listener) {
        String locationInfo = defaultCity.getLatitude() + "," + defaultCity.getLongitude();
        new RetrofitFactory(Api.JISU_URL).getApiInterface()
                .getLocationWeather(Api.JISU_APP_KEY,locationInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Weather weather) {
                        int temp = Integer.valueOf(weather.getInfo().getTemp());
                        int img = Integer.valueOf(weather.getInfo().getImg());
                        PreferencesLoader.putInt(PreferencesLoader.DEFAULT_CITY_TEMP,temp);
                        PreferencesLoader.putInt(PreferencesLoader.DEFAULT_CITY_IMG,img);
                        listener.loadSuccess(weather);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailure(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
