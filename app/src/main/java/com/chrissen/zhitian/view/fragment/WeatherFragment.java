package com.chrissen.zhitian.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.adapter.WeatherPagerAdapter;
import com.chrissen.zhitian.model.bean.DefaultCity;
import com.chrissen.zhitian.model.bean.SavedCity;
import com.chrissen.zhitian.model.bean.Weather;
import com.chrissen.zhitian.presenter.WeatherPresenter;
import com.chrissen.zhitian.presenter.WeatherPresenterImpl;
import com.chrissen.zhitian.util.PreferencesLoader;
import com.chrissen.zhitian.util.WeatherInfoHelper;
import com.chrissen.zhitian.util.weatherview.cloudy.CloudView;
import com.chrissen.zhitian.util.weatherview.foggy.FoggyView;
import com.chrissen.zhitian.util.weatherview.haze.HazeView;
import com.chrissen.zhitian.util.weatherview.rainy.RainView;
import com.chrissen.zhitian.util.weatherview.sand.SandView;
import com.chrissen.zhitian.util.weatherview.snow.SnowView;
import com.chrissen.zhitian.util.weatherview.sunny.LeafView;
import com.chrissen.zhitian.view.WeatherView;
import com.chrissen.zhitian.view.fragment.component.SupportFragment;
import com.chrissen.zhitian.view.fragment.weather_pager.weather.AqiFragment;
import com.chrissen.zhitian.view.fragment.weather_pager.weather.SummaryFragment;
import com.chrissen.zhitian.view.fragment.weather_pager.weather.DailyFragment;
import com.chrissen.zhitian.view.fragment.weather_pager.weather.HourlyFragment;
import com.chrissen.zhitian.view.fragment.weather_pager.weather.IndexFragment;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class WeatherFragment extends Fragment implements WeatherView {
    private static final String TAG = "WeatherFragment";
    private WeatherPresenter presenter;
    private RelativeLayout weatherviewContainerRl;
    private ImageButton cityManagementIb , warningIb, toStartIb , toEndIb;
    private ViewPager weatherViewPager;
    private WeatherPagerAdapter adapter;
    private List<Fragment> fragmentList = new ArrayList<>();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        presenter = new WeatherPresenterImpl(this);
        SummaryFragment dwf = new SummaryFragment();
        HourlyFragment hourlyFragment = new HourlyFragment();
        DailyFragment dailyFragment = new DailyFragment();
        AqiFragment aqiFragment = new AqiFragment();
        IndexFragment indexFragment = new IndexFragment();
        SupportFragment supportFragment = new SupportFragment();
        fragmentList.add(dwf);
        fragmentList.add(hourlyFragment);
        fragmentList.add(dailyFragment);
        fragmentList.add(aqiFragment);
        fragmentList.add(indexFragment);
        fragmentList.add(supportFragment);
        adapter = new WeatherPagerAdapter(getFragmentManager(),fragmentList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather,container,false);
        weatherviewContainerRl = (RelativeLayout) view.findViewById(R.id.weather_weatherview_container_rl);
        cityManagementIb= (ImageButton) view.findViewById(R.id.weather_city_management_ib);
        cityManagementIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.main_drawer_layout);
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
        warningIb = (ImageButton) view.findViewById(R.id.weather_warning_ib);
        warningIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.main_drawer_layout);
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        weatherViewPager = (ViewPager) view.findViewById(R.id.weather_view_pager);
        weatherViewPager.setAdapter(adapter);
        weatherViewPager.setOffscreenPageLimit(5);
        toStartIb = (ImageButton) view.findViewById(R.id.weather_to_start_ib);
        toStartIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherViewPager.setCurrentItem(0);
            }
        });
        toEndIb = (ImageButton) view.findViewById(R.id.weather_to_end_ib);
        toEndIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherViewPager.setCurrentItem(fragmentList.size()-2);
            }
        });
        if (weatherViewPager.getCurrentItem() == 0) {
            toStartIb.setVisibility(View.INVISIBLE);
        }
        weatherViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position > 0) {
                    toStartIb.setVisibility(View.VISIBLE);
                }else {
                    toStartIb.setVisibility(View.INVISIBLE);
                }
                if (position == fragmentList.size() - 2 || position == fragmentList.size() - 1) {
                    toEndIb.setVisibility(View.INVISIBLE);
                }else {
                    toEndIb.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    @Subscribe
    public void getLocation(DefaultCity defaultCity){
        presenter.getLocationWeather(defaultCity);
    }

    @Subscribe
    public void getCity(SavedCity savedCity){
        presenter.getWeather(savedCity);
    }

    @Override
    public void showWeather(Weather weather) {
        setWeatherBackground(weather);
        EventBus.getDefault().post(weather);
    }

    private void setWeatherBackground(Weather weather) {
        String img = weather.getInfo().getImg();
        int weatherColor = WeatherInfoHelper.getWeatherColor(img);
        int weatherType = WeatherInfoHelper.getWeatherType(img);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        weatherviewContainerRl.removeAllViews();
        switch (weatherType){
            case R.string.weatherview_sunny:
                LeafView leafView = new LeafView(getActivity());
                leafView.setBackgroundColor(weatherColor);
                weatherviewContainerRl.addView(leafView,params);
                break;
            case R.string.weatherview_cloudy:
                CloudView cloudView = new CloudView(getActivity());
                cloudView.setBackgroundColor(weatherColor);
                weatherviewContainerRl.addView(cloudView,params);
                break;
            case R.string.weatherview_rainy:
                RainView rainView = new RainView(getActivity());
                rainView.setBackgroundColor(weatherColor);
                weatherviewContainerRl.addView(rainView,params);
                break;
            case R.string.weatherview_snowy:
                SnowView snowView = new SnowView(getActivity());
                snowView.setBackgroundColor(weatherColor);
                weatherviewContainerRl.addView(snowView,params);
                break;
            case R.string.weatherview_foggy:
                FoggyView foggyView = new FoggyView(getActivity());
                foggyView.setBackgroundColor(weatherColor);
                weatherviewContainerRl.addView(foggyView,params);
                break;
            case R.string.weatherview_sand:
                SandView sandView = new SandView(getActivity());
                sandView.setBackgroundColor(weatherColor);
                weatherviewContainerRl.addView(sandView,params);
                break;
            case R.string.weatherview_hazy:
                HazeView hazeView = new HazeView(getActivity());
                hazeView.setBackgroundColor(weatherColor);
                weatherviewContainerRl.addView(hazeView,params);
                break;
        }
        PreferencesLoader.putInt(PreferencesLoader.WEATHER_COLOR,weatherColor);
    }

    @Override
    public void showErrorInfo(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("Weather");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("Weather");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
