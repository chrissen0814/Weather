package com.chrissen.zhitian.adapter;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.model.bean.DefaultCity;
import com.chrissen.zhitian.model.bean.SavedCity;
import com.chrissen.zhitian.model.bean.Weather;
import com.chrissen.zhitian.util.Api;
import com.chrissen.zhitian.util.EnglishTextView;
import com.chrissen.zhitian.util.PreferencesLoader;
import com.chrissen.zhitian.util.RetrofitFactory;
import com.chrissen.zhitian.util.WeatherInfoHelper;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/22 0022.
 */

public class CityManagementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "CityManagementAdapter";

    private static final int HEADER = 0;
    private static final int NORMAL = 1;


    private List<SavedCity> savedCityList;
    private Fragment fragment;
    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;

    public CityManagementAdapter(Fragment fragment ,List<SavedCity> savedCityList){
        this.fragment = fragment;
        this.savedCityList = savedCityList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == NORMAL){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_management,parent,false);
            return new CityViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_management_default_city,parent,false);
            return new DefaultCityViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return HEADER;
        }else {
            return NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(position == 0){
            final DefaultCity defaultCity = DataSupport.find(DefaultCity.class,1);
            ((DefaultCityViewHolder)holder).cityName.setText(defaultCity.getCityName());
            int temp = PreferencesLoader.getInt(PreferencesLoader.DEFAULT_CITY_TEMP,0);
            int img = PreferencesLoader.getInt(PreferencesLoader.DEFAULT_CITY_IMG,0);
            ((DefaultCityViewHolder)holder).tempTv.setText(String.valueOf(temp));
            int color = WeatherInfoHelper.getWeatherColor(String.valueOf(img));
            int weatherImagePath = WeatherInfoHelper.getWeatherImagePath(String.valueOf(img));
            ((DefaultCityViewHolder)holder).cardView.setCardBackgroundColor(color);
            ((DefaultCityViewHolder)holder).weatherImageIv.setImageResource(weatherImagePath);
            ((DefaultCityViewHolder)holder).view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(defaultCity);
                    ((DrawerLayout)fragment.getActivity().findViewById(R.id.main_drawer_layout))
                            .closeDrawer(Gravity.RIGHT);
                }
            });
            ((DefaultCityViewHolder)holder).view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    RelativeLayout cityManagementRl = (RelativeLayout) fragment.getView().findViewById(R.id.city_management_rl);
                    Snackbar.make(cityManagementRl,"默认城市不能删除",Snackbar.LENGTH_SHORT)
                            .show();
                    return true;
                }
            });
        }else {
            SavedCity savedCity = savedCityList.get(position-1);
            ((CityViewHolder)holder).cityName.setText(savedCity.getCityName());
            setWeatherInfo(savedCity,((CityViewHolder)holder));
            ((CityViewHolder)holder).view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v,holder.getAdapterPosition());
                }
            });
            ((CityViewHolder)holder).view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longClickListener.onItemLongClick(v,holder.getAdapterPosition());
                    return true;
                }
            });
        }

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
                        int color = WeatherInfoHelper.getWeatherColor(weather.getInfo().getImg());
                        holder.cardView.setCardBackgroundColor(color);
                        holder.tempTv.setText(weather.getInfo().getTemp());
                        int weatherImagePath = WeatherInfoHelper.getWeatherImagePath(weather.getInfo().getImg());
                        holder.weatherImageIv.setImageResource(weatherImagePath);
                    }

                    @Override
                    public void onError(Throwable e) {
                        holder.tempTv.setText("00");
                        holder.weatherImageIv.setImageResource(R.drawable.weather_nothing);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    public int getItemCount() {
        return savedCityList.size() + 1;
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

    class DefaultCityViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView cityName;
        private EnglishTextView tempTv;
        private ImageView weatherImageIv;
        private CardView cardView;

        public DefaultCityViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            cardView = (CardView) itemView.findViewById(R.id.city_management_default_cv);
            cityName = (TextView) itemView.findViewById(R.id.city_management_default_city_name_tv);
            tempTv = (EnglishTextView) itemView.findViewById(R.id.city_management_default_weather_temp_tv);
            weatherImageIv = (ImageView) itemView.findViewById(R.id.city_management_default_weather_image_iv);
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
