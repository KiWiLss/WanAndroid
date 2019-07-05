package com.kiwilss.lxkj.mvpretrofit.dialog.loading;

import android.content.Context;
import android.support.annotation.NonNull;
import com.kiwilss.lxkj.mvpretrofit.R;
import com.lxj.xpopup.core.CenterPopupView;

/**
 * @author : Lss kiwilss
 * @FileName: CustomPopup
 * @e-mail : kiwilss@163.com
 * @time : 2019-05-31
 * @desc : {DESCRIPTION}白花不带字
 */
public class LoadingPwOne extends CenterPopupView {
    //private TextView tv_title;

    public LoadingPwOne(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_center_impl_loading;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();

//        tv_title = findViewById(com.lxj.xpopup.R.id.tv_title);
//        if(title!=null){
//            tv_title.setVisibility(VISIBLE);
//            tv_title.setText(title);
//        }
    }

//    private String title;
//    public LoadingPwOne setTitle(String title){
//        this.title = title;
//        return this;
//    }
}
