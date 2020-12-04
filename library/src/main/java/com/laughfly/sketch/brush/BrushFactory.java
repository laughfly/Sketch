package com.laughfly.sketch.brush;

import android.graphics.Paint;
import android.support.annotation.ColorInt;

import com.laughfly.sketch.FactoryIDs;
import com.laughfly.sketch.SketchFactory;

/**
 * Create by caowenyao on 2019/9/5.
 */
public class BrushFactory implements SketchFactory<BrushElement> {
    private Paint mPaint;

    public BrushFactory(float defaultSize, int defaultColor) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(defaultColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(defaultSize);
    }

    public void setSize(float size) {
        mPaint.setStrokeWidth(size);
    }

    public void setColor(@ColorInt int color) {
        mPaint.setColor(color);
    }

    @Override
    public String getFactoryId() {
        return FactoryIDs.BRUSH;
    }

    @Override
    public BrushElement createElement() {
        return new BrushElement(new Paint(mPaint));
    }
}
