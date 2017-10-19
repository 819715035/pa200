package com.ys.pa200.weight.point;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by admin on 2017/10/11 0011.
 */

public class OvalMeasurePoint extends BasePoint
{
    private float angle;
    private float lengthX;
    private float lengthY;
    private float length;
    private Point endPoint = new Point();
    public OvalMeasurePoint(int x, int y,int pos)
    {
        super(x, y);
        index = pos;
    }

    @Override
    public void drawMeasure(float startX, float startY, float endX, float endY, Canvas canvas, Paint paint)
    {
        paint.setColor(Color.parseColor("#0000ff"));
        initData(startX,startY,endX,endY);
        canvas.save();
       // canvas.translate(startX+lengthX/2,startY+lengthY/2);
        canvas.drawRect(startX,startY,endPoint.x,endPoint.y,paint);
        canvas.rotate(angle);
        canvas.drawOval(startX,startY,endX,endY,paint);
        canvas.restore();//回滚到上一次状态
    }

    private void initData(float startX, float startY, float endX, float endY) {
        lengthX = endX - startX;
        lengthY = endY - startY;
        angle = (float) (Math.atan(lengthY/lengthX)*180/Math.PI);
        length = (float) Math.sqrt(Math.pow(lengthX,2)+Math.pow(lengthY,2));
        endPoint.x = (int) (startX-lengthX*length/lengthY);
        endPoint.y = (int) (startY-length);
    }

    @Override
    float getMeasure()
    {
        float x = Math.abs(endX-startX);
        float y = Math.abs(endY - startY);
        return x*y;
    }
}
