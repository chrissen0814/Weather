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

public class WeatherSmallReceiver extends AppWidgetProvider implements OnWeatherListener {

    private ComponentName componentName;
    private AppWidgetManager manager;
    private RemoteViews remoteViews;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_small);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context,R.id.widget_small_ll,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_small_ll,pi);
        componentName = new ComponentName(context,WeatherSmallReceiver.class);
        manager = AppWidgetManager.getInstance(context);
        DefaultCity defaultCity = DataSupport.find(DefaultCity.class,1);
        WeatherModel weatherModel = new WeatherModelImpl();
        weatherModel.loadLocationWeather(defaultCity,this);
    }



    @Override
    public void loadSuccess(Weather weather) {
        int weatherImagePath = WeatherInfoHelper.getWeatherImagePath(weather.getInfo().getImg());
        String temp = weather.getInfo().getTemp() + "℃";
        remoteViews.setImageViewResource(R.id.widget_small_image_iv,weatherImagePath);
        remoteViews.setTextViewText(R.id.widget_small_text_tv,temp);
        manager.updateAppWidget(componentName,remoteViews);
    }

    @Override
    public void loadFailure(String msg) {
        remoteViews.setImageViewResource(R.id.widget_small_image_iv,R.drawable.weather_nothing);
        remoteViews.setTextViewText(R.id.widget_small_text_tv,"N/A");
        manager.updateAppWidget(componentName,remoteViews);
    }
}
