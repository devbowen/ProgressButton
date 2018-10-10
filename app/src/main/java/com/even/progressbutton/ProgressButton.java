package com.even.progressbutton;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

/**
 * @author Bowen Wang
 */
public class ProgressButton extends AppCompatButton {

    private ProgressDrawable drawable;
    private float textSize;

    public ProgressButton(Context context) {
        super(context);
        init();
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        textSize = getTextSize();
        drawable = new ProgressDrawable(textSize);
    }

    public void startLoad() {
        setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        setCompoundDrawablePadding(15);
        drawable.startRotate();
        setClickable(false);
    }

    public void loadSuccess() {
        setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        setCompoundDrawablePadding(15);
        drawable.showSuccess();
    }

    public void loadFail() {
        setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        setCompoundDrawablePadding(15);
        drawable.showFail();
    }

    public void stopLoad() {
        setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        setClickable(true);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        drawable.stopRotate();
    }

}
