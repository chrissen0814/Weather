package com.chrissen.zhitian.view.fragment.weather_pager.weather;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.model.bean.Weather;
import com.chrissen.zhitian.util.EnglishTextView;
import com.chrissen.zhitian.util.WeatherInfoHelper;
import com.chrissen.zhitian.view.fragment.weather_pager.base.BaseSubscribeFragment;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class AqiFragment extends BaseSubscribeFragment {

    private TextView levelTv , primaryPolluteTv , affectTv , pm25Tv , pm10Tv;
    private EnglishTextView updateTimeTv;

    @Override
    protected int getLayoutId() {
        return R.layout.pager_aqi_weather;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        levelTv = (TextView) view.findViewById(R.id.aqi_quality_level_tv);
        primaryPolluteTv = (TextView) view.findViewById(R.id.aqi_primary_pollute_tv);
        affectTv = (TextView) view.findViewById(R.id.aqi_affect_tv);
        updateTimeTv = (EnglishTextView) view.findViewById(R.id.aqi_update_time_tv);
        pm25Tv = (TextView) view.findViewById(R.id.aqi_pm25_tv);
        pm10Tv = (TextView) view.findViewById(R.id.aqi_pm10_tv);
    }

    @Override
    protected void setWeather(Weather weather) {
        String levelInfo = "空气质量" + weather.getInfo().getAqi().getAqiInfo().getLevel();
        String primaryPolluteInfo = "首要污染物:" + weather.getInfo().getAqi().getPrimarypollutant();
        String[] updateTimes = weather.getInfo().getAqi().getTimePoint().split(" ");
        String pm25Info = "PM2.5: " + weather.getInfo().getAqi().getPm2_5();
        String pm10Info = "PM10: " + weather.getInfo().getAqi().getPm10();
        updateTimeTv.setText(updateTimes[1]);
        levelTv.setText(levelInfo);
        levelTv.setTextColor(WeatherInfoHelper.getAirqualityColor(weather.getInfo().getAqi().getQuality()));
        primaryPolluteTv.setText(primaryPolluteInfo);
        affectTv.setText(weather.getInfo().getAqi().getAqiInfo().getAffect());
        pm25Tv.setText(pm25Info);
        pm10Tv.setText(pm10Info);
    }
}
