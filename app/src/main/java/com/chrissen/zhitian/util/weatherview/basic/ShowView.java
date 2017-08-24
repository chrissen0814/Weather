package com.chrissen.zhitian.util.weatherview.basic;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public abstract class ShowView<T extends BaseItem> extends BaseView {

    protected List<T> itemList = new ArrayList<>();
    protected int size = 1;

    public ShowView(Context context) {
        super(context);
    }

    public ShowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void drawSub(Canvas canvas) {
        for(T t : itemList){
            t.draw(canvas);
        }
    }

    @Override
    public void baseInit(int width, int height) {
        size = getCount();
        Resources resources = getResources();
        for(int i = 0 ; i < size ; i++){
            itemList.add(getItem(width,height,resources));
        }

    }

    @Override
    public void logic() {
        beforeLogicLoop();
        for(T t : itemList){
            t.move();
        }
    }

    public abstract void beforeLogicLoop();
    public abstract T getItem(int width , int height , Resources resources);
    public abstract int getCount();

}
