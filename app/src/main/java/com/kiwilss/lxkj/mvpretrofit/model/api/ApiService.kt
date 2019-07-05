/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ApiService
 * Author:   kiwilss
 * Date:     2019-06-24 11:27
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvpretrofit.model.api

import com.kiwilss.lxkj.mvpretrofit.model.BaseBean
import com.kiwilss.lxkj.mvpretrofit.model.bean.Province
import com.kiwilss.lxkj.mvpretrofit.ui.collect.CollectData
import com.kiwilss.lxkj.mvpretrofit.ui.home.HomeBanner
import com.kiwilss.lxkj.mvpretrofit.ui.home.HomeBean
import com.kiwilss.lxkj.mvpretrofit.ui.knowledge.KnowledgeBean
import com.kiwilss.lxkj.mvpretrofit.ui.knowledge.detail.KnowledgeListFgBean
import com.kiwilss.lxkj.mvpretrofit.ui.login.LoginInfo
import com.kiwilss.lxkj.mvpretrofit.ui.navigation.NavigationBean
import com.kiwilss.lxkj.mvpretrofit.ui.project.ProjectFgBean
import com.kiwilss.lxkj.mvpretrofit.ui.project.detail.ProjectListInfo
import com.kiwilss.lxkj.mvpretrofit.ui.search.SearchHotBean
import com.kiwilss.lxkj.mvpretrofit.ui.search_list.SearchListBean
import com.kiwilss.lxkj.mvpretrofit.ui.todo.fragment.ToDoFgBean
import com.kiwilss.lxkj.mvpretrofit.ui.wechat.WeChatTitle
import com.kiwilss.lxkj.mvpretrofit.ui.wechat.detail.WeChatFgDetailBean
import retrofit2.Call
import retrofit2.http.*

/**
 *@FileName: ApiService
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-24
 * @desc   : {DESCRIPTION}
 */
interface ApiService{

    companion object{
        const val BASE_URL = "https://www.wanandroid.com/"
    }

    /**
     * 自带的方法获取省
     * @return Call<List<Province>>
     */
    @GET("http://guolin.tech/api/china")
    fun getProvince(): Call<List<Province>>


