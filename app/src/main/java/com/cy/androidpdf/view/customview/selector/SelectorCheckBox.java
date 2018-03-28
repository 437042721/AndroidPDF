package com.cy.androidpdf.view.customview.selector;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import com.cy.androidpdf.R;


/**
 * Created by lenovo on 2017/7/31.
 */

public class SelectorCheckBox extends AppCompatCheckBox {
    private int backgroundID, backgroundCheckedID , bg_color,bg_checked_color,
            textColorID , textColorCheckedID,
            buttonRes , buttonCheckedRes;
    private String textChecked,textUnChecked;
    private SelectorOnCheckedChangeListener selectorOnCheckedChangeListener;

    public SelectorCheckBox(Context context) {
        this(context, null);
    }
    private boolean myListener=true;

    public SelectorCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.SelectorCheckBox);
        backgroundID=arr.getResourceId(R.styleable.SelectorCheckBox_backgroundUnChecked,0);
        backgroundCheckedID=arr.getResourceId(R.styleable.SelectorCheckBox_backgroundChecked,0);

        if (backgroundID==0){
            bg_color=arr.getColor(R.styleable.SelectorCheckBox_backgroundUnChecked,0);

        }
        if (backgroundCheckedID==0){
            bg_checked_color=arr.getColor(R.styleable.SelectorCheckBox_backgroundChecked,0);

        }

        buttonRes=arr.getResourceId(R.styleable.SelectorCheckBox_buttonUnChecked,0);
        buttonCheckedRes=arr.getResourceId(R.styleable.SelectorCheckBox_buttonChecked,0);



        textColorID=arr.getColor(R.styleable.SelectorCheckBox_textColorUnChecked,0);
        textColorCheckedID=arr.getColor(R.styleable.SelectorCheckBox_textColorChecked,0);

        textChecked=arr.getString(R.styleable.SelectorCheckBox_textChecked);
        textUnChecked=arr.getString(R.styleable.SelectorCheckBox_textUnChecked);
        arr.recycle();

        if (isChecked()) {

            setResOnChecked();

        } else {
            setResOnUnChecked();

        }

        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setResOnChecked();

                } else {
                    setResOnUnChecked();


                }
                if (selectorOnCheckedChangeListener != null) {
                    selectorOnCheckedChangeListener.onCheckedChanged(buttonView, isChecked);
                }
            }
        });

    }

    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        if (myListener){

            super.setOnCheckedChangeListener(listener);
            myListener=false;
        }
    }

    //监听器使用这个方法
    public void setSelectorOnCheckedChangeListener(SelectorOnCheckedChangeListener listener) {
        this.selectorOnCheckedChangeListener = listener;
    }

    public interface SelectorOnCheckedChangeListener extends OnCheckedChangeListener {
        @Override
        void onCheckedChanged(CompoundButton buttonView, boolean isChecked);
    }

    private void setResOnChecked() {
        if (backgroundCheckedID != 0) {

            setBackgroundResource(backgroundCheckedID);
        }
        if (bg_checked_color!=0){
            setBackgroundColor(bg_checked_color);
        }
        if (buttonCheckedRes != 0) {
            setButtonDrawable(buttonCheckedRes);
        }


        if (textColorCheckedID != 0) {

            setTextColor(textColorCheckedID);
        }

        if (textChecked!=null){
            setText(textChecked);
        }


    }

    private void setResOnUnChecked() {
        if (backgroundID != 0) {

            setBackgroundResource(backgroundID);
        }
        if (bg_color!=0){
            setBackgroundColor(bg_color);
        }
        if (buttonRes != 0) {
            setButtonDrawable(buttonRes);
        }

        if (textColorID != 0) {

            setTextColor(textColorID);
        }
        if (textUnChecked!=null){
            setText(textUnChecked);
        }
    }
}
