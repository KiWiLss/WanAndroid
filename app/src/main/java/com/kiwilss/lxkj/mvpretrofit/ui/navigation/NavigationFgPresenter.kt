/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: NavigationFgPresenter
 * Author:   kiwilss
 * Date:     2019-06-28 08:17
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.navigation

import com.kiwilss.lxkj.mvpretrofit.base.BasePresenter
import com.kiwilss.lxkj.mvpretrofit.config.KEY_FG_NAVIGATION_DATA
import com.kiwilss.lxkj.mvpretrofit.config.KEY_FG_NAVIGATION_ERROR
import com.kiwilss.lxkj.mvpretrofit.model.api.RetrofitHelper
import kotlinx.coroutines.launch

/**
 *@FileName: NavigationFgPresenter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-28
 * @desc   : {DESCRIPTION}
 */
class NavigationFgPresenter: BasePresenter() {

    fun getNavigationList(){
        presenterScope.launch {
            handlerResult(KEY_FG_NAVIGATION_DATA, KEY_FG_NAVIGATION_ERROR){
                RetrofitHelper.apiService.getNaviList()
            }
        }
    }

}