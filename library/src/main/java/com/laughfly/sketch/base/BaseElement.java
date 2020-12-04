package com.laughfly.sketch.base;

import android.graphics.Canvas;

import com.laughfly.sketch.SketchElement;

/**
 * Create by caowenyao on 2019/9/5.
 */
public abstract class BaseElement implements SketchElement {
    private boolean mEditMode;

    @Override
    public boolean storeInHistory() {
        return true;
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public boolean accept(float x, float y) {
        return false;
    }

    @Override
    public void setEditMode(boolean editMode) {
        mEditMode = editMode;
    }

    @Override
    public boolean isEditMode() {
        return mEditMode;
    }

    @Override
    public void motionDown(float x, float y) {

    }

    @Override
    public void motionMove(float x, float y) {

    }

    @Override
    public void motionUp(float x, float y) {

    }
}
