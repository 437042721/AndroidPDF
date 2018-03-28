package com.cy.androidpdf.model.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by lenovo on 2017/7/31.
 */

public class InitUtils {

    public static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences("YIGOUDE", Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getSP(context).edit();
    }

}
