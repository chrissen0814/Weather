package com.chrissen.zhitian.presenter;

import android.content.Context;

import com.chrissen.zhitian.model.LocationModel;
import com.chrissen.zhitian.model.LocationModelImpl;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class LocationPresenterImpl implements LocationPresenter {
    private LocationModel locationModel;

    public LocationPresenterImpl(){
        locationModel = new LocationModelImpl();
    }

    @Override
    public void loadLocation(Context context) {
        locationModel.accessLocation(context);
    }

}
