package com.chrissen.zhitian.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.model.bean.Weather;
import com.chrissen.zhitian.util.EnglishTextView;
import com.chrissen.zhitian.util.WeatherInfoHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class AqiFragment extends Fragment {

    private TextView levelTv , primaryPolluteTv , affectTv , pm25Tv , pm10Tv;
    private EnglishTextView updateTimeTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_aqi_weather,container,false);
        initLayout(view);
        return view;
    }

    private void initLayout(View view) {
        levelTv = (TextView) view.findViewById(R.id.aqi_quality_level_tv);
        primaryPolluteTv = (TextView) view.findViewById(R.id.aqi_primary_pollute_tv);
        affectTv = (TextView) view.findViewById(R.id.aqi_affect_tv);
        updateTimeTv = (EnglishTextView) view.findViewById(R.id.aqi_update_time_tv);
        pm25Tv = (TextView) view.findViewById(R.id.aqi_pm25_tv);
        pm10Tv = (TextView) view.findViewById(R.id.aqi_pm10_tv);
    }


    @Subscribe(priority = 2)
    public void setAirqualityWeatherInfo(Weather weather){
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
