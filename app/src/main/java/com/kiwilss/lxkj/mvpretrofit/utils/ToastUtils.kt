/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ToastUtils
 * Author:   kiwilss
 * Date:     2019-06-11 15:21
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvpretrofit.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.Fragment
import com.coder.zzq.smartshow.toast.SmartToast

/**
 *@FileName: ToastUtils
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-11
 * @desc   : {DESCRIPTION}
 */

/** toast相关 **/
fun Context.toast(msg: CharSequence?) = SmartToast.show(msg)

fun Context.toast(res: Int) = SmartToast.show(res)

fun Context.toastS(msg: CharSequence?) = SmartToast.success(msg)

fun Context.toastS(res: Int) = SmartToast.success(res)

fun Context.toastE(msg: CharSequence?) = SmartToast.error(msg)

fun Context.toastE(res: Int) = SmartToast.error(res)



fun Context.toastL(msg: CharSequence) = SmartToast.showLong(msg)

fun Context.toastL(res: Int) = SmartToast.showLong(res)



fun Fragment.toast(msg: CharSequence?) = SmartToast.show(msg)

fun Fragment.toast(res: Int) = SmartToast.show(res)

fun Fragment.toastS(msg: CharSequence?) = SmartToast.success(msg)

fun Fragment.toastS(res: Int) = SmartToast.success(res)

fun Fragment.toastE(msg: CharSequence?) = SmartToast.error(msg)

fun Fragment.toastE(res: Int) = SmartToast.error(res)


fun Fragment.toastL(msg: CharSequence) = SmartToast.showLong(msg)

fun Fragment.toastL(res: Int) = SmartToast.showLong(res)

/**
 * 跳到浏览器
 * @receiver Context
 * @param url String
 * @param newTask Boolean
 * @return Boolean
 */
fun Context.browse(url: String, newTask: Boolean = false): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        if (newTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        false
    }
}

/**
 * 原生分享
 * @receiver Context
 * @param text String
 * @param subject String
 * @return Boolean
 */
fun Context.share(text: String, subject: String = ""): Boolean {
    try {
        val intent = Intent(android.content.Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(android.content.Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, null))
        return true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        return false
    }
}

class ToastUtils{
    companion object{




    }
}