package com.hds.crop;

import android.content.Context;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

/**
 * Created by hds on 17-11-15.
 */

public class CropLayout extends FrameLayout {
    private static final String TAG = "CropLayout";


    private int mTouchSlop;
    private int currentCorner = -1;
    private float preTouchX = -1;
    private boolean canBeExpand = false;

    private Shapper shapper;
    private ViewDragHelper dragHelper;

    public CropLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public CropLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CropLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        shapper = (Shapper) getChildAt(0);
    }

    private void init() {
        final ViewConfiguration vc = ViewConfiguration.get(getContext());
        mTouchSlop = vc.getScaledTouchSlop() * 3;

        dragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == getChildView();
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
//                Log.d(TAG, "cancel");
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
//                settleView(child, left);
                dragToUpdate(left + dx, child.getTop());
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - getChildView().getWidth();
                return Math.min(Math.max(left, leftBound), rightBound);
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                dragToUpdate(child.getLeft(), top + dy);
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - getChildView().getHeight() - getChildView().getPaddingBottom();
                return Math.min(Math.max(top, topBound), bottomBound);
            }

        });
    }

    private void dragToUpdate(int x, int y) {
        RectF rectF = shapper.getCornerRects();
        Log.d(TAG, "touchSlop: " + mTouchSlop + ",x:" + x + ",y:" + y + "," + rectF);
        //find out touch position within {0,1,2,3}

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (handleExpand(x, y, event)) {
            //处理 缩放
            return true;
        }
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    /**
     * 手指在四个角
     *
     * @param x
     * @param y
     * @return
     */
    private int inCorner(float x, float y) {
        RectF rectF = shapper.getCornerRects();
        rectF.left = shapper.getShapper().getLeft();
        rectF.top = shapper.getShapper().getTop();
        rectF.right = shapper.getShapper().getRight();
        rectF.bottom = shapper.getShapper().getBottom();
        //left top
        if ((rectF.left) < x && (rectF.left + mTouchSlop) > x
                && (rectF.top - mTouchSlop) <= y && (rectF.top + mTouchSlop) > y) {
            Log.d(TAG, "0  corner");
            return 0;//0
        }
        if ((rectF.right - mTouchSlop) < x && (rectF.right) > x
                && (rectF.top - mTouchSlop) <= y && (rectF.top + mTouchSlop) > y) {
            Log.d(TAG, "1  corner");
            return 1;//1
        }
        if ((rectF.left) < x && (rectF.left + mTouchSlop) > x
                && (rectF.bottom - mTouchSlop) <= y && (rectF.bottom) > y) {
            Log.d(TAG, "3  corner");
            return 3;//3
        }
        if ((rectF.right - mTouchSlop) < x && (rectF.right + mTouchSlop / 2) > x
                && (rectF.bottom - mTouchSlop) <= y && (rectF.bottom) > y) {
            Log.d(TAG, "2  corner");
            return 2;//2
        }
        return -1;
    }


    private void setCanBeExpand(boolean canBeExpand) {
        this.canBeExpand = canBeExpand;
    }

    private boolean handleExpand(float x, float y, MotionEvent ev) {
        final int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            currentCorner = -1;
            if ((currentCorner = inCorner(x, y)) == -1) {
                return false;
            } else {
                setCanBeExpand(true);
            }
        }
        if (currentCorner == -1) return false;
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                if (canBeExpand) {
                    final float rawX = ev.getX();
                    final float rawY = ev.getY();
                    if (preTouchX != -1) {
                        //hit
                        Log.d(TAG, "rawX:" + rawX + ",rawY:" + rawY);
                        layoutChild(currentCorner, rawX, rawY);
                    }
                    preTouchX = rawX;
                    return true;
                } else return false;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setCanBeExpand(false);
                preTouchX = -1;
                break;
            default:
                break;
        }
        return false;
    }

    int l = 0, t = 0, r = 0, b = 0;

    private void layoutChild(int currentCorner, float rawX, float rawY) {
        synchronized (this) {
            if (currentCorner == 0) {
                l = (int) rawX;
                t = (int) rawY;
                r = getChildView().getRight();
                b = getChildView().getBottom();
            } else if (currentCorner == 1) {
                l = getChildView().getLeft();
                t = (int) rawY;
                r = (int) rawX;
                b = getChildView().getBottom();
            } else if (currentCorner == 2) {
                l = getChildView().getLeft();
                t = getChildView().getTop();
                r = (int) rawX;
                b = (int) rawY;
            } else if (currentCorner == 3) {
                l = (int) rawX;
                t = getChildView().getTop();
                r = getChildView().getRight();
                b = (int) rawY;
            }
            if (l >= r || b <= t) return;
            l = Math.max(0, l);
            t = Math.max(0, t);
            r = Math.min(getWidth(), r);
            b = Math.min(getHeight(), b);

            int w = r - l, h = b - t;
            if (w < getChildView().getMinimumWidth()) {
                w = getChildView().getMinimumWidth();
            }
            if (l + w > getRight()) {
                l = getRight() - w;
            }
//            l = Math.min(getRight() - w, getChildView().getLeft());
            if (h < getChildView().getMinimumHeight()) {
                h = getChildView().getMinimumHeight();
            }
            if (t + h > getBottom()) {
                t = getBottom() - h;
            }
            Log.d(TAG, "index:" + currentCorner + String.format(",l:%s,t:%s,vR:%s,vB:%s", l, t, getRight() - getChildView().getRight(), getBottom() - getChildView().getBottom()));
            LayoutParams lp = (LayoutParams) getChildView().getLayoutParams();
            lp.width = w;
            lp.height = h;
            lp.setMargins(l, t, 0, 0);
            getChildView().layout(l, t, r, b);
            getChildView().requestLayout();
        }
    }

    private View getChildView() {
        return shapper.getShapper();
    }

}
