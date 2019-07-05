/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: WebViewPresenter
 * Author:   kiwilss
 * Date:     2019-06-25 21:26
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.webview

import com.kiwilss.lxkj.mvpretrofit.base.BasePresenter
import com.kiwilss.lxkj.mvpretrofit.config.KEY_WEBVIEW_ADD
import com.kiwilss.lxkj.mvpretrofit.config.KEY_WEBVIEW_ERROR
import com.kiwilss.lxkj.mvpretrofit.model.api.RetrofitHelper
import kotlinx.coroutines.launch

/**
 *@FileName: WebViewPresenter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-25
 * @desc   : {DESCRIPTION}
 */
class WebViewPresenter: BasePresenter() {

    fun collectArticle(id: Int){
        presenterScope.launch {
            handlerResult(KEY_WEBVIEW_ADD, KEY_WEBVIEW_ERROR){
                RetrofitHelper.apiService.addCollect(id)
            }
        }
    }


}