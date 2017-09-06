package com.chrissen.zhitian.receiver;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.model.WeatherModel;
import com.chrissen.zhitian.model.WeatherModelImpl;
import com.chrissen.zhitian.model.bean.DefaultCity;
import com.chrissen.zhitian.model.bean.Weather;
import com.chrissen.zhitian.presenter.OnWeatherListener;
import com.chrissen.zhitian.util.WeatherInfoHelper;
import com.chrissen.zhitian.view.activity.MainActivity;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/8/25 0025.
 */

public class WeatherMiddleReceiver extends AppWidgetProvider implements OnWeatherListener {

    private ComponentName componentName;
    private AppWidgetManager manager;
    private RemoteViews remoteViews;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_middle);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context,R.id.widget_middle_ll,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_middle_ll,pi);
        componentName = new ComponentName(context,WeatherMiddleReceiver.class);
        manager = AppWidgetManager.getInstance(context);
        DefaultCity defaultCity = DataSupport.find(DefaultCity.class,1);
        WeatherModel weatherModel = new WeatherModelImpl();
        weatherModel.loadLocationWeather(defaultCity,this);

    }

    @Override
    public void loadSuccess(Weather weather) {
        int weatherImagePath = WeatherInfoHelper.getWeatherImagePath(weather.getInfo().getImg());
        String temp = weather.getInfo().getTemp() + "℃";
        String airquality = weather.getInfo().getAqi().getQuality();
        String humidity = weather.getInfo().getHumidity() + "%";
        String wind =weather.getInfo().getWindPower();
        remoteViews.setImageViewResource(R.id.widget_middle_image_iv,weatherImagePath);
        if(airquality.length() > 1){
            String air = airquality.substring(0,2) + "\n" + airquality.substring(2,airquality.length());
            remoteViews.setTextViewText(R.id.widget_middle_airquality_tv,air);
        }else {
            remoteViews.setTextViewText(R.id.widget_middle_airquality_tv,airquality);
        }
        remoteViews.setTextViewText(R.id.widget_middle_temp_tv,temp);
        remoteViews.setTextViewText(R.id.widget_middle_humidity_tv,humidity);
        remoteViews.setTextViewText(R.id.widget_middle_wind_tv,wind);
        manager.updateAppWidget(componentName,remoteViews);
    }

    @Override
    public void loadFailure(String msg) {
        remoteViews.setImageViewResource(R.id.widget_middle_image_iv,R.drawable.weather_nothing);
        remoteViews.setTextViewText(R.id.widget_middle_temp_tv,"N/A");
        remoteViews.setTextViewText(R.id.widget_middle_humidity_tv,"00");
        remoteViews.setTextViewText(R.id.widget_middle_wind_tv,"00");
        remoteViews.setTextViewText(R.id.widget_middle_airquality_tv,"00");
        manager.updateAppWidget(componentName,remoteViews);
    }
}
