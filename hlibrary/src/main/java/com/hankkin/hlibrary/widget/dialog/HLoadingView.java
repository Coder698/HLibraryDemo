package com.hankkin.hlibrary.widget.dialog;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mac on 2017/2/28.
 */

public class HLoadingView extends View {

    private int mWidth, mHeight, mRadius;
    private Paint mPaint;
    private float degree;
    private ValueAnimator degreeAnimation, radiusAnimation;
    private int[] colors = {getResources().getColor(android.R.color.holo_green_light),
            getResources().getColor(android.R.color.holo_blue_bright),
            getResources().getColor(android.R.color.holo_red_light),
            getResources().getColor(android.R.color.holo_orange_light),
            getResources().getColor(android.R.color.holo_purple),
            getResources().getColor(android.R.color.holo_red_dark)};

    public HLoadingView(Context context) {
        super(context);
        init();
    }

    public HLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mWidth / 2, mHeight / 2);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.rotate(degree);
        for (int i = 0; i < 6; i++) {
            mPaint.setColor(colors[i]);
            canvas.drawCircle(0, mRadius, 10, mPaint);
            canvas.rotate(60);
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = widthSize * 1 / 3;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = heightSize * 1 / 3;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mHeight = getHeight();
        mRadius = mWidth / 4;
    }

    public void startDegreeAnimation() {

        degree = 0;
        degreeAnimation = ValueAnimator.ofFloat(0, 360);
        degreeAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degree = (float) animation.getAnimatedValue();
                invalidate();
            }

        });
        degreeAnimation.setRepeatCount(ValueAnimator.INFINITE);
        degreeAnimation.setDuration(1000);
        degreeAnimation.start();
    }

    public void endAnimation() {
        degreeAnimation.cancel();
        degreeAnimation.end();
        startRadiusAnimation();
    }


    private void startRadiusAnimation() {
        degree = 0;
        radiusAnimation = ValueAnimator.ofInt(mWidth / 4, mWidth / 2, 0);
        radiusAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRadius = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        radiusAnimation.setDuration(500);
        radiusAnimation.start();

    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (!hasWindowFocus) {
            degreeAnimation.cancel();
            degreeAnimation.end();
        }
    }
}