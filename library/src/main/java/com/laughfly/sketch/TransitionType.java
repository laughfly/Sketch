package com.laughfly.sketch;

import android.support.annotation.IntDef;

/**
 * Create by caowenyao on 2019/9/6.
 */
@IntDef
public @interface TransitionType {
    int ADD = 0;
    int REMOVE = 1;
    int MOTION_START = 2;
    int MOTION_MOVE = 3;
    int MOTION_END = 4;
}
