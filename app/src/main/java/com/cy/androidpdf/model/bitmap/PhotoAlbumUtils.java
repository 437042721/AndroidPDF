package com.cy.androidpdf.model.bitmap;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.cy.androidpdf.model.Constant;
import com.cy.androidpdf.view.activity.ScanActivity;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by lenovo on 2017/7/20.
 */

public class PhotoAlbumUtils {



    /*
    全局异常捕获
     */
    public static void createIntentOfCrashPermission(final Activity activity) {

        //6.0权限动态添加
        // 没有相册权限。

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 申请授权。
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.CRASH_WRITE_EXTERNAL_PEMISSION);
        } else {//有存储权限
            // 存储权限被用户同意，可以去放肆了。
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent=new Intent(activity,ScanActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            },3000);
        }

    }

    public static void showToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

}
