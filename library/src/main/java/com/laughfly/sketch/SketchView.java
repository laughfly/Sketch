package com.laughfly.sketch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.laughfly.sketch.model.LocalSketchModel;

import java.util.HashMap;
import java.util.List;

/**
 * Create by caowenyao on 2019/9/5.
 */
public class SketchView extends View implements SketchBoard {

    private HashMap<String, SketchFactory<?>> mSketchFactories = new HashMap<>();

    private SketchFactory<?> mCurrentFactory;

    //TODO
    private SketchCallback mCallback;

    private LocalSketchModel mSketchModel = new LocalSketchModel();

    private float mCanvasScale = 1f;

    private Paint mCanvasPaint;

    private Matrix mCanvasMatrix = new Matrix();

    private Canvas mBufferCanvas;

    private Bitmap mBufferBitmap;

    private Canvas mFixedCanvas;

    private Bitmap mFixedBitmap;

    public SketchView(Context context) {
        super(context);
    }

    public SketchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SketchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SketchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createBufferCanvas(w, h);
        createFixedCanvas(w, h, mFixedBitmap);
    }

    public void setCanvasScale(float canvasScale) {
        mCanvasScale = canvasScale;
    }

    private void createBufferCanvas(int w, int h) {
        if (mCanvasPaint == null) {
            mCanvasPaint = new Paint();
            mCanvasPaint.setAntiAlias(true);
            mCanvasPaint.setFilterBitmap(true);
            mCanvasPaint.setDither(true);
        }
        mBufferBitmap = Bitmap.createBitmap((int) (w * mCanvasScale), (int) (h * mCanvasScale), Bitmap.Config.ARGB_4444);
        mBufferCanvas = new Canvas(mBufferBitmap);
        mCanvasMatrix.setScale(1f / mCanvasScale, 1f / mCanvasScale, 0, 0);
    }

    private void createFixedCanvas(int w, int h, Bitmap previousBitmap) {
        mFixedBitmap = Bitmap.createBitmap((int) (w * mCanvasScale), (int) (h * mCanvasScale), Bitmap.Config.ARGB_4444);
        mFixedCanvas = new Canvas(mFixedBitmap);
        if (previousBitmap != null) {

        }
    }

    @Override
    public void registerFactory(@NonNull SketchFactory<?> factory) {
        mSketchFactories.put(factory.getFactoryId(), factory);
    }

    @Override
    public void unregisterFactory(@NonNull SketchFactory<?> factory) {
        mSketchFactories.remove(factory.getFactoryId());
    }

    @Override
    public void setCurrentFactory(@NonNull String factoryId) {
        SketchFactory<?> sketchFactory = mSketchFactories.get(factoryId);
        if (sketchFactory != null) {
            mCurrentFactory = sketchFactory;
        }
    }

    @Override
    public void setCurrentFactory(@Nullable SketchFactory<?> factory) {
        mCurrentFactory = factory;
    }

    @Override
    public @Nullable
    SketchFactory<?> getCurrentFactory() {
        return mCurrentFactory;
    }

    @Override
    public @Nullable
    String getCurrentFactoryId() {
        return mCurrentFactory != null ? mCurrentFactory.getFactoryId() : null;
    }

    @Override
    public void setCallback(SketchCallback callback) {
        mCallback = callback;
    }

    @Override
    public boolean canUndo() {
        return mSketchModel.canUndo();
    }

    @Override
    public void undo() {
        if(mSketchModel.undo()) {
            clearCanvas(mFixedCanvas);
            drawElements(mFixedCanvas, mSketchModel.getHistoryElements());
            invalidate();
        }
    }

    @Override
    public boolean canRedo() {
        return mSketchModel.canRedo();
    }

    @Override
    public void redo() {
        if(mSketchModel.redo()) {
            clearCanvas(mFixedCanvas);
            drawElements(mFixedCanvas, mSketchModel.getHistoryElements());
            invalidate();
        }
    }

    @Override
    public void clean() {
        mSketchModel.clean();
        clearCanvas(mFixedCanvas);
        invalidate();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX() * mCanvasScale;
        float y = event.getY() * mCanvasScale;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                motionDown(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                motionMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
                motionUp(x, y);
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        clearCanvas(mBufferCanvas);
        drawBitmap(mBufferCanvas, mFixedBitmap);
        drawCurrentElement(mBufferCanvas);
        canvas.drawBitmap(mBufferBitmap, mCanvasMatrix, mCanvasPaint);
    }

    private void drawBackground(Canvas canvas) {

    }

    private void drawBitmap(Canvas canvas, Bitmap bitmap) {
        canvas.drawBitmap(bitmap, 0, 0, mCanvasPaint);
    }

    private void drawElement(Canvas canvas, SketchElement element) {
        if (element != null) {
            element.draw(canvas);
        }
    }

    private void drawCurrentElement(Canvas canvas) {
        drawElement(canvas, mSketchModel.getCurrentElement());
    }

    private void drawElements(Canvas canvas, List<SketchElement> elements) {
        if (elements != null) {
            for (SketchElement element : elements) {
                element.draw(canvas);
            }
        }
    }

    private void motionDown(float x, float y) {
        SketchElement currentElement = mSketchModel.getCurrentElement();
        SketchElement touchElement = findTouchElement(x, y);

        if (touchElement != currentElement) {
            if (currentElement != null) {
                currentElement.setEditMode(false);
                if(currentElement.storeInHistory()) {
                    mSketchModel.addElement(currentElement);
                    if (touchElement == null) {
                        drawElement(mFixedCanvas, currentElement);
                    }
                }
            }

            if(touchElement != null) {
                mSketchModel.removeElement(touchElement);
                touchElement.setEditMode(true);
                clearCanvas(mFixedCanvas);
                drawElements(mFixedCanvas, mSketchModel.getHistoryElements());
            }
        } else if(touchElement != null){
            touchElement.setEditMode(true);
        }

        if (touchElement == null) {
            touchElement = mCurrentFactory.createElement();
        }

        mSketchModel.setCurrentElement(touchElement);

        touchElement.motionDown(x, y);

        invalidate();
    }

    private void motionMove(float x, float y) {
        SketchElement currentElement = mSketchModel.getCurrentElement();

        if (currentElement != null) {
            currentElement.motionMove(x, y);
        }

        invalidate();
    }

    private void motionUp(float x, float y) {
        SketchElement currentElement = mSketchModel.getCurrentElement();

        if (currentElement != null) {
            currentElement.motionUp(x, y);
        }

        invalidate();
    }

    private SketchElement findTouchElement(float x, float y) {
        return mSketchModel.findTouchElement(x, y);
    }

    private void clearCanvas(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
    }
}
