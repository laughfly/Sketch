package com.laughfly.sketch.eraser;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.laughfly.sketch.FactoryIDs;
import com.laughfly.sketch.SketchFactory;

/**
 * Create by caowenyao on 2019/9/5.
 */
public class EraserFactory implements SketchFactory<EraserElement> {
    private Paint mPaint;

    public EraserFactory(float defaultSize) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(defaultSize);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public void setSize(float size) {
        mPaint.setStrokeWidth(size);
    }

    @Override
    public String getFactoryId() {
        return FactoryIDs.ERASER;
    }

    @Override
    public EraserElement createElement() {
        return new EraserElement(new Paint(mPaint));
    }
}
