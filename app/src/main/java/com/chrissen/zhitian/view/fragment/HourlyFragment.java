package com.chrissen.zhitian.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.adapter.HourlyAdapter;
import com.chrissen.zhitian.model.bean.Weather;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class HourlyFragment extends Fragment {

    private RecyclerView hourlyRv;
    private HourlyAdapter hourlyAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_hourly_weather,container,false);
        hourlyRv = (RecyclerView) view.findViewById(R.id.weather_hourly_rv);
        return view;
    }

    @Subscribe(priority = 4)
    public void setHourlyWeatherInfo(Weather weather){
        hourlyRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        hourlyAdapter = new HourlyAdapter(weather.getInfo().getHourlyList());
        hourlyRv.setAdapter(hourlyAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
