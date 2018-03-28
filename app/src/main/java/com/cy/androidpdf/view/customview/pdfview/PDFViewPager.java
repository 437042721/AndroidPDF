package com.cy.androidpdf.view.customview.pdfview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

public class PDFViewPager extends ViewPager {
    private PDFPagerAdapter pdfPagerAdapter;
    protected Context context;
    private OnVPClickLisetener onVPClickLisetener;
    public PDFViewPager(Context context, String pdfPath) {
        super(context);
        this.context = context;
        init(pdfPath);
    }

    protected void init(String pdfPath) {
        setClickable(true);
        initAdapter(context, pdfPath);
    }

    protected void initAdapter(Context context, String pdfPath) {
        pdfPagerAdapter=new PDFPagerAdapter.Builder(context)
                .setPdfPath(pdfPath)
                .setOffScreenSize(getOffscreenPageLimit())
                .setOnPageClickListener(clickListener)
                .create();
        setAdapter(pdfPagerAdapter);
    }

    private OnPageClickListener clickListener = new OnPageClickListener() {

        @Override
        public void onPageTap(View view, float x, float y) {

            if (onVPClickLisetener!=null){
                onVPClickLisetener.onVPClick();
            }

//            Toast.makeText(getContext(),"798",Toast.LENGTH_SHORT).show();
//
//            int item = getCurrentItem();
//            int total = getChildCount();
//
//            if (x < 0.33f && item > 0) {
//                item -= 1;
//                setCurrentItem(item);
//            } else if (x >= 0.67f && item < total - 1) {
//                item += 1;
//                setCurrentItem(item);
//            }
        }
    };

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        try {
//            return super.onInterceptTouchEvent(ev);
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
    public void setOnVPClickLisetener(OnVPClickLisetener onVPClickLisetener){
        this.onVPClickLisetener=onVPClickLisetener;
    }
    public interface  OnVPClickLisetener{
        public void onVPClick();
    }
    public int getCount(){
        return pdfPagerAdapter.getCount();
    }
}
