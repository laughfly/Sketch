package com.laughfly.sketch;

/**
 * Create by caowenyao on 2019/9/6.
 */
public interface SketchCallback {
    void onElementCountChange(int elementCount);
    void onElementTransition(@TransitionType int action, SketchElement element);
    void onElementFactorySelect(String factoryId, SketchFactory factory);
}
