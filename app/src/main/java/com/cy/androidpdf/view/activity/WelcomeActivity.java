package com.cy.androidpdf.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.cy.androidpdf.R;
import com.cy.androidpdf.model.bitmap.PhotoAlbumActivity;
import com.cy.androidpdf.model.bitmap.PhotoAlbumUtils;

public class WelcomeActivity extends PhotoAlbumActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //异常捕获存储权限

        PhotoAlbumUtils.createIntentOfCrashPermission(this);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        onMyRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onClick(View v) {

    }
}
