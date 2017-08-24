package com.chrissen.zhitian.util.weatherview.foggy;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.chrissen.zhitian.util.weatherview.basic.BaseItem;

import java.util.Random;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class FogItem extends BaseItem {

    private float opt;
    private Paint paint;
    private Random random;
    private int length , lineWidth;
    private int startX , startY , stopX , stopY;

    public FogItem(int width, int height, Resources resources) {
        super(width, height, resources);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        random = new Random();
        lineWidth = random.nextInt(40) + 20;
        paint.setStrokeWidth(lineWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        loopInit();
    }

    private void loopInit() {
        startX = random.nextInt(width/2);
        startY = random.nextInt(height*3/4);
        length = 300 + random.nextInt(500);
        stopX = startX + length;
        stopY = startY;
        opt = 5f + random.nextFloat();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(startX,startY,stopX,stopY,paint);
    }

    @Override
    public void move() {
        startX += opt;
        stopX += opt;
        if(stopX - length > width){
            loopInit();
        }
    }
}
