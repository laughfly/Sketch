package com.laughfly.sketch;

import android.graphics.Canvas;

/**
 * Create by caowenyao on 2019/9/4.
 */
public interface SketchElement {
    boolean storeInHistory();
    void draw(Canvas canvas);
    boolean accept(float x, float y);
    void setEditMode(boolean editMode);
    boolean isEditMode();
    void motionDown(float x, float y);
    void motionMove(float x, float y);
    void motionUp(float x, float y);
}
