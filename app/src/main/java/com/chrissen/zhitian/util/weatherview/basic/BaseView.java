package com.chrissen.zhitian.util.weatherview.basic;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public abstract class BaseView extends View {

    private static final String TAG = "BaseView";
    private static final int sleepTime = 30;
    private RefreshThread refreshThread = null;

    public BaseView(Context context) {
        super(context);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void drawSub(Canvas canvas);

    public abstract void baseInit(int width , int height);

    public abstract void logic();

    @Override
    protected void onDraw(Canvas canvas) {
        if(refreshThread == null){
            refreshThread = new RefreshThread();
            refreshThread.start();
        }else {
            drawSub(canvas);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        running = false;
        super.onDetachedFromWindow();
    }

    private boolean running = true;
    private class RefreshThread extends Thread{
        @Override
        public void run() {
            baseInit(getWidth(),getHeight());
            while (running){
                try{
                    logic();
                    postInvalidate();
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Log.d(TAG, e.toString());
                }
            }
        }
    }

}
