package com.ys.pa200.weight.point;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by admin on 2017/10/11 0011.
 */

public abstract class BasePoint extends Point
{

    public int index;
    public float startX;
    public float startY;
    public float endX;
    public float endY;
    public float height;
    public float scale;
    public BasePoint(int x, int y)
    {
        super(x, y);
    }

    //绘制测量图形
    abstract void drawMeasure(float startX, float startY, float endX, float endY, Canvas canvas, Paint paint);

    public void drawTag(float x,float y,Canvas canvas,Paint paint){
        canvas.drawLine(x-10,y,x+10,y,paint);
        canvas.drawLine(x,y-10,x,y+10,paint);
        canvas.drawText(index/2+"",x,y-30,paint);
    }

    public void startDraw(float startX, float startY, float endX, float endY,float height,float scale,Canvas canvas, Paint paint){
        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
        this.height = height;
        drawTag(this.startX,this.startY,canvas,paint);
        drawMeasure(this.startX,this.startY,this.endX,this.endY,canvas,paint);
        drawTag(this.endX,this.endY,canvas,paint);
        if (scale==0){
            this.scale = 1;
        }else{
            this.scale = scale;
        }
    }

    abstract float getMeasure();
}
