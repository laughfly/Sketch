package com.laughfly.sketch.laser;

import android.graphics.Color;
import android.graphics.Paint;

import com.laughfly.sketch.FactoryIDs;
import com.laughfly.sketch.SketchFactory;

/**
 * Create by caowenyao on 2019/9/6.
 */
public class LaserFactory implements SketchFactory<LaserElement> {

    private LaserElement mLaserElement;

    private Paint mPaint;

    private float mSize;

    public LaserFactory(float size) {
        mSize = size;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public String getFactoryId() {
        return FactoryIDs.LASER;
    }

    @Override
    public LaserElement createElement() {
        if(mLaserElement == null) {
            mLaserElement = new LaserElement(mPaint, mSize);
        }
        return mLaserElement;
    }
}
