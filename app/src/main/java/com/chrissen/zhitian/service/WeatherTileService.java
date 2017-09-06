package com.chrissen.zhitian.service;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

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

@RequiresApi(api = Build.VERSION_CODES.N)
public class WeatherTileService extends TileService implements OnWeatherListener{

    @Override
    public void onStartListening() {
        DefaultCity defaultCity = DataSupport.find(DefaultCity.class,1);
        if(defaultCity != null){
            WeatherModel weatherModel = new WeatherModelImpl();
            weatherModel.loadLocationWeather(defaultCity,this);
        }else {
            Toast.makeText(this, "默认城市不存在！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick() {
        startActivityAndCollapse(new Intent(this,MainActivity.class));
    }

    @Override
    public void loadSuccess(Weather weather) {
        int weatherIconPath = WeatherInfoHelper.getWeatherImagePath(weather.getInfo().getImg());
        Icon icon = Icon.createWithResource(this,weatherIconPath);
        String temp = weather.getInfo().getTemp() + "℃";
        getQsTile().setState(Tile.STATE_INACTIVE);
        getQsTile().setIcon(icon);
        getQsTile().setLabel(temp);
        getQsTile().setState(Tile.STATE_ACTIVE);
        getQsTile().updateTile();
    }

    @Override
    public void loadFailure(String msg) {
        Icon icon = Icon.createWithResource(this, R.drawable.weather_nothing);
        String info = "Nothing";
        getQsTile().setState(Tile.STATE_INACTIVE);
        getQsTile().setIcon(icon);
        getQsTile().setLabel(info);
        getQsTile().setState(Tile.STATE_ACTIVE);
        getQsTile().updateTile();
    }
}
