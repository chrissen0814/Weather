package com.chrissen.zhitian.util.weatherview.snow;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.chrissen.zhitian.util.weatherview.basic.BaseItem;

import java.util.Random;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class SnowItem extends BaseItem {

    private Paint paint;
    private float snowRadius;
    private Random random;
    private float opt;

    private int dx;
    private int posX,posY;
    private int distance = 0; /** 方向 1 左边飘，2 垂直，3 右边 */
    private int finalDx = 0;

    public SnowItem(int width, int height, Resources resources) {
        super(width, height, resources);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        random = new Random();
        snowRadius = random.nextInt(10) + 5;
        loopInit();
    }

    private void loopInit() {
        posX = random.nextInt(width);
        posY = random.nextInt(height/2);
        opt = 0.5f + random.nextFloat();
        dx  = 5 + random.nextInt(10);

        distance = random.nextInt(10);
        int dxRand = 4;
        switch(distance){
            case 1:
            case 2:
            case 3:
                finalDx += -random.nextInt(dxRand);
                break;
            case 4:
            case 5:
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                finalDx += random.nextInt(dxRand);
                break;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(posX,posY,snowRadius,paint);
    }

    @Override
    public void move() {
        posX += finalDx;
        posY += dx * opt;
        if(posY > height){
            loopInit();
        }
    }
}
