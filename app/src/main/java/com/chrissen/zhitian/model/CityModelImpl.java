package com.chrissen.zhitian.model;

import android.util.Log;

import com.chrissen.zhitian.model.bean.CityList;
import com.chrissen.zhitian.util.Api;
import com.chrissen.zhitian.util.RetrofitFactory;

import org.litepal.crud.DataSupport;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/20 0020.
 */

public class CityModelImpl implements CityModel {
    private static final String TAG = "CityModelImpl";

    @Override
    public void loadCityList() {
        new RetrofitFactory(Api.JISU_URL).getApiInterface()
                .getCityList(Api.JISU_APP_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<CityList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CityList cityList) {
                        Log.i(TAG, "onNext: " + "start");
                        DataSupport.saveAll(cityList.getCityList());
                        Log.i(TAG, "onNext: " + "end");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
