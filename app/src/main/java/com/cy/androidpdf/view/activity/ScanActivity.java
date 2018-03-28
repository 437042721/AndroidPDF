package com.cy.androidpdf.view.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cy.androidpdf.R;
import com.cy.androidpdf.model.Constant;
import com.cy.androidpdf.model.bean.BookcaseBean;
import com.cy.androidpdf.model.utils.DataManagerUtils;
import com.cy.androidpdf.model.utils.LogUtils;
import com.cy.androidpdf.view.adapter.RVAdapter;
import com.cy.androidpdf.view.base.BaseActivity;
import com.cy.androidpdf.view.customview.progress.ScanProgressView;
import com.cy.androidpdf.view.customview.recyclerview.VerticalRecyclerView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ScanActivity extends BaseActivity {
    //    private TextView ;
    private RVAdapter<BookcaseBean> rvAdapter;
    private List<BookcaseBean> list;
    private ScanProgressView scanProgressView;
    private TextView tv_result;
    private Handler handler;
    private LinearLayout layout_content, layout_scan;
    private boolean stop = false;
    private long exitTime = 0;
    private Button btn_scan_cancel;
    private ImageView iv_scan;

    private TextView tv_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//                switch (msg.what) {
//                    case 0:
//                        tv_result.setText("扫描中,已发现" + list.size() + "个文件...");
//
//                        break;
//                }
            }
        };

        setContentView(R.layout.activity_scan);


        tv_all= (TextView) findViewById(R.id.tv_all);

        tv_all.setOnClickListener(this);

        btn_scan_cancel = (Button) findViewById(R.id.btn_scan_cancel);
        btn_scan_cancel.setOnClickListener(this);

        layout_content = (LinearLayout) findViewById(R.id.layout_content);
        layout_scan = (LinearLayout) findViewById(R.id.layout_scan);


        iv_scan = (ImageView) findViewById(R.id.iv_scan);
        iv_scan.setOnClickListener(this);
