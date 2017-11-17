package com.hds.crop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by hds on 17-11-16.
 */

public class CropFrameLayout extends FrameLayout {

    private static final int mColorCorner = Color.RED;
    private static final int mColorRect = 0x30FF4081;
    private final static int LEN = 40;
    private final static int STROKE_WIDTH = 6;

    private RectF mHotRectF = new RectF();
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CropFrameLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public CropFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CropFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHotRectF.left = getWidth() / 2 - getWidth() / 4;
        mHotRectF.top = getHeight() / 2 - getHeight() / 4;
        mHotRectF.right = getWidth() / 2 + getWidth() / 4;
        mHotRectF.bottom = getHeight() / 2 + getHeight() / 4;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawCorners(canvas);
        drawRect(canvas);
    }

    private void drawRect(Canvas canvas) {
        mPaint.setColor(mColorRect);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(mHotRectF.left, mHotRectF.top, mHotRectF.right, mHotRectF.bottom, mPaint);
    }


    private void drawCorners(Canvas canvas) {
        mPaint.setColor(mColorCorner);
        mPaint.setStrokeWidth(STROKE_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);
        int offset = STROKE_WIDTH / 2;
        canvas.drawLine(mHotRectF.left, mHotRectF.top + offset, mHotRectF.left + LEN + offset, mHotRectF.top + offset, mPaint);
        canvas.drawLine(mHotRectF.left + offset, mHotRectF.top, mHotRectF.left + offset, mHotRectF.top + LEN + offset, mPaint);

        canvas.drawLine(mHotRectF.right - LEN - offset, mHotRectF.top + offset, mHotRectF.right, mHotRectF.top + offset, mPaint);
        canvas.drawLine(mHotRectF.right - offset, mHotRectF.top, mHotRectF.right - offset, mHotRectF.top + LEN + offset, mPaint);

        canvas.drawLine(mHotRectF.right - offset, mHotRectF.bottom, mHotRectF.right - offset, mHotRectF.bottom - LEN - offset, mPaint);
        canvas.drawLine(mHotRectF.right - offset, mHotRectF.bottom - offset, mHotRectF.right - LEN - offset, mHotRectF.bottom - offset, mPaint);

        canvas.drawLine(mHotRectF.left + offset, mHotRectF.bottom, mHotRectF.left + offset, mHotRectF.bottom - LEN - offset, mPaint);
        canvas.drawLine(mHotRectF.left, mHotRectF.bottom - offset, mHotRectF.left + LEN + offset, mHotRectF.bottom - offset, mPaint);
    }





}
