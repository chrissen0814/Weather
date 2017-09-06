package com.chrissen.zhitian.view.fragment.weather_pager.weather;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.adapter.DailyDayAdapter;
import com.chrissen.zhitian.adapter.DailyNightAdapter;
import com.chrissen.zhitian.model.bean.Weather;
import com.chrissen.zhitian.view.fragment.weather_pager.base.BaseSubscribeFragment;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class DailyFragment extends BaseSubscribeFragment {

    private boolean isShownBack;
    private RecyclerView dailyDayRv , dailyNightRv;
    private DailyDayAdapter dailyDayAdapter;
    private DailyNightAdapter dailyNightAdapter;
    private RelativeLayout swtciRl;
    private FrameLayout dailyDayFl , dailyNightFl;
    private TextView titleTv;
    private Switch dayNightSwitch;

    @Override
    protected int getLayoutId() {
        return R.layout.pager_daily_weather;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        dayNightSwitch = (Switch) view.findViewById(R.id.daily_weather_switch_tb);
        titleTv = (TextView) view.findViewById(R.id.daily_weather_title_tv);
        swtciRl = (RelativeLayout) view.findViewById(R.id.daily_weather_swtch_rl);
        dailyDayFl = (FrameLayout) view.findViewById(R.id.daily_day_fl);
        dailyNightFl = (FrameLayout) view.findViewById(R.id.daily_night_fl);
        dailyDayRv = (RecyclerView) view.findViewById(R.id.weather_daily_day_weather_rv);
        dailyNightRv = (RecyclerView) view.findViewById(R.id.weather_daily_night_weather_rv);
    }

    @Override
    protected void setWeather(Weather weather) {
        dailyDayRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        dailyDayAdapter = new DailyDayAdapter(weather.getInfo().getDailyList());
        dailyDayRv.setAdapter(dailyDayAdapter);
        dailyNightRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        dailyNightAdapter = new DailyNightAdapter(weather.getInfo().getDailyList());
        dailyNightRv.setAdapter(dailyNightAdapter);
        setFlipAnimation();
    }

    private void setFlipAnimation() {
        final AnimatorSet rightOutSet = (AnimatorSet)AnimatorInflater.loadAnimator(getActivity(),R.animator.card_right_out);
        final AnimatorSet leftInSet = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(),R.animator.card_left_in);
        rightOutSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                dayNightSwitch.setClickable(false);
            }
        });
        leftInSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                dayNightSwitch.setClickable(true);
            }
        });
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density*distance;
        dailyDayFl.setCameraDistance(scale);
        dailyNightFl.setCameraDistance(scale);
        dayNightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isShownBack) {
                    rightOutSet.setTarget(dailyDayFl);
                    leftInSet.setTarget(dailyNightFl);
                    rightOutSet.start();
                    leftInSet.start();
                    swtciRl.setBackgroundColor(getResources().getColor(R.color.card_daily_night));
                    titleTv.setTextColor(Color.WHITE);
                    isShownBack = true;
                }else {
                    rightOutSet.setTarget(dailyNightFl);
                    leftInSet.setTarget(dailyDayFl);
                    rightOutSet.start();
                    leftInSet.start();
                    isShownBack = false;
                    swtciRl.setBackgroundColor(Color.WHITE);
                    titleTv.setTextColor(Color.BLACK);
                }
            }
        });
    }

}
