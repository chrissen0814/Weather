package com.chrissen.zhitian.view.fragment.weather_pager.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chrissen.zhitian.model.bean.Weather;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Administrator on 2017/9/6 0006.
 */

public abstract class BaseSubscribeFragment extends Fragment {

    protected Activity mActivity;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity)context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(),container,false);
        initView(view,savedInstanceState);
        return view;
    }

    @Subscribe
    public void setWeatherInfo(Weather weather){
        setWeather(weather);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View view , Bundle savedInstanceState);

    protected abstract void setWeather(Weather weather);

}
