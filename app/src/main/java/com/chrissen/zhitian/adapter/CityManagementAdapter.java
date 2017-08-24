package com.chrissen.zhitian.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrissen.zhitian.MyApplication;
import com.chrissen.zhitian.R;
import com.chrissen.zhitian.model.bean.SavedCity;
import com.chrissen.zhitian.model.bean.Weather;
import com.chrissen.zhitian.util.Api;
import com.chrissen.zhitian.util.EnglishTextView;
import com.chrissen.zhitian.util.RetrofitFactory;
import com.chrissen.zhitian.util.WeatherInfoHelper;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/22 0022.
 */

public class CityManagementAdapter extends RecyclerView.Adapter<CityManagementAdapter.CityViewHolder> {

    private List<SavedCity> savedCityList;

    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;
    public CityManagementAdapter(List<SavedCity> savedCityList){
        this.savedCityList = savedCityList;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_management,parent,false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder, int position) {
        SavedCity savedCity = savedCityList.get(position);
        holder.cityName.setText(savedCity.getCityName());
        setWeatherInfo(savedCity,holder);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v,holder.getAdapterPosition());
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickListener.onItemLongClick(v,holder.getAdapterPosition());
                return true;
            }
        });
    }

    private void setWeatherInfo(SavedCity savedCity , final CityViewHolder holder){
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
                        int weatherColor = WeatherInfoHelper.getWeatherColor(weather.getInfo().getImg());
                        String temp = weather.getInfo().getTemp() + MyApplication.getContext().getResources().getString(R.string.celsius);
                        int weatherImagePath = WeatherInfoHelper.getWeatherImagePath(weather.getInfo().getImg());
                        holder.cardView.setCardBackgroundColor(weatherColor);
                        holder.tempTv.setText(temp);
                        holder.weatherImageIv.setImageResource(weatherImagePath);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return savedCityList.size();
    }

    class CityViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView cityName;
        private EnglishTextView tempTv;
        private ImageView weatherImageIv;
        private CardView cardView;

        public CityViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            cardView = (CardView) itemView.findViewById(R.id.city_management_cv);
            cityName = (TextView) itemView.findViewById(R.id.city_management_city_name_tv);
            tempTv = (EnglishTextView) itemView.findViewById(R.id.city_management_weather_temp_tv);
            weatherImageIv = (ImageView) itemView.findViewById(R.id.city_management_weather_image_iv);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view , int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view , int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        longClickListener = listener;
    }

}
