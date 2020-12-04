package com.laughfly.sketch;

import java.util.List;

/**
 * Create by caowenyao on 2019/9/6.
 */
public interface SketchModel {

    interface Callback {
        void onElementCountChange(int elementCount);
        void onElementTransition(@TransitionType int action, SketchElement element);
    }

    List<SketchElement> getHistoryElements();

    void addElement(SketchElement element);

    void removeElement(SketchElement element);

    void setCurrentElement(SketchElement currentElement);

    SketchElement getCurrentElement();

    void setCallback(Callback callback);

    int getElementCount();

    boolean canUndo();

    boolean undo();

    boolean canRedo();

    boolean redo();

    void clean();
}
