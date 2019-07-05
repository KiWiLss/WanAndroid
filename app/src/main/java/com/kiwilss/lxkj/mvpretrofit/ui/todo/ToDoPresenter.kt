/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ToDoPresenter
 * Author:   kiwilss
 * Date:     2019-06-28 22:03
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.todo

import com.kiwilss.lxkj.mvpretrofit.base.BasePresenter
import com.kiwilss.lxkj.mvpretrofit.config.KEY_TODO_FG_DELETE
import com.kiwilss.lxkj.mvpretrofit.config.KEY_TODO_FG_ERROR
import com.kiwilss.lxkj.mvpretrofit.config.KEY_TODO_FG_LIST
import com.kiwilss.lxkj.mvpretrofit.config.KEY_TODO_FG_UPDATE
import com.kiwilss.lxkj.mvpretrofit.model.api.RetrofitHelper
import kotlinx.coroutines.launch

/**
 *@FileName: ToDoPresenter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-28
 * @desc   : {DESCRIPTION}
 */
class ToDoPresenter: BasePresenter() {
    //-----------activity-----------------



    //-----------fragment---------------

    fun getToDoList(page: Int, map: MutableMap<String,Any>,status: Int){
        presenterScope.launch {
            handlerResult(KEY_TODO_FG_LIST + status.toString(),
                KEY_TODO_FG_ERROR + status.toString()){
                RetrofitHelper.apiService.getTodoList(page,map)
            }
        }
    }

    fun deleteOneToDo(id: Int,status: Int){
        presenterScope.launch {
            handlerResult(KEY_TODO_FG_DELETE + status.toString(),
                KEY_TODO_FG_ERROR + status.toString()){
                RetrofitHelper.apiService.deleteOneToDo(id)
            }
        }
    }

    fun updateOneToDo(id: Int,status: Int,status2: Int){
        presenterScope.launch {
            handlerResult(
                KEY_TODO_FG_UPDATE + status2.toString(),
                KEY_TODO_FG_ERROR + status2.toString()){
                RetrofitHelper.apiService.updateItemToDo(id,status)
            }
        }

    }
}