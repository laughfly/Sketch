package com.laughfly.sketch;

/**
 * Create by caowenyao on 2019/9/4.
 */
public interface SketchBoard {
    void registerFactory(SketchFactory<?> factory);

    void unregisterFactory(SketchFactory<?> factory);

    void setCallback(SketchCallback callback);

    void setCurrentFactory(String factoryId);

    void setCurrentFactory(SketchFactory<?> factory);

    SketchFactory<?> getCurrentFactory();

    String getCurrentFactoryId();

    boolean canUndo();

    void undo();

    boolean canRedo();

    void redo();

    void clean();
}
