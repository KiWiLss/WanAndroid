/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: BaseModel
 * Author:   kiwilss
 * Date:     2019-06-24 11:24
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvpretrofit.model

import com.squareup.moshi.Json

/**
 *@FileName: BaseModel
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-24
 * @desc   : {DESCRIPTION}
 */
data class BaseBean<T>(@Json(name = "data") val data: T,
                         @Json(name = "errorCode") val errorCode: Int,
                         @Json(name = "errorMsg") val errorMsg: String)