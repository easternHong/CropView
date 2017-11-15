package com.hds.crop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

/**
 * Created by hds on 17-11-15.
 */

public class EffectLayout extends FrameLayout implements Shapper {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private RectF defaultRect = new RectF();

    private int colorCorner = Color.RED;
    private int colorRect = 0x30FF4081;

    public EffectLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public EffectLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EffectLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private int mTouchSlop;

    private void init() {
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        final ViewConfiguration vc = ViewConfiguration.get(getContext());
        mTouchSlop = vc.getScaledTouchSlop() * 3;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(colorRect);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        mPaint.setColor(colorCorner);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        //0
        canvas.drawLine(0, 0, mTouchSlop, 0, mPaint);
        canvas.drawLine(0, 0, 0, mTouchSlop, mPaint);
        //1
        canvas.drawLine(getWidth() - mTouchSlop, 0, getWidth(), 0, mPaint);
        canvas.drawLine(getWidth(), 0, getWidth(), mTouchSlop, mPaint);
        //2
        canvas.drawLine(getWidth() - mTouchSlop, getHeight(),
                getWidth(), getHeight(), mPaint);
        canvas.drawLine(getWidth(), getHeight() - mTouchSlop,
                getWidth(), getHeight(), mPaint);
//        //3
        canvas.drawLine(0, getHeight(),
                0, getHeight() - mTouchSlop, mPaint);
        canvas.drawLine(0, getHeight(),
                mTouchSlop, getHeight(), mPaint);
        super.dispatchDraw(canvas);
    }

//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        if (getChildCount() > 0) {
////            requestLayout();
//            for (int i = 0; i < getChildCount(); i++) {
//                getChildAt(i).requestLayout();
//            }
//        }
//    }

    @Override
    public View getShapper() {
        return this;
    }

    RectF rectFS = new RectF();

    @Override
    public RectF getCornerRects() {
        rectFS.left = getLeft();
        rectFS.top = getTop();
        rectFS.right = getRight();
        rectFS.bottom = getBottom();
        return rectFS;
    }

}
