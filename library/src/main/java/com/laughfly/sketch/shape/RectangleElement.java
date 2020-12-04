package com.laughfly.sketch.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.support.annotation.IntDef;

import com.laughfly.sketch.base.BaseElement;
import com.laughfly.sketch.base.SketchRectF;

/**
 * Create by caowenyao on 2019/9/5.
 */
public class RectangleElement extends BaseElement {
    private SketchRectF mRect = new SketchRectF();
    private Paint mPaint;
    private float mDownX, mDownY;

    private int mEditType;

    private int mPaintColor;

    private Xfermode mClearMode;

    @IntDef
    @interface EditType {
        int CENTER = 0;
        int LEFT = 2;
        int RIGHT = 4;
        int TOP = 8;
        int BOTTOM = 16;
        int LEFT_TOP = 10;
        int TOP_RIGHT = 12;
        int BOTTOM_LEFT = 18;
        int RIGHT_BOTTOM = 20;
    }

    public RectangleElement(Paint paint) {
        mPaint = paint;
    }

    @Override
    public String toString() {
        return "RectangleElement{" +
                "mRect=" + mRect +
                '}';
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(mRect, mPaint);

        if(isEditMode()) {
            drawEditPoint(canvas, mRect.left, mRect.centerY());
            drawEditPoint(canvas, mRect.right, mRect.centerY());
            drawEditPoint(canvas, mRect.centerX(), mRect.top);
            drawEditPoint(canvas, mRect.centerX(), mRect.bottom);

            drawEditPoint(canvas, mRect.left, mRect.top);
            drawEditPoint(canvas, mRect.right, mRect.top);
            drawEditPoint(canvas, mRect.left, mRect.bottom);
            drawEditPoint(canvas, mRect.right, mRect.bottom);
        }
    }

    private void drawEditPoint(Canvas canvas, float x, float y) {
        Paint.Style style = mPaint.getStyle();
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, mPaint.getStrokeWidth() * 2, mPaint);
        Xfermode xfermode = mPaint.getXfermode();
        mPaint.setXfermode(mClearMode);
        canvas.drawCircle(x, y, mPaint.getStrokeWidth(), mPaint);
        mPaint.setStyle(style);
        mPaint.setXfermode(xfermode);
    }

    @Override
    public boolean accept(float x, float y) {
        float outSizeX = mRect.getWidth() * 0.2f;
        float outSizeY = mRect.getHeight() * 0.2f;
        mRect.inset(-outSizeX, -outSizeY);
        boolean accept = mRect.contains(x,y);
        mRect.inset(outSizeX, outSizeY);
        return accept;
    }

    @Override
    public void setEditMode(boolean editMode) {
        if(isEditMode() == editMode) return;
        super.setEditMode(editMode);
        if(editMode) {
            mClearMode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
            mPaintColor = mPaint.getColor();
            mPaint.setColor(0xFFFF0000);
        } else {
            mClearMode = null;
            mPaint.setColor(mPaintColor);
        }
    }

    @Override
    public void motionDown(float x, float y) {
        if (isEditMode()) {
            int xAlign, yAlign;

            float width = mRect.getWidth();
            float height = mRect.getHeight();

            float centerX = mRect.centerX();
            float centerY = mRect.centerY();

            float offsetX = x - centerX;
            float offsetY = y - centerY;

            boolean xReverse = mRect.isXReverse();
            boolean yReverse = mRect.isYReverse();

            if (Math.abs(offsetX) < width / 2.5f) {
                xAlign = EditType.CENTER;
            } else {
                xAlign = offsetX > 0 && !xReverse || offsetX < 0 && xReverse ? EditType.RIGHT : EditType.LEFT;
            }

            if (Math.abs(offsetY) < height / 2.5f) {
                yAlign = EditType.CENTER;
            } else {
                yAlign = offsetY > 0 && !yReverse || offsetY < 0 && yReverse ? EditType.BOTTOM : EditType.TOP;
            }

            mEditType = xAlign | yAlign;
        } else {
            mRect.set(x, y, x, y);
        }
        mDownX = x;
        mDownY = y;
    }

    @Override
    public void motionMove(float x, float y) {
        if (isEditMode()) {
            switch (mEditType) {
                case EditType.CENTER:
                    mRect.offset(x - mDownX, y - mDownY);
                    break;
                case EditType.LEFT:
                    mRect.left += x - mDownX;
                    break;
                case EditType.TOP:
                    mRect.top += y - mDownY;
                    break;
                case EditType.RIGHT:
                    mRect.right += x - mDownX;
                    break;
                case EditType.BOTTOM:
                    mRect.bottom += y - mDownY;
                    break;
                case EditType.BOTTOM_LEFT:
                    mRect.bottom += y - mDownY;
                    mRect.left += x - mDownX;
                    break;
                case EditType.LEFT_TOP:
                    mRect.left += x - mDownX;
                    mRect.top += y - mDownY;
                    break;
                case EditType.RIGHT_BOTTOM:
                    mRect.right += x - mDownX;
                    mRect.bottom += y - mDownY;
                    break;
                case EditType.TOP_RIGHT:
                    mRect.top += y - mDownY;
                    mRect.right += x - mDownX;
                    break;
            }
            mDownX = x;
            mDownY = y;
        } else {
            mRect.set(Math.min(x, mDownX), Math.min(y, mDownY), Math.max(x, mDownX), Math.max(y, mDownY));
        }
    }

    @Override
    public void motionUp(float x, float y) {
        motionMove(x, y);
    }
}
