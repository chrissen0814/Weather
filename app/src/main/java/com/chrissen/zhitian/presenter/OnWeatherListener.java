package com.chrissen.zhitian.presenter;

import com.chrissen.zhitian.model.bean.Weather;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public interface OnWeatherListener {

    void loadSuccess(Weather weather);
    void loadFailure(String msg);

}
