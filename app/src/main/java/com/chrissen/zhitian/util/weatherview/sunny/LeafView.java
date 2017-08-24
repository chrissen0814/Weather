package com.chrissen.zhitian.util.weatherview.sunny;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.chrissen.zhitian.util.weatherview.basic.ShowView;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class LeafView extends ShowView<LeafItem> {



    public LeafView(Context context) {
        super(context);
    }

    public LeafView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void beforeLogicLoop() {

    }

    @Override
    public LeafItem getItem(int width, int height, Resources resources) {
        return new LeafItem(width,height,resources);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
