package com.kiwilss.lxkj.mvpretrofit.utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

/**
 * @author : Lss kiwilss
 * @FileName: JUtils
 * @e-mail : kiwilss@163.com
 * @time : 2019-06-27
 * @desc : {DESCRIPTION}
 */
public class JUtils extends AppCompatActivity {

    WeakReference weakReference = new WeakReference<Activity>(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //weakReference.
    }

    String test;

    public void testFori(){
        String a = "hello";
        String b = a + 9;
        String concat = a.concat("hello");

        String[] data = new String[4];
    }

}
