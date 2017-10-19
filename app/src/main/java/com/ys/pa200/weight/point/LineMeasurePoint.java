package com.ys.pa200.weight.point;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by admin on 2017/10/11 0011.
 */

public class LineMeasurePoint extends BasePoint
{
    public LineMeasurePoint(int x, int y,int pos)
    {
        super(x, y);
        index = pos;
    }

    @Override
    public void drawMeasure(float startX, float startY, float endX, float endY, Canvas canvas, Paint paint)
    {
        canvas.drawLine(startX,startY,endX,endY,paint);
        canvas.drawText("距离："+getMeasure(),endX+20,endY+30,paint);
    }

    @Override
    float getMeasure()
    {
        float x = (float) Math.pow((endX-startX),2);
        float y = (float) Math.pow((endY-startY),2);
        return (float) (Math.sqrt(x+y)*5/(height*scale));
    }
}