    /**
     * 获取首页文章
     * @param page ") page: Int
     * @return BaseBean<List<HomeBean>>
     */
    @GET("article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): BaseBean<HomeBean>

    @GET("banner/json")
    suspend fun getBanner(): BaseBean<List<HomeBanner>>


    /**
     * 登录
     * http://www.wanandroid.com/user/login
     * @param username
     * @param password
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username")username: String,
              @Field("password")password: String): BaseBean<LoginInfo>

    /**
     * 注册
     * http://www.wanandroid.com/user/register
     * @param username
     * @param password
     * @param repassword
     */
    @POST("user/register")
    @FormUrlEncoded
    suspend fun register(@Field("username")username: String,
                 @Field("password")password: String,
                 @Field("repassword")repassword: String): BaseBean<LoginInfo>


    /**
     * 退出登录
     * http://www.wanandroid.com/user/logout/json
     */
    @GET("user/logout/json")
    suspend fun exit(): BaseBean<Any>

    /**
     * 收藏站内文章
     * http://www.wanandroid.com/lg/collect/1165/json
     * @param id article id
     */
    @POST("lg/collect/{id}/json")
    suspend fun addCollect(@Path("id")id: Int) : BaseBean<Any>
    /**
     * 文章列表中取消收藏文章
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     * @param id
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun cancelCollect(@Path("id")id: Int): BaseBean<Any>

    /**
     *  获取收藏列表
     *  http://www.wanandroid.com/lg/collect/list/0/json
     *  @param page
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectList(@Path("page")page: Int): BaseBean<CollectData>

    /**
     * 收藏列表中取消收藏文章
     * http://www.wanandroid.com/lg/uncollect/2805/json
     * @param id
     * @param originId
     */
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    suspend fun cancelCollect(@Path("id")id: Int, @Field("originId")originId: Int) : BaseBean<Any>


    /**
     * 获取知识体系
     * http://www.wanandroid.com/tree/json
     */
    @GET("tree/json")
    suspend fun getKnowledge(): BaseBean<List<KnowledgeBean>>

    @GET("article/list/{page}/json")
        suspend fun getKnowledgeList(@Path("page")page: Int,@Query("cid")cid: Int)
    : BaseBean<KnowledgeListFgBean>


    /**
     * 获取公众号列表
     * http://wanandroid.com/wxarticle/chapters/json
     */
    @GET("wxarticle/chapters/json")
    suspend fun getWeChatTitle(): BaseBean<List<WeChatTitle>>

    /**
     * 获取公众号列表内容详情列表
     * @param id ")id: Int
     * @param page ")page: Int
     * @return BaseBean<WeChatFgDetailBean>
     */
    @GET("wxarticle/list/{id}/{page}/json")
        suspend fun getWeChatDetail(@Path("id")id: Int,@Path("page")page: Int): BaseBean<WeChatFgDetailBean>

    /**
     * 获取导航数据
     * @return BaseBean<NavigationBean>
     */
    @GET("navi/json")
    suspend fun getNaviList(): BaseBean<List<NavigationBean>>

    /**
     * 项目数据
     * http://www.wanandroid.com/project/tree/json
     */
    @GET("project/tree/json")
    suspend fun getProjectData(): BaseBean<List<ProjectFgBean>>

    /**
     * 项目列表数据
     * http://www.wanandroid.com/project/list/1/json?cid=294
     * @param page
     * @param cid
     */
    @GET("project/list/{page}/json")
    suspend fun getProjectList(@Path("page")page: Int,
                       @Query("cid")cid: Int): BaseBean<ProjectListInfo>


    /**
     * V2版本 ： 获取TODO列表数据
     * http://www.wanandroid.com/lg/todo/v2/list/页码/json
     * @param page 页码从1开始，拼接在 url 上
     * @param map
     *          status 状态， 1-完成；0未完成; 默认全部展示；
     *          type 创建时传入的类型, 默认全部展示
     *          priority 创建时传入的优先级；默认全部展示
     *          orderby 1:完成日期顺序；2.完成日期逆序；3.创建日期顺序；4.创建日期逆序(默认)；
     */
    @GET("/lg/todo/v2/list/{page}/json")
    suspend fun getTodoList(@Path("page") page: Int
                            , @QueryMap map: MutableMap<String, Any>)
            : BaseBean<ToDoFgBean>

    /**
     * 4. 仅更新完成状态Todo
     * id: 拼接在链接上，为唯一标识
    status: 0或1，传1代表未完成到已完成，反之则反之。
     * @param id ")id: Int
     * @param status ")status: Int
     */
    @FormUrlEncoded
    @POST("lg/todo/done/{id}/json")
    suspend fun updateItemToDo(@Path("id")id: Int,@Field("status")status: Int): BaseBean<Any>

    /**
     * 删除一条 todo
     * @param id ")id: Int
     * @return BaseBean<Any>
     */
    @POST("lg/todo/delete/{id}/json")
        suspend fun deleteOneToDo(@Path("id")id: Int): BaseBean<Any>

    /**
     * 新增一条 todo
    title: 新增标题（必须）
    content: 新增详情（必须）
    date: 2018-08-01 预定完成时间（不传默认当天，建议传）
    type: 大于0的整数（可选）；
    priority 大于0的整数（可选）；
     * @param map HashMap<String,Any>
     * @return BaseBean<Any>
     */
    @FormUrlEncoded
        @POST("lg/todo/add/json")
        suspend fun addOneToDo(@FieldMap map: HashMap<String,Any>): BaseBean<Any>

    /**更新一条 todo
     * 	id: 拼接在链接上，为唯一标识，列表数据返回时，每个todo 都会有个id标识 （必须）
    title: 更新标题 （必须）
    content: 新增详情（必须）
    date: 2018-08-01（必须）
    status: 0 // 0为未完成，1为完成
    type: ；
    priority: ；
     * @param id ")id: Int
     * @param map HashMap<String,Any>
     * @return BaseBean<Any>
     */
        @FormUrlEncoded
        @POST("lg/todo/update/{id}/json")
        suspend fun updateOneToDo(@Path("id")id: Int,@FieldMap map: HashMap<String,Any>): BaseBean<Any>


    /**
     * 获取搜索热词
     * @return BaseBean<List<SearchHotBean>>
     */
        @GET("hotkey/json")
        suspend fun getSearchHot(): BaseBean<List<SearchHotBean>>

        @FormUrlEncoded
        @POST("article/query/{page}/json")
        suspend fun getSearchList(@Path("page")page: Int,@Field("k")k: String): BaseBean<SearchListBean>

}