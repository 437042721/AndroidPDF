package com.cy.androidpdf.model.bitmap;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.cy.androidpdf.model.Constant;
import com.cy.androidpdf.view.activity.ScanActivity;
import com.cy.androidpdf.view.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lenovo on 2017/9/8.
 */

public abstract class PhotoAlbumActivity extends BaseActivity {

    public void onMyRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constant.CRASH_WRITE_EXTERNAL_PEMISSION://异常捕获存储请求返回
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 存储权限被用户同意，可以去放肆了。
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            startAppcompatActivity(ScanActivity.class);
                            finish();
                        }
                    },3000);
                } else {
                    showToast("存储权限被禁用，您将无法使用该APP");
                    //异常捕获存储权限

                    PhotoAlbumUtils.createIntentOfCrashPermission(this);

                }
                break;
        }
    }


}
