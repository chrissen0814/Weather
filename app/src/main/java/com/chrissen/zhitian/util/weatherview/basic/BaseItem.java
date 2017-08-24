package com.chrissen.zhitian.util.weatherview.basic;

import android.content.res.Resources;
import android.graphics.Canvas;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public abstract class BaseItem {
    protected int width , height;
    protected Resources resources;

    public BaseItem(int width , int height , Resources resources){
        this.width = width;
        this.height = height;
        this.resources = resources;
    }

    public abstract void draw(Canvas canvas);
    public abstract void move();

}
