package com.cy.androidpdf.view.customview.selector;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.cy.androidpdf.R;


/**
 * Created by lenovo on 2017/8/30.
 */

public class ImageViewCheckBox extends ImageView {
    private int backgroundID, backgroundCheckedID, bg_color, bg_checked_color,
            srcCheckedID, srcUncheckedID;

    private OnCheckedChangeListener onCheckedChangeListener;
    private boolean isChecked = false;

    private boolean isMyListener = true;
    private float downX, downY;

    public ImageViewCheckBox(Context context) {
        this(context, null);
    }


    public ImageViewCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.ImageViewCheckBox);
        backgroundID = arr.getResourceId(R.styleable.ImageViewCheckBox_bgUnChecked, 0);
        backgroundCheckedID = arr.getResourceId(R.styleable.ImageViewCheckBox_bgChecked, 0);

        if (backgroundID == 0) {
            bg_color = arr.getColor(R.styleable.ImageViewCheckBox_bgUnChecked, 0);

        }
        if (backgroundCheckedID == 0) {
            bg_checked_color = arr.getColor(R.styleable.ImageViewCheckBox_bgChecked, 0);

        }

        srcCheckedID = arr.getResourceId(R.styleable.ImageViewCheckBox_srcChecked, 0);
        srcUncheckedID = arr.getResourceId(R.styleable.ImageViewCheckBox_srcUnChecked, 0);

        isChecked = arr.getBoolean(R.styleable.ImageViewCheckBox_checked, false);


        if (isChecked()) {

            setResOnChecked();

        } else {
            setResOnUnChecked();

        }
        arr.recycle();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setChecked(!isChecked);
                if (onCheckedChangeListener!=null){

                    onCheckedChangeListener.onCheckedChanged(ImageViewCheckBox.this,isChecked);
                }
            }
        });

    }


    //    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            //按下
//            case MotionEvent.ACTION_DOWN:
//                downX=event.getRawX();
//                downY=event.getRawY();
//                break;
//            //移动
//            case MotionEvent.ACTION_MOVE:
//                break;
//            //抬起
//            case MotionEvent.ACTION_UP:
//
//                int[] startXY = new int[2];
//                getLocationOnScreen(startXY);
//                float endX=startXY[0]+getWidth();
//                float endY=startXY[1]+getHeight();
//
//                Log.e("downX", "--------------------" + downX);
//                Log.e("downY", "--------------------" + downY);
//
//                Log.e("startX", "--------------------" + startXY[0]);
//                Log.e("startY", "--------------------" + startXY[1]);
//                Log.e("endX", "--------------------" + endX);
//                Log.e("endY", "--------------------" + endY);
//
//                if ((downX>=startXY[0]*1f)&&(downX<=endX)&&(downY>=startXY[1]*1f)&&(downY<=endY)){
//                    Log.e("up", "--------------------" + isChecked);
//
//                    setChecked(!isChecked);
//                    if (onCheckedChangeListener != null) {
//                        onCheckedChangeListener.onCheckedChanged(ImageViewCheckBox.this, isChecked);
//                    }
//                }
//
//                break;
//        }
//        return super.onTouchEvent(event);
//    }
//    @Override
//    public void setOnClickListener(OnClickListener l) {
//
//        if (isMyListener) {
//            Log.e("oncli", "--------------------" + 354);
//
//            super.setOnClickListener(l);
//            isMyListener = !isMyListener;
//        }
//    }


    @Override
    public void setOnClickListener(OnClickListener l) {
        if (isMyListener) {

            super.setOnClickListener(l);
            isMyListener=false;
        }
    }

    //监听器使用这个方法
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListener = listener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(ImageViewCheckBox iv, boolean isChecked);
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        if (checked) {
            setResOnChecked();
        } else {
            setResOnUnChecked();
        }
    }

    private void setResOnChecked() {
        if (backgroundCheckedID != 0) {

            setBackgroundResource(backgroundCheckedID);
        }
        if (bg_checked_color != 0) {
            setBackgroundColor(bg_checked_color);
        }
        if (srcCheckedID != 0) {
            setImageResource(srcCheckedID);
        }

    }

    private void setResOnUnChecked() {
        if (backgroundID != 0) {

            setBackgroundResource(backgroundID);
        }
        if (bg_color != 0) {
            setBackgroundColor(bg_color);
        }
        if (srcUncheckedID != 0) {
            setImageResource(srcUncheckedID);
        }
    }
}
