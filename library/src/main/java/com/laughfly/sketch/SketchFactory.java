package com.laughfly.sketch;

/**
 * Create by caowenyao on 2019/9/4.
 */
public interface SketchFactory<E extends SketchElement> {
    String getFactoryId();
    E createElement();
}
