/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ChoicePhotoPw2
 * Author:   kiwilss
 * Date:     2019-06-19 23:04
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvpretrofit.dialog

import android.annotation.SuppressLint
import android.content.Context

import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mylibrary.core.click
import com.lxj.xpopup.core.BottomPopupView
import kotlinx.android.synthetic.main.pw_normal_modify_header.view.*

/**
 *@FileName: ChoicePhotoPw2
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-19
 * @desc   : {DESCRIPTION}
 */
@SuppressLint("ViewConstructor")
class ChoicePhotoPw(context: Context, private val choiceOnClickListener: ChoiceOnClickListener)
    : BottomPopupView(context) {
    override fun getImplLayoutId(): Int {
        return R.layout.pw_normal_modify_header
    }

    override fun onCreate() {
        super.onCreate()


        tv_pw_modify_header_cancel.click {
            dismiss()
        }

        tv_pw_modify_header_take.click {
            dismiss()
            choiceOnClickListener.takePhoto()
        }

        tv_pw_modify_header_album.click {
            dismiss()
            choiceOnClickListener.openAlbum()
        }

    }


}

interface ChoiceOnClickListener{
    fun takePhoto()
    fun openAlbum()
}