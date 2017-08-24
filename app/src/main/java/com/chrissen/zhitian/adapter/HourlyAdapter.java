package com.chrissen.zhitian.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.model.bean.entity.Hourly;
import com.chrissen.zhitian.util.EnglishTextView;
import com.chrissen.zhitian.util.WeatherInfoHelper;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder> {
    private List<Hourly> hourlyList;

    public HourlyAdapter(List<Hourly> hourlyList){
        this.hourlyList = hourlyList;
    }


    @Override
    public HourlyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hourly_weather,parent,false);
        return new HourlyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HourlyViewHolder holder, int position) {
        Hourly hourly = hourlyList.get(position);
        holder.timeTv.setText(hourly.getTime());
        holder.tempTv.setText(hourly.getTemp());
        int weatherImagePath = WeatherInfoHelper.getWeatherImagePath(hourly.getImg());
        holder.weatherImageIv.setImageResource(weatherImagePath);
    }

    @Override
    public int getItemCount() {
        return hourlyList.size();
    }

    class HourlyViewHolder extends RecyclerView.ViewHolder {
        private EnglishTextView timeTv , tempTv;
        private ImageView weatherImageIv;
        public HourlyViewHolder(View itemView) {
            super(itemView);
            timeTv = (EnglishTextView) itemView.findViewById(R.id.item_hourly_time_tv);
            tempTv = (EnglishTextView) itemView.findViewById(R.id.item_hourly_temp_tv);
            weatherImageIv = (ImageView) itemView.findViewById(R.id.item_hourly_weather_image_iv);
        }
    }

}
