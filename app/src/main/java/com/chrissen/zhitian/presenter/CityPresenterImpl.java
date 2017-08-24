package com.chrissen.zhitian.presenter;

import com.chrissen.zhitian.model.CityModel;
import com.chrissen.zhitian.model.CityModelImpl;

/**
 * Created by Administrator on 2017/8/20 0020.
 */

public class CityPresenterImpl implements CityPresenter {

    private CityModel cityModel;


    public CityPresenterImpl(){
        cityModel = new CityModelImpl();
    }

    @Override
    public void saveCityList() {
        cityModel.loadCityList();
    }
}
