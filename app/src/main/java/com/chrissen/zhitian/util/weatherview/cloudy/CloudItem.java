package com.chrissen.zhitian.util.weatherview.cloudy;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.util.weatherview.basic.BaseItem;

import java.util.Random;


/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class CloudItem extends BaseItem {

    private float opt;
    private int posX , posY;
    private Paint paint;
    private Random random;
    private Bitmap cloudBitmap;

    public CloudItem(int width, int height, Resources resources) {
        super(width, height, resources);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        random = new Random();
        cloudBitmap = BitmapFactory.decodeResource(resources, R.drawable.weatherview_cloud);
        loopInit();
    }

    private void loopInit() {
        posX = random.nextInt(width);
        posY = random.nextInt(height/2);
        opt = 5f + random.nextFloat();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(cloudBitmap,posX,posY,paint);
    }

    @Override
    public void move() {
        posX += opt;
        if(posX > width){
            loopInit();
        }
    }
}
