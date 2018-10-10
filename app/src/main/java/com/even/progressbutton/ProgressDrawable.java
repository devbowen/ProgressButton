package com.even.progressbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.animation.AccelerateInterpolator;

/**
 * @author Bowen Wang
 */
public class ProgressDrawable extends Drawable {

    private static final int STAGE_NULL = 0, STAGE_ROTATE = 1, STAGE_SUCCESS = 2, STAGE_FAIL = 3;
    private int stage = 0;
    private float degrees = 0;
    private int colorDefault = Color.WHITE, colorSuccess = Color.GREEN, colorError = Color.RED;
    private Paint mPaint;
    private ValueAnimator animator;
    private float width;
    private RectF rectF;
    private float centerX, centerY;
    private Animatable animatable;
    private Path pathSuccess, pathFailL, pathFailR;
    private float lengthSuccess, lengthFail, length;

    public ProgressDrawable(float width) {
        this.width = width;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        rectF = new RectF(0, 0, width, width);
        centerX = width / 2;
        centerY = width / 2;
        initPath();
    }

    public void initPath() {
        pathSuccess = new Path();
        pathSuccess.moveTo(width * 1f, width * 0.2f);
        pathSuccess.lineTo(width * 0.4f, width * 0.8f);
        pathSuccess.lineTo(0f, width * 0.4f);
        lengthSuccess = new PathMeasure(pathSuccess, false).getLength();


        pathFailL = new Path();
        pathFailL.moveTo(width * 0.9f, width * 0.9f);
        pathFailL.lineTo(width * 0.1f, width * 0.1f);
        lengthFail = new PathMeasure(pathFailL, false).getLength();

        pathFailR = new Path();
        pathFailR.moveTo(width * 0.9f, width * 0.1f);
        pathFailR.lineTo(width * 0.1f, width * 0.9f);
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) width;
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) width;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (stage == STAGE_ROTATE) {
            canvas.save();
            canvas.rotate(degrees, centerX, centerY);
            canvas.drawArc(rectF, -90f, 100f, false, mPaint);
            canvas.restore();
            return;
        }
        if (stage == STAGE_SUCCESS) {
            canvas.drawPath(pathSuccess, mPaint);

            return;
        }
        if (stage == STAGE_FAIL) {
            canvas.drawPath(pathFailL, mPaint);
            canvas.drawPath(pathFailR, mPaint);
            return;
        }
    }

    public void startRotate() {
        stage = STAGE_ROTATE;
        mPaint.setColor(colorDefault);
        if (animator == null) {
            animator = ValueAnimator.ofFloat(0f, 1f);
            animator.setDuration(1000);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    degrees += 5;
                    invalidateSelf();
                }
            });
        }
        animator.start();
    }

    public void stopRotate() {
        if (animator != null && animator.isRunning()) {
            animator.end();
        }
    }

    public void showSuccess() {
        stage = STAGE_SUCCESS;
        length = lengthSuccess;
        mPaint.setColor(colorSuccess);
        startAnim();
    }

    public void showFail() {
        stage = STAGE_FAIL;
        length = lengthFail;
        mPaint.setColor(colorError);
        startAnim();
    }

    //利用属性动画改变参数Phase，从而动态的改变DashPathEffect的长度，实现跟踪路径的效果
    private void startAnim() {
        stopRotate();
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "Phase", 1f, 0f);
        animator.setDuration(400);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (animatable != null) {
                    animatable.stop();
                }
            }
        });
        animator.start();
    }

    private void setPhase(float phase) {
        mPaint.setPathEffect(new DashPathEffect(new float[]{length, length}, -length * phase));
        invalidateSelf();
    }


    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
