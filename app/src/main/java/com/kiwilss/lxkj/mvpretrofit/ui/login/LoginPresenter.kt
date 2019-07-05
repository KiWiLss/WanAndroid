/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: LoginPresenter
 * Author:   kiwilss
 * Date:     2019-06-25 23:14
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.login

import com.kiwilss.lxkj.mvpretrofit.base.BasePresenter
import com.kiwilss.lxkj.mvpretrofit.config.KEY_LOGIN_ERROR
import com.kiwilss.lxkj.mvpretrofit.config.KEY_LOGIN_LOGIN
import com.kiwilss.lxkj.mvpretrofit.config.KEY_LOGIN_REGISTER
import com.kiwilss.lxkj.mvpretrofit.model.api.RetrofitHelper
import kotlinx.coroutines.launch

/**
 *@FileName: LoginPresenter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-25
 * @desc   : {DESCRIPTION}
 */
class LoginPresenter: BasePresenter() {

    fun login(account: String, pwd: String){
        presenterScope.launch {
            handlerResult(KEY_LOGIN_LOGIN, KEY_LOGIN_ERROR){
                RetrofitHelper.apiService.login(account,pwd)
            }
        }

    }

    fun register(account: String, pwd: String){

        presenterScope.launch {
            handlerResult(KEY_LOGIN_REGISTER, KEY_LOGIN_ERROR){
                RetrofitHelper.apiService.register(account,pwd,pwd)
            }
        }
    }
}