package com.chrissen.zhitian.util.weatherview.snow;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.chrissen.zhitian.util.weatherview.basic.ShowView;


/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class SnowView extends ShowView<SnowItem> {


    public SnowView(Context context) {
        super(context);
    }

    public SnowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void beforeLogicLoop() {

    }

    @Override
    public SnowItem getItem(int width, int height, Resources resources) {
        return new SnowItem(width,height,resources);
    }

    @Override
    public int getCount() {
        return 30;
    }
}
