package com.chrissen.zhitian.util;

import com.chrissen.zhitian.model.bean.CityList;
import com.chrissen.zhitian.model.bean.Weather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/4/5.
 */

public interface ApiInterface {

    @GET("city")
    Observable<CityList> getCityList(@Query("appkey") String appkey);

    @GET("query")
    Observable<Weather> getWeather(@Query("appkey") String appkey , @Query("citycode") String cityCode);


}
