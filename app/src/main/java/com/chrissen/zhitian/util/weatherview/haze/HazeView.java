package com.chrissen.zhitian.util.weatherview.haze;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.chrissen.zhitian.util.weatherview.basic.ShowView;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class HazeView extends ShowView<HazeItem> {

    public HazeView(Context context) {
        super(context);
    }

    public HazeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void beforeLogicLoop() {

    }

    @Override
    public HazeItem getItem(int width, int height, Resources resources) {
        return new HazeItem(width,height,resources);
    }

    @Override
    public int getCount() {
        return 15;
    }
}
