package com.laughfly.sketch.model;

import android.support.annotation.NonNull;

import com.laughfly.sketch.SketchElement;
import com.laughfly.sketch.SketchModel;
import com.laughfly.sketch.TransitionType;

import java.util.LinkedList;
import java.util.List;

/**
 * Create by caowenyao on 2019/9/5.
 */
public class LocalSketchModel implements SketchModel {

    private final List<SketchElement> mHistoryElements = new LinkedList<>();

    private final List<SketchElement> mRedoElements = new LinkedList<>();

    private SketchElement mCurrentElement;

    private Callback mCallback;

    public LocalSketchModel() {
    }

    @Override
    public List<SketchElement> getHistoryElements() {
        return mHistoryElements;
    }

    public List<SketchElement> getRedoElements() {
        return mRedoElements;
    }

    @Override
    public void addElement(SketchElement element) {
        mHistoryElements.add(element);
    }

    @Override
    public void removeElement(SketchElement element) {
        mHistoryElements.remove(element);
    }

    public boolean removeElements(@NonNull List<SketchElement> c) {
        return mHistoryElements.removeAll(c);
    }

    public SketchElement findTouchElement(float x, float y) {
        if (mCurrentElement != null && mCurrentElement.accept(x, y)) return mCurrentElement;
        int size = mHistoryElements.size();
        for (int i = size - 1; i >= 0; i--) {
            SketchElement element = mHistoryElements.get(i);
            if (element.accept(x, y)) {
                return element;
            }
        }
        return null;
    }

    @Override
    public void setCurrentElement(SketchElement currentElement) {
        mCurrentElement = currentElement;
    }

    @Override
    public SketchElement getCurrentElement() {
        return mCurrentElement;
    }

    @Override
    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public int getElementCount() {
        int size = 0;
        size += mHistoryElements.size();
        size += (mCurrentElement != null) ? 1 : 0;

        return size;
    }

    public int getRedoElementSize() {
        return mRedoElements.size();
    }

    public void bringElementFront(SketchElement element) {
        boolean remove = mHistoryElements.remove(element);
        if (remove) {
            mHistoryElements.add(element);
        }
    }

    private void onElementCountChange() {
        if (mCallback != null) {
            mCallback.onElementCountChange(getElementCount());
        }
    }

    private void onElementAdd(SketchElement element) {
        if (mCallback != null) {
            mCallback.onElementTransition(TransitionType.ADD, element);
        }
    }

    private void onElementRemove(SketchElement element) {
        if (mCallback != null) {
            mCallback.onElementTransition(TransitionType.REMOVE, element);
        }
    }

    private void onElementMotionStart(SketchElement element) {
        if (mCallback != null) {
            mCallback.onElementTransition(TransitionType.MOTION_START, element);
        }
    }

    private void onElementMotionEnd(SketchElement element) {
        if (mCallback != null) {
            mCallback.onElementTransition(TransitionType.MOTION_END, element);
        }
    }

    @Override
    public boolean canUndo() {
        return getElementCount() > 0;
    }

    @Override
    public boolean undo() {
        boolean undo = false;
        if (mCurrentElement != null) {
            mCurrentElement.setEditMode(false);
            if(mCurrentElement.storeInHistory()) {
                mRedoElements.add(mCurrentElement);
            }
            mCurrentElement = null;
            undo = true;
        } else {
            int size = mHistoryElements.size();
            if(size > 0) {
                SketchElement removed = mHistoryElements.remove(size - 1);
                if (removed != null) {
                    removed.setEditMode(false);
                    if(removed.storeInHistory()) {
                        mRedoElements.add(removed);
                    }
                    undo = true;
                }
            }
        }

        return undo;
    }

    @Override
    public boolean canRedo() {
        return getRedoElementSize() > 0;
    }

    @Override
    public boolean redo() {
        boolean redo = false;
        int size = getRedoElementSize();
        if(size > 0) {
            if (mCurrentElement != null) {
                mCurrentElement.setEditMode(false);
                if(mCurrentElement.storeInHistory()) {
                    mHistoryElements.add(mCurrentElement);
                }
                mCurrentElement = null;
                redo = true;
            }

            SketchElement removed = mRedoElements.remove(size - 1);
            if (removed != null) {
                if(removed.storeInHistory()) {
                    mHistoryElements.add(removed);
                }
                redo = true;
            }
        }
        return redo;
    }

    @Override
    public void clean() {
        mCurrentElement = null;
        mRedoElements.clear();
        mHistoryElements.clear();
    }
}
