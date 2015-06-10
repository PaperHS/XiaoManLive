package com.insthub.ecmobile.component;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by caifangmao on 15/6/4.
 */
public class CircleLoadingView extends View {

    private ValueAnimator mRotateAnimator;
    private ValueAnimator mScalingAnimator;

    private float mRotateFactor;
    private float mScalingFactor;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mWidth;
    private int mHeight;

    private int outerRadius;
    private int innerRadius;

    private RectF outerRect;
    private RectF innerRect;

    private int mCenterX;
    private int mCenterY;

    public CircleLoadingView(Context context){
        this(context, null);
    }

    public CircleLoadingView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public CircleLoadingView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);

        mRotateAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        mRotateAnimator.setDuration(600L);
        mRotateAnimator.setInterpolator(new LinearInterpolator());
        mRotateAnimator.setRepeatCount(-1);
        mRotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation){
                mRotateFactor = (Float) animation.getAnimatedValue();

                invalidate();
            }
        });

        mScalingAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        mScalingAnimator.setDuration(2000L);
        mScalingAnimator.setRepeatCount(-1);
        mScalingAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mScalingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator animation){
                mScalingFactor = (Float) animation.getAnimatedValue();

                invalidate();
            }
        });

        outerRect = new RectF();
        innerRect = new RectF();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xFFFF0000);
        mPaint.setStrokeWidth(5);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    @Override
    protected void onAttachedToWindow(){
        super.onAttachedToWindow();

        mRotateAnimator.start();
        mScalingAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

        mCenterX = w / 2;
        mCenterY = h / 2;

        outerRadius = mCenterX > mCenterY ? mCenterY - 2 : mCenterX - 2;
        innerRadius = (int) (outerRadius * 0.8);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.save();
        canvas.rotate(360 * mRotateFactor, mCenterX, mCenterY);

        float startAngle = mScalingFactor > 0.5 ? (mScalingFactor - 0.5f) * 2 * 360 : 0;
        float sweepAngle = mScalingFactor > 0.5 ? 360 - startAngle : mScalingFactor * 2 * 360;

        outerRect.left = mCenterX - outerRadius;
        outerRect.top = mCenterY - outerRadius;
        outerRect.right = mCenterX + outerRadius;
        outerRect.bottom = mCenterY + outerRadius;

        innerRect.left = mCenterX - innerRadius;
        innerRect.top = mCenterY - innerRadius;
        innerRect.right = mCenterX + innerRadius;
        innerRect.bottom = mCenterY + innerRadius;

        canvas.drawArc(outerRect, startAngle, sweepAngle, false, mPaint);
        canvas.drawArc(innerRect, startAngle + 180, sweepAngle, false, mPaint);

        canvas.restore();
    }
}
