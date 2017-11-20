package com.hds.touch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by hds on 17-11-17.
 */

public class Touch extends FrameLayout {
    public Touch(@NonNull Context context) {
        super(context);
    }

    public Touch(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Touch(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
