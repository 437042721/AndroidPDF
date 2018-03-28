package com.cy.androidpdf.view.customview.pdfview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by lenovo on 2017/9/17.
 */

public class PdfFrameLayout extends FrameLayout {
    private float downX;
    private float downY;
    private OnPDFClickListener onPDFClickListener;
    public PdfFrameLayout(Context context) {
        this(context, null);
    }

    public PdfFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                float upX = ev.getX();
                if (Math.abs(upX - downX) < 5f) {
                    if (onPDFClickListener!=null){
                        onPDFClickListener.onPDFClick();
                    }
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setOnPDFClickListener(OnPDFClickListener onPDFClickListener){
        this.onPDFClickListener=onPDFClickListener;
    }
    public interface OnPDFClickListener{
        public void onPDFClick();
    }

}
