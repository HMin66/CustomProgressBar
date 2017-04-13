package com.huangmin66.customprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * 描述：
 * 作者： 天天童话丶
 * 时间： 2017/4/13.
 */
public class CustomProgressBar extends HorizontalProgressBar {


    private int mRadius = dp2px(30);

    private int mMaxPaintWidth;

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mReachHeight = (int) (mUnReachHeight * 2.5f);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar);
        mRadius = (int) ta.getDimension(R.styleable.CustomProgressBar_radius, mRadius);
        ta.recycle();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        //设置防抖动
        mPaint.setDither(true);
        //当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，圆形样式
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mMaxPaintWidth = Math.max(mReachHeight, mUnReachHeight);
        int expect = mRadius * 2 + mMaxPaintWidth + getPaddingLeft() + getPaddingRight();

        int width = resolveSize(expect, widthMeasureSpec);
        int height = resolveSize(expect, heightMeasureSpec);
        //取最小值为半径
        int readWidth = Math.min(width, height);
        mRadius = (readWidth - getPaddingLeft() -getPaddingRight() - mMaxPaintWidth) / 2;

        setMeasuredDimension(readWidth, readWidth);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        String text = getProgress() + "%";
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;

        canvas.save();

        canvas.translate(getPaddingLeft() + mMaxPaintWidth / 2, getPaddingTop() + mMaxPaintWidth / 2);
        mPaint.setStyle(Paint.Style.STROKE);
        //draw unreach bar
        mPaint.setColor(mUnReachColor);
        mPaint.setStrokeWidth(mUnReachHeight);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);

        //draw reach bar
        mPaint.setColor(mReachColor);
        mPaint.setStrokeWidth(mReachHeight);
        float sweepAngle = getProgress() * 1.0f / getMax() * 360;
        canvas.drawArc(new RectF(0, 0, mRadius * 2, mRadius * 2), 0, sweepAngle, false, mPaint);

        //draw text
        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, mRadius - textWidth / 2, mRadius - textHeight, mPaint);

        canvas.restore();
    }
}
