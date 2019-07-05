/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: WeChatFgPresenter
 * Author:   kiwilss
 * Date:     2019-06-27 21:17
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.wechat

import com.kiwilss.lxkj.mvpretrofit.base.BasePresenter
import com.kiwilss.lxkj.mvpretrofit.config.*
import com.kiwilss.lxkj.mvpretrofit.model.api.RetrofitHelper
import kotlinx.coroutines.launch

/**
 *@FileName: WeChatFgPresenter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-27
 * @desc   : {DESCRIPTION}
 */
class WeChatFgPresenter: BasePresenter() {

    //-------------------wechat fg------------------

    fun getWeChatTitles(){
        presenterScope.launch {
            handlerResult(KEY_FG_WECHAT_TITLE, KEY_FG_WECHAT_ERROR){
                RetrofitHelper.apiService.getWeChatTitle()
            }
        }
    }

    //------------------wechat fg detail-------------------

    fun getWeChatDetail(id:Int, page: Int,type: Int){
        presenterScope.launch {
            handlerResult(KEY_FG_WECHAT_DETAIL_DATA+type.toString(),
                KEY_FG_WECHAT_DETAIL_ERROR+type.toString()){
                    RetrofitHelper.apiService.getWeChatDetail(id,page)
            }
        }
    }

    fun collectArticle(id: Int,type: Int){
        val key = KEY_FG_WECHAT_DETAIL_ADD + type.toString()
        val keyError = KEY_FG_WECHAT_DETAIL_ERROR  + type.toString()
        presenterScope.launch {
            handlerResult(key, keyError){
                RetrofitHelper.apiService.addCollect(id)
            }
        }
    }

    fun cancelCollect(id: Int,type: Int){
        val key = KEY_FG_WECHAT_DETAIL_CANCEL + type.toString()
        val keyError = KEY_FG_WECHAT_DETAIL_ERROR  + type.toString()
        presenterScope.launch {
            handlerResult(key, keyError){
                RetrofitHelper.apiService.cancelCollect(id)
            }
        }
    }
}