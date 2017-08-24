package com.chrissen.zhitian.adapter;

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
 * Created by Administrator on 2017/8/21 0021.
 */

public class DailyDayAdapter extends RecyclerView.Adapter<DailyDayAdapter.DailyViewHolder> {

    private List<Daily> dailyList;

    public DailyDayAdapter(List<Daily> dailyList){
        this.dailyList = dailyList;
    }

    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_weather,parent,false);
        return new DailyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DailyViewHolder holder, int position) {
        Daily daily = dailyList.get(position);
        holder.timeTv.setText(WeatherInfoHelper.getDay(daily.getDate()));
        holder.weekTv.setText(daily.getWeek());
        holder.tempTv.setText(daily.getDay().getTempHigh());
        int weatherImagePath = WeatherInfoHelper.getWeatherImagePath(daily.getDay().getImg());
        holder.weatherImageIv.setImageResource(weatherImagePath);

    }

    @Override
    public int getItemCount() {
        return dailyList.size();
    }

    class DailyViewHolder extends RecyclerView.ViewHolder {
        private TextView weekTv;
        private EnglishTextView timeTv , tempTv ;
        private ImageView weatherImageIv;
        public DailyViewHolder(View itemView) {
            super(itemView);
            weekTv = (TextView) itemView.findViewById(R.id.item_daily_week_tv);
            timeTv = (EnglishTextView) itemView.findViewById(R.id.item_daily_time_tv);
            tempTv = (EnglishTextView) itemView.findViewById(R.id.item_daily_temp_tv);
            weatherImageIv = (ImageView) itemView.findViewById(R.id.item_daily_weather_image_iv);
        }
    }

}
