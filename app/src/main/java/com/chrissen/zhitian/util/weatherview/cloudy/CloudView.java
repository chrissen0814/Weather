package com.chrissen.zhitian.util.weatherview.cloudy;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.chrissen.zhitian.util.weatherview.basic.ShowView;


/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class CloudView extends ShowView<CloudItem> {

    public CloudView(Context context) {
        super(context);
    }

    public CloudView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void beforeLogicLoop() {

    }

    @Override
    public CloudItem getItem(int width, int height, Resources resources) {
        return new CloudItem(width,height,resources);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
