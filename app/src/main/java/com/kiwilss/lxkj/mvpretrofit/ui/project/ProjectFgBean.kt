/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ProjectFgBean
 * Author:   kiwilss
 * Date:     2019-06-28 14:20
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvpretrofit.ui.project
import com.squareup.moshi.Json


/**
 *@FileName: ProjectFgBean
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-28
 * @desc   : {DESCRIPTION}
 */
data class ProjectFgBean(
    @Json(name = "children")
    val children: List<Any>,
    @Json(name = "courseId")
    val courseId: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "order")
    val order: Int,
    @Json(name = "parentChapterId")
    val parentChapterId: Int,
    @Json(name = "userControlSetTop")
    val userControlSetTop: Boolean,
    @Json(name = "visible")
    val visible: Int
)