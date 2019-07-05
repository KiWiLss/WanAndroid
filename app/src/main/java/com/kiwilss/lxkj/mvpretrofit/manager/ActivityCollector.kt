/**
 * Copyright (C), 2017-2018, XXX有限公司
 * FileName: ActivityCollector
 * Author:   kiwilss
 * Date:     2018/11/19 14:10
 * Description: activity collect
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvpretrofit.manager

import android.app.Activity

/**
 *@FileName: ActivityCollector
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2018/11/19
 * @desc   : activity collect 做成单利
 */
class ActivityCollector private constructor(){

    companion object {
        fun instance() : ActivityCollector = ActivityCollectorSingle.instance
        //val instance: ActivityCollector by lazy { ActivityCollector() }
    }


    private val activityList = arrayListOf<Activity>()

    private object ActivityCollectorSingle{
        val instance = ActivityCollector()
    }


    fun addActivity(activity: Activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity)
        }
    }

    fun removeActivity(activity: Activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity)
            activity.finish()
        }
    }

    /**
     * 移除但不关闭
     * @param activity Activity
     */
    fun removeActivity2(activity: Activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity)
        }
    }

    fun finishAll() {
        for (i in activityList.indices) {
            if (!activityList[i].isFinishing) {
                activityList[i].finish()
            }
        }
    }

    fun finishAnyOne(clz: Class<*>) {//销毁任意一个
        for (i in activityList.indices) {
            if (clz == activityList[i].javaClass) {
                if (!activityList[i].isFinishing) {
                    activityList[i].finish()
                }
            }
        }
    }


}