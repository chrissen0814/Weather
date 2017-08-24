package com.chrissen.zhitian.util.weatherview.foggy;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.chrissen.zhitian.util.weatherview.basic.ShowView;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class FoggyView extends ShowView<FogItem> {


    public FoggyView(Context context) {
        super(context);
    }

    public FoggyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void beforeLogicLoop() {

    }

    @Override
    public FogItem getItem(int width, int height, Resources resources) {
        return new FogItem(width,height,resources) ;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
