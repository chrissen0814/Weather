package com.chrissen.zhitian.util.weatherview.rainy;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.chrissen.zhitian.util.weatherview.basic.BaseItem;

import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class RainItem extends BaseItem {

    private float opt;
    private int sizeX , sizeY ;
    private int startX , startY , stopX , stopY;
    private Paint paint;
    private Random random;

    public RainItem(int width, int height, Resources resources) {
        super(width, height, resources);
        init();
        loopInit();
    }

    private void loopInit() {
        sizeX = 10 + random.nextInt(10);
        sizeY = 20 + random.nextInt(20);
        startX = random.nextInt(width);
        startY = random.nextInt(height);
        opt = 0.2f + random.nextFloat();
        stopX = startX + sizeX;
        stopY = startY + sizeY;
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        random = new Random();
    }

    @Override
    public void draw(Canvas canvas) {
        Log.d(TAG, "drawView:" + startX + " " + startY + " " + stopX + " " + stopY);
        canvas.drawLine(startX,startY,stopX,stopY,paint);
    }

    @Override
    public void move() {
        startX += sizeX * opt;
        stopX += sizeX * opt;
        startY += sizeY * opt;
        stopY += sizeY * opt;

        if(startY > height){
            loopInit();
        }
    }
}
