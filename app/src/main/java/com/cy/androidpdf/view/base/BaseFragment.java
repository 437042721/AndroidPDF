package com.cy.androidpdf.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.cy.androidpdf.R;


/**
 * Created by lenovo on 2017/6/22.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public AppCompatActivity myActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = (AppCompatActivity) getActivity();


    }
 /*

     */

    public void replaceFragment(int framelayout_id, Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(framelayout_id, fragment).commitAllowingStateLoss();
    }
   /*

     */

    //    public void addFragment(int framelayout_id, Fragment fragment) {
//        FragmentManager fragmentManager = getChildFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(framelayout_id, fragment).addToBackStack(null).commit();
//    }
    public void popupFragment() {

        if (myActivity.getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finishActivity();
            return;
        }
        myActivity.getSupportFragmentManager().popBackStack();

    }


    public void startFragment(int framelayout_id, Fragment fragment) {
        FragmentTransaction fragmentTransaction = myActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out);
        fragmentTransaction.replace(framelayout_id, fragment).addToBackStack(null).commit();

    }

    public void finishActivity() {
        myActivity.finish();

    }

    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int string_id) {
        Toast.makeText(getContext(), getContext().getResources().getString(string_id), Toast.LENGTH_SHORT).show();
    }


    public Fragment createFragment(int position) {
        return null;
    }


}
