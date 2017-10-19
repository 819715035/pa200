package com.ys.pa200.weight.point;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by admin on 2017/10/11 0011.
 */

public class RectMeasurePoint extends BasePoint
{
    public RectMeasurePoint(int x, int y,int pos)
    {
        super(x, y);
        index = pos;
    }

    @Override
    public void drawMeasure(float startX, float startY, float endX, float endY, Canvas canvas, Paint paint)
    {
        paint.setColor(Color.parseColor("#e8a94f"));
        canvas.drawRect(startX,startY,endX,endY,paint);
        canvas.drawText("距离："+getMeasure(),endX+20,endY+30,paint);
    }

    @Override
    float getMeasure()
    {
        float x = Math.abs(endX-startX);
        float y = Math.abs(endY - startY);
        return (float) (x*y*25/Math.pow(height*scale,2));
    }
}