//        tv_scan = (TextView) findViewById(tv_scan);
//        tv_scan.setOnClickListener(this);


        tv_result = (TextView) findViewById(R.id.tv_result);


        scanProgressView = (ScanProgressView) findViewById(R.id.scan_progress);

        list = new ArrayList<>();

        rvAdapter = new RVAdapter<BookcaseBean>(list) {
            @Override
            public void bindDataToView(MyViewHolder holder, int position, BookcaseBean bean, boolean isSelected) {
                holder.setText(R.id.tv_name, bean.getName());
                holder.setText(R.id.tv_size, bean.getSize());

                if (("." + bean.getType()).equals(Constant.SUFFIX_PDF)) {
                    holder.setImage(R.id.iv_type, R.drawable.pdf);
                }

            }

            @Override
            public int getItemLayoutID(int viewType) {
                return R.layout.item_scan;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public void onItemClick(int position, BookcaseBean bean) {

                File file = new File(bean.getPath());
                if (!file.exists() || (file != null && file.length() == 0)) {
                    showToast("亲，该图书已无效或已被删除~");


                    getContentResolver().delete(Uri.parse("content://media/external/file"),
                            "(" + MediaStore.Files.FileColumns.DATA + " like ?)",
                            new String[]{bean.getPath()});


                    Uri uri = Uri.fromFile(new File(bean.getPath()));
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                    sendBroadcast(intent);

                    rvAdapter.remove(position);
                    return;
                }

                if (("." + bean.getType()).equals(Constant.SUFFIX_PDF)) {

                    Intent intent = new Intent(ScanActivity.this, PDFActivity.class);
                    intent.putExtra(Constant.INTENT_2_PDF_ACT, bean.getPath());
                    startActivity(intent);
                }
            }
        };
        ((VerticalRecyclerView) findViewById(R.id.vrv)).setAdapter(rvAdapter);
        scan();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_scan:
                scan();
                break;
            case R.id.btn_scan_cancel:
                stop = true;
                break;
            case R.id.tv_all:
                stop = false;
                iv_scan.setVisibility(View.GONE);
                tv_all.setVisibility(View.GONE);
                rvAdapter.clear();
                showScan(true);


                scanProgressView.startAnim();

                tv_result.setText("扫描中,已发现0个文件...");

                rvAdapter.clear();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File sdRoot = new File(Environment.getExternalStorageDirectory() + "/");
                        Collection<File> collection = FileUtils.listFiles(sdRoot, new String[]{"pdf"}, true);


                            for (File item : collection) {
                                if (stop==true){
                                    break;
                                }

                                BookcaseBean bookcaseBean = new BookcaseBean();
                                bookcaseBean.setName(item.getName());
                                bookcaseBean.setPath(item.getAbsolutePath());
                                bookcaseBean.setType("pdf");
                                bookcaseBean.setSize(DataManagerUtils.getFormatSize(item.length()));
                                list.add(bookcaseBean);


                                Log.d("Name: ",item.getName()+"");
                                Log.d("Path: ", item.getAbsolutePath() + "");
                            }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                scanProgressView.stopAnimAndReset();

                                if (list.size() == 0) {

                                    layout_scan.setVisibility(View.GONE);

                                    findViewById(R.id.iv_empty).setVisibility(View.VISIBLE);


                                    iv_scan.setVisibility(View.VISIBLE);
                                    tv_all.setVisibility(View.VISIBLE);


                                    return;
                                }


                                showScan(false);

                                rvAdapter.notifyDataSetChanged();

                                iv_scan.setVisibility(View.VISIBLE);
                                tv_all.setVisibility(View.VISIBLE);



                                rvAdapter.notifyDataSetChanged();

                            }
                        });
                    }
                }).start();


                break;


        }
    }

    private void scan() {

        stop = false;
        iv_scan.setVisibility(View.GONE);
        rvAdapter.clear();
        showScan(true);


        scanProgressView.startAnim();

        tv_result.setText("扫描中,已发现0个文件...");


        new Thread(new Runnable() {
            @Override
            public void run() {


                String[] projection = new String[]{MediaStore.Files.FileColumns._ID,
                        MediaStore.Files.FileColumns.DATA,
                        MediaStore.Files.FileColumns.SIZE
                };


                Cursor cursor = getContentResolver().query(
                        Uri.parse("content://media/external/file"),
                        projection, "(" + MediaStore.Files.FileColumns.DATA + " like ?)",
                        new String[]{"%" + Constant.SUFFIX_PDF}, null);

                if (cursor != null && cursor.moveToFirst()) {
                    int idindex = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
                    int dataindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
                    int sizeindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE);


                    do {
                        String path = cursor.getString(dataindex);

                        int dot = path.lastIndexOf("/");
                        String name = path.substring(dot + 1);
                        String type = name.substring(name.lastIndexOf(".") + 1);

                        if (name.lastIndexOf(".") > 0) {
                            name = name.substring(0, name.lastIndexOf("."));
                        }


                        if (cursor.getLong(sizeindex) > 0) {


                            BookcaseBean bookcaseBean = new BookcaseBean();
                            bookcaseBean.setName(name);
                            bookcaseBean.setPath(path);
                            bookcaseBean.setType(type);
                            bookcaseBean.setSize(DataManagerUtils.getFormatSize(cursor.getLong(sizeindex)));
                            list.add(bookcaseBean);
                            LogUtils.log("size", list.size());
                        }


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_result.setText("扫描中,已发现" + list.size() + "个文件...");


                            }
                        });


                    } while (cursor.moveToNext() && stop == false);

                    cursor.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            scanProgressView.stopAnimAndReset();

                            if (list.size() == 0) {

                                layout_scan.setVisibility(View.GONE);

                                findViewById(R.id.iv_empty).setVisibility(View.VISIBLE);


                                iv_scan.setVisibility(View.VISIBLE);


                                return;
                            }


                            showScan(false);

                            rvAdapter.notifyDataSetChanged();

                            iv_scan.setVisibility(View.VISIBLE);


                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btn_scan_cancel.setClickable(true);


                            scanProgressView.stopAnimAndReset();


                            if (list.size() == 0) {

                                layout_scan.setVisibility(View.GONE);

                                findViewById(R.id.iv_empty).setVisibility(View.VISIBLE);


                                iv_scan.setVisibility(View.VISIBLE);


                                return;
                            }

                            showScan(false);

                            rvAdapter.clear();

                            iv_scan.setVisibility(View.VISIBLE);


                        }
                    });
                }
            }
        }).start();
    }

    private void showScan(boolean showScan) {
        findViewById(R.id.iv_empty).setVisibility(View.GONE);

        if (showScan) {


            layout_content.setVisibility(View.INVISIBLE);
            layout_scan.setVisibility(View.VISIBLE);

        } else {


            layout_content.setVisibility(View.VISIBLE);
            layout_scan.setVisibility(View.GONE);

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplicationContext(), "2秒内再按一次返回桌面", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                //点击返回键返回桌面而不是退出程序

                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
