package com.cy.androidpdf.view.activity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cy.androidpdf.R;
import com.cy.androidpdf.model.Constant;
import com.cy.androidpdf.model.utils.LogUtils;
import com.cy.androidpdf.view.base.BaseActivity;
import com.cy.androidpdf.view.base.BaseDialog;
import com.cy.androidpdf.view.customview.pdfview.PdfFrameLayout;
import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnDrawListener;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;

import java.io.File;

public class PDFActivity extends BaseActivity {
    private BaseDialog dialog_pdf;
    private SeekBar seekBar;
    private TextView tv_last, tv_next;
    private int pageCount_lower;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        dialog_pdf = new BaseDialog(this);
        dialog_pdf.config(R.layout.dialog_pdf, Gravity.BOTTOM, 0, true);

        tv_last = (TextView) dialog_pdf.findViewById(R.id.tv_last);
        tv_next = (TextView) dialog_pdf.findViewById(R.id.tv_next);


        seekBar = (SeekBar) dialog_pdf.findViewById(R.id.seekbar);



        final PdfFrameLayout frameLayout = (PdfFrameLayout) findViewById(R.id.activity_pdf);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            final PDFViewPager pdfViewPager = new PDFViewPager(this, getIntent().getStringExtra(Constant.INTENT_2_PDF_ACT));
//
//            seekBar.setMax(pdfViewPager.getCount());
//
//            frameLayout.addView(pdfViewPager);
//            pdfViewPager.setOnVPClickLisetener(new PDFViewPager.OnVPClickLisetener() {
//                @Override
//                public void onVPClick() {
//                    dialog_pdf.show();
//                }
//            });
//
//            pdfViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//                    seekBar.setProgress(position + 1);
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//
//                }
//            });
//
//            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                @Override
//                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar seekBar) {
//
//                }
//
//                @Override
//                public void onStopTrackingTouch(SeekBar seekBar) {
//                    pdfViewPager.setCurrentItem(seekBar.getProgress() - 1);
//
//                }
//            });
//
//            tv_last.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if (pdfViewPager.getCurrentItem()>0){
//
//                        pdfViewPager.setCurrentItem(pdfViewPager.getCurrentItem()- 1);
//                    }
//
//                }
//            });
//            tv_next.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (pdfViewPager.getCurrentItem()<pdfViewPager.getCount()-1){
//
//                        pdfViewPager.setCurrentItem(pdfViewPager.getCurrentItem()+1);
//                    }
//                }
//            });
//
//        } else {
            final PDFView pdfView = new PDFView(this, null);
            frameLayout.addView(pdfView);


            frameLayout.setOnPDFClickListener(new PdfFrameLayout.OnPDFClickListener() {
                @Override
                public void onPDFClick() {
                    dialog_pdf.show();
                }
            });


            pdfView.fromFile(new File(getIntent().getStringExtra(Constant.INTENT_2_PDF_ACT)))
                    .defaultPage(1)
                    .enableSwipe(true)
                    .onDraw(new OnDrawListener() {
                        @Override
                        public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                        }
                    })
                    .onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {

                        }
                    })
                    .onPageChange(new OnPageChangeListener() {
                        @Override
                        public void onPageChanged(int page, int pageCount) {//page从1开始
                            pageCount_lower=pageCount;

                            seekBar.setMax(pageCount);

                            LogUtils.log("max", pdfView.getPageCount());
                            seekBar.setProgress(page);
                            LogUtils.log("page", page);

                        }
                    })
                    .load();


            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    LogUtils.log("seek", seekBar.getProgress());
                    pdfView.jumpTo(seekBar.getProgress());

                }
            });

            tv_last.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pdfView.getCurrentPage()>0){

                        pdfView.jumpTo(pdfView.getCurrentPage());
                    }
                }
            });
            tv_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pdfView.getCurrentPage()<pageCount_lower-1){
                        pdfView.jumpTo(pdfView.getCurrentPage()+2);

                    }
                }
            });
//        }

    }

    @Override
    public void onClick(View v) {

    }
}
