package com.cy.androidpdf.view.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


/**
 * Created by lenovo on 2017/4/25.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    /*
        Acitivity跳转
         */
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int string_id) {
        Toast.makeText(this, getResources().getString(string_id), Toast.LENGTH_SHORT).show();
    }


    public void startAppcompatActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    public void startAppcompatActivity(Class<?> cls, boolean hasLogined) {
        if (hasLogined) {

            startActivity(new Intent(this, cls));
        } else {
            showToast("请先登录");

        }
    }


    public void initData() {
    }
    /*
    状态栏全透明去阴影
     */

    public void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    /*
    状态栏隐藏
     */

    public void setStatusBarHide() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    /*

     */

    public void replaceFragment(int framelayout_id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(framelayout_id, fragment).commit();
    }
//    /*
//
//     */
//
//    public void addFragment(int framelayout_id, Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(framelayout_id, fragment).addToBackStack(null).commit();
//    }


    public Fragment createFragment(int position) {
        return null;
    }
}
