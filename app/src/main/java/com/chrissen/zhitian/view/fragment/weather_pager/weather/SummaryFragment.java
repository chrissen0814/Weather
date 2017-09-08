package com.chrissen.zhitian.view.fragment.weather_pager.weather;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.model.bean.DefaultCity;
import com.chrissen.zhitian.model.bean.SavedCity;
import com.chrissen.zhitian.model.bean.Weather;
import com.chrissen.zhitian.util.EnglishTextView;
import com.chrissen.zhitian.util.WeatherInfoHelper;
import com.chrissen.zhitian.view.fragment.weather_pager.base.BaseSubscribeFragment;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class SummaryFragment extends BaseSubscribeFragment {
    private static final String TAG = "SummaryFragment";

    private EnglishTextView  updateTimeTv ,tempTv , tempMaxMinTv , humidityTv ,sunTv;
    private TextView cityNameTv , forecastHourlyTv,forecastDayTv , airQualityTv, windTv, weatherTextTv  ;
    private ImageView airqualityIv , weatherTextIv;


    @Override
    protected int getLayoutId() {
        return R.layout.pager_default_weather;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        cityNameTv = (TextView) view.findViewById(R.id.weather_city_name_tv);
        updateTimeTv = (EnglishTextView) view.findViewById(R.id.weather_update_time_tv);
        airQualityTv = (TextView) view.findViewById(R.id.weather_airquality_tv);
        forecastHourlyTv = (TextView) view.findViewById(R.id.weather_forecast_hourly_tips_tv);
        forecastDayTv = (TextView) view.findViewById(R.id.weather_forecast_day_tips_tv);
        windTv = (TextView) view.findViewById(R.id.weather_wind_tv);
        weatherTextTv = (TextView) view.findViewById(R.id.weather_weather_text_tv);
        humidityTv = (EnglishTextView) view.findViewById(R.id.weather_humidity_tv);
        tempTv = (EnglishTextView) view.findViewById(R.id.weather_temp_tv);
        tempMaxMinTv = (EnglishTextView) view.findViewById(R.id.weather_temp_max_min_tv);
        sunTv = (EnglishTextView) view.findViewById(R.id.weather_sun_tv);
        airqualityIv = (ImageView) view.findViewById(R.id.weather_airquality_image_iv);
        weatherTextIv = (ImageView) view.findViewById(R.id.weather_weather_text_image_iv);
    }

    @Override
    protected void setWeather(Weather weather) {
        DefaultCity defaultCity = DataSupport.find(DefaultCity.class,1);
        String parentCityName = defaultCity.getParentCityName();
        if(weather.getInfo().getCityName().equals(parentCityName.substring(0,parentCityName.length()-1))
                || weather.getInfo().getCityName().equals(defaultCity.getCityName())){
            cityNameTv.setText(DataSupport.find(DefaultCity.class,1).getCityName());
        }else {
            String cityName = weather.getInfo().getCityName();
            List<SavedCity> savedCityList = DataSupport.findAll(SavedCity.class);
            for(SavedCity savedCity : savedCityList){
                if(savedCity.getCityName().equals(cityName)){
                    cityNameTv.setText(cityName);
                    break;
                }
            }
        }
        String updateTime = WeatherInfoHelper.getUpdateTime(weather.getInfo().getUpdateTime());
        String tempInfo = weather.getInfo().getTemp() + getResources().getString(R.string.celsius);
        String tempLow = "L:" + weather.getInfo().getTempLow();
        String tempHigh = "H:" + weather.getInfo().getTempHigh();
        int weatherImagePath = WeatherInfoHelper.getWeatherImagePath(weather.getInfo().getImg());
        String humidityInfo = weather.getInfo().getHumidity() + "%";
        String windInfo = weather.getInfo().getWindDirect() + "\n" +  weather.getInfo().getWindPower();
        String sunInfo =weather.getInfo().getDailyList().get(0).getSunrise()
                +"\n" + weather.getInfo().getDailyList().get(0).getSunset();
        String airquality = weather.getInfo().getAqi().getQuality();
        int airqualityColor = WeatherInfoHelper.getAirqualityColor(airquality);
        updateTimeTv.setText(updateTime);
        tempTv.setText(tempInfo);
        forecastHourlyTv.setText(WeatherInfoHelper.getHourlyWeatherTipsInfo(weather.getInfo().getHourlyList()));
        forecastDayTv.setText(WeatherInfoHelper.getDayWeatherTipsInfo(weather.getInfo().getDailyList()));
        tempMaxMinTv.setText(tempLow + "\n" + tempHigh);
        if(airquality.length() > 1){
            airQualityTv.setText(airquality.substring(0,2) + "\n" + airquality.substring(2,airquality.length()));
        }else {
            airQualityTv.setText(airquality);
        }
        airQualityTv.setTextColor(airqualityColor);
        airqualityIv.setColorFilter(airqualityColor);
        weatherTextIv.setImageResource(weatherImagePath);
        weatherTextTv.setText(weather.getInfo().getWeather());
        windTv.setText(windInfo);
        humidityTv.setText(humidityInfo);
        sunTv.setText(sunInfo);
    }
}
