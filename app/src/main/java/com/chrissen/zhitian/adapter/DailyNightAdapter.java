package com.chrissen.zhitian.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.model.bean.entity.Daily;
import com.chrissen.zhitian.util.EnglishTextView;
import com.chrissen.zhitian.util.WeatherInfoHelper;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class DailyNightAdapter extends RecyclerView.Adapter<DailyNightAdapter.NightViewHolder> {

    private List<Daily> dailyList;

    public DailyNightAdapter(List<Daily> dailyList){
        this.dailyList = dailyList;
    }


    @Override
    public NightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_weather,parent,false);
        return new NightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NightViewHolder holder, int position) {
        Daily daily = dailyList.get(position);
        holder.timeTv.setText(WeatherInfoHelper.getDay(daily.getDate()));
        holder.weekTv.setText(daily.getWeek());
        holder.tempTv.setText(daily.getNight().getTempHigh());
        int weatherImagePath = WeatherInfoHelper.getWeatherImagePath(daily.getNight().getImg());
        holder.weatherImageIv.setImageResource(weatherImagePath);
    }

    @Override
    public int getItemCount() {
        return dailyList.size();
    }

    class NightViewHolder extends RecyclerView.ViewHolder{

        private TextView weekTv;
        private EnglishTextView timeTv , tempTv ;
        private ImageView weatherImageIv;

        public NightViewHolder(View itemView) {
            super(itemView);
            itemView.setBackgroundColor(Color.TRANSPARENT);
            weekTv = (TextView) itemView.findViewById(R.id.item_daily_week_tv);
            timeTv = (EnglishTextView) itemView.findViewById(R.id.item_daily_time_tv);
            tempTv = (EnglishTextView) itemView.findViewById(R.id.item_daily_temp_tv);
            weatherImageIv = (ImageView) itemView.findViewById(R.id.item_daily_weather_image_iv);
            weekTv.setTextColor(Color.WHITE);
            timeTv.setTextColor(Color.WHITE);
            tempTv.setTextColor(Color.WHITE);
            weatherImageIv.setColorFilter(Color.WHITE);
        }
    }

}
