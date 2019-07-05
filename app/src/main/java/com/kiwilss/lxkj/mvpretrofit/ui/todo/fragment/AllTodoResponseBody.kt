package com.kiwilss.lxkj.mvpretrofit.ui.todo.fragment

import com.chad.library.adapter.base.entity.SectionEntity
import com.squareup.moshi.Json
import java.io.Serializable



// 所有TODO，包括待办和已完成
data class AllTodoResponseBody(
        @Json(name = "type") val type: Int,
        @Json(name = "doneList") val doneList: MutableList<TodoListBean>,
        @Json(name = "todoList") val todoList: MutableList<TodoListBean>
)

data class TodoListBean(
        @Json(name = "date") val date: Long,
        @Json(name = "todoList") val todoList: MutableList<TodoBean>
)

// TODO实体类
data class TodoBean(
        @Json(name = "id") val id: Int,
        @Json(name = "completeDate") val completeDate: String,
        @Json(name = "completeDateStr") val completeDateStr: String,
        @Json(name = "content") val content: String,
        @Json(name = "date") val date: Long,
        @Json(name = "dateStr") val dateStr: String,
        @Json(name = "status") val status: Int,
        @Json(name = "title") val title: String,
        @Json(name = "type") val type: Int,
        @Json(name = "userId") val userId: Int,
        @Json(name = "priority") val priority: Int
)



class TodoDataBean : SectionEntity<Data> {

    constructor(isHeader: Boolean, header: String): super(isHeader,header)

        constructor(todoBean: Data): super(todoBean)
}


data class ToDoFgBean(
    @Json(name = "curPage")
    val curPage: Int,
    @Json(name = "datas")
    val datas: List<Data>,
    @Json(name = "offset")
    val offset: Int,
    @Json(name = "over")
    val over: Boolean,
    @Json(name = "pageCount")
    val pageCount: Int,
    @Json(name = "size")
    val size: Int,
    @Json(name = "total")
    val total: Int
)

data class Data(
    @Json(name = "completeDate")
    val completeDate: Any,
    @Json(name = "completeDateStr")
    val completeDateStr: String,
    @Json(name = "content")
    val content: String,
    @Json(name = "date")
    val date: Long,
    @Json(name = "dateStr")
    val dateStr: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "priority")
    val priority: Int,
    @Json(name = "status")
    val status: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "type")
    val type: Int,
    @Json(name = "userId")
    val userId: Int
): Serializable

