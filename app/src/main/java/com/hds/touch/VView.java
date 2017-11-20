package com.hds.touch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hds on 17-11-17.
 */

public class VView extends View {
    public VView(Context context) {
        super(context);
    }

    public VView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getAction(event.getAction());
        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void getAction(int id) {
        if ((id & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
            Log.d("getAction", "ACTION_DOWN");
        } else if ((id & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
            Log.d("getAction", "ACTION_UP");
        } else if ((id & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_MOVE) {
            Log.d("getAction", "ACTION_MOVE");
        }

    }
}
