package com.chrissen.zhitian.util.weatherview.sand;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.chrissen.zhitian.util.weatherview.basic.ShowView;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class SandView extends ShowView<SandItem> {


    public SandView(Context context) {
        super(context);
    }

    public SandView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void beforeLogicLoop() {

    }

    @Override
    public SandItem getItem(int width, int height, Resources resources) {
        return new SandItem(width,height,resources);
    }

    @Override
    public int getCount() {
        return 10;
    }
}
