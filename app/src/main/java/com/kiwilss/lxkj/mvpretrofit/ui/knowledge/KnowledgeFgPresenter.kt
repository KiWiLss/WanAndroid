/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: KnowledgeFgPresenter
 * Author:   kiwilss
 * Date:     2019-06-26 23:00
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.knowledge

import com.blankj.utilcode.util.LogUtils
import com.kiwilss.lxkj.mvpretrofit.base.BasePresenter
import com.kiwilss.lxkj.mvpretrofit.config.*
import com.kiwilss.lxkj.mvpretrofit.model.api.RetrofitHelper
import kotlinx.coroutines.launch

/**
 *@FileName: KnowledgeFgPresenter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-26
 * @desc   : {DESCRIPTION}
 */
class KnowledgeFgPresenter: BasePresenter() {

    //-----------------knowledge fg-------------------------
        fun getKnowledgeData(){
            presenterScope.launch {
                handlerResult(KEY_KNOWLEDGE_FG_DATA, KEY_KNOWLEDGE_FG_ERROR){
                    RetrofitHelper.apiService.getKnowledge()
                }
            }
        }


    //------------------knowledgelistactivity-------------------


    //-------------------------knowledge list fg ------------------

    fun getKnowledgeListData(page: Int,cid: Int,type: Int){
        val key = KEY_KNOWLEDGE_List_FG_DATA + type.toString()
        val keyError = KEY_KNOWLEDGE_List_FG_ERROR  + type.toString()
        LogUtils.e(key)
        presenterScope.launch {
            handlerResult(key, keyError){
                RetrofitHelper.apiService.getKnowledgeList(page,cid)
            }
        }
    }

    fun getKnowledgeListData(page: Int,cid: Int){
        presenterScope.launch {
            handlerResult(KEY_KNOWLEDGE_List_FG_DATA, KEY_KNOWLEDGE_List_FG_ERROR){
                RetrofitHelper.apiService.getKnowledgeList(page,cid)
            }
        }
    }

    fun collectArticle(id: Int,type: Int){
        val key = KEY_KNOWLEDGE_List_FG_ADD + type.toString()
        val keyError = KEY_KNOWLEDGE_List_FG_ERROR  + type.toString()
        presenterScope.launch {
            handlerResult(key, keyError){
                RetrofitHelper.apiService.addCollect(id)
            }
        }
    }

    fun cancelCollect(id: Int,type: Int){
        val key = KEY_KNOWLEDGE_List_FG_CANCEL + type.toString()
        val keyError = KEY_KNOWLEDGE_List_FG_ERROR  + type.toString()
        presenterScope.launch {
            handlerResult(key, keyError){
                RetrofitHelper.apiService.cancelCollect(id)
            }
        }
    }

}