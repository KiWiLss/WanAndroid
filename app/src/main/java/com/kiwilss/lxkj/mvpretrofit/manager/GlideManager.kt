package com.kiwilss.lxkj.mvpretrofit.manager


class GlideManager private constructor() {

    private object GlideUtilsSingle {
        val GLIDE_UTILS = GlideManager()
    }
//
//    /**
//     * 一般的加载
//     */
//    fun loadImg(context: Context, url: String, view: ImageView) {
//        Glide.with(context).load(url).into(view)
//
//    }
//
//    /**
//     * 加载图片带占位图
//     */
//    fun loadImg(context: Context, url: String, view: ImageView, placeholderId: Int) {
//        val options = RequestOptions().placeholder(placeholderId)
//        Glide.with(context).load(url).apply(options).into(view)
//    }
//
//    /**
//     * 加载图片带加载错误图片
//     */
//    fun loadImgError(context: Context, url: String, view: ImageView, errorId: Int) {
//        val options = RequestOptions().error(errorId)
//        Glide.with(context).load(url).apply(options).into(view)
//    }
//
//    /**
//     * 加载图片带,占位图,加载错误图片
//     */
//    fun loadImg(context: Context, url: String,
//                view: ImageView, placeholderId: Int, errorId: Int) {
//        val options = RequestOptions()
//                .placeholder(placeholderId).error(errorId)
//        Glide.with(context).load(url).apply(options).into(view)
//    }
//
//    /**
//     * 一般的加载,圆形图
//     */
//    fun loadCircleImg(context: Context, url: String, view: ImageView) {
//        val options = RequestOptions().circleCrop()
//        Glide.with(context).load(url).apply(options).into(view)
//    }
//
//    /**
//     * 一般的加载,圆形图,带占位图
//     */
//    fun loadCircleImg(context: Context, url: String, view: ImageView, placeholderId: Int) {
//        val options = RequestOptions().placeholder(placeholderId).circleCrop()
//        Glide.with(context).load(url).apply(options).into(view)
//    }
//
//    /**
//     * 一般的加载,圆形图,带加载错误图
//     */
//    fun loadCircleImgError(context: Context, url: String, view: ImageView, errorId: Int) {
//        val options = RequestOptions().error(errorId).circleCrop()
//        Glide.with(context).load(url).apply(options).into(view)
//    }
//
//    /**
//     * 一般的加载,圆形图,带,占位图,加载错误图
//     */
//    fun loadCircleImg(context: Context, url: String, view: ImageView, placeholder: Int, errorId: Int) {
//        val options = RequestOptions().placeholder(placeholder).error(errorId).circleCrop()
//        //Glide.with(context).load(url).apply(options).into(view);
//        load(context, url, view, options)
//    }
//
//    private fun load(context: Context, url: String, view: ImageView, options: RequestOptions) {
//        Glide.with(context).load(url).apply(options).into(view)
//    }
//
//    /**
//     * 一般的加载,正方形
//     */
//    fun loadSquareImg(context: Context, url: String, view: ImageView) {
//        Glide.with(context).load(url)
//                .apply(bitmapTransform(CropSquareTransformation()))
//                .into(view)
//    }
//
//    /**
//     * 一般的加载,正方形,占位图
//     */
//    fun loadSquareImg(context: Context, url: String, view: ImageView, placeholderId: Int) {
//        Glide.with(context).load(url)
//                .apply(bitmapTransform(CropSquareTransformation()).placeholder(placeholderId))
//                .into(view)
//    }
//
//    /**
//     * 一般的加载,正方形,错误图
//     */
//    fun loadSquareImgError(context: Context, url: String, view: ImageView, errorId: Int) {
//        Glide.with(context).load(url)
//                .apply(bitmapTransform(CropSquareTransformation()).error(errorId))
//                .into(view)
//    }
//
//    /**
//     * 一般的加载,正方形,错误图,占位图
//     */
//    fun loadSquareImg(context: Context, url: String, view: ImageView, errorId: Int, placeholderId: Int) {
//        Glide.with(context).load(url)
//                .apply(bitmapTransform(CropSquareTransformation()).placeholder(placeholderId).error(errorId))
//                .into(view)
//    }
//
//    /**
//     * 一般的加载,圆角
//     */
//    fun loadRoundeImg(context: Context, url: String, view: ImageView, radius: Int, cornerType: RoundedCornersTransformation.CornerType) {
//        Glide.with(context).load(url)
//                .apply(bitmapTransform(RoundedCornersTransformation(radius, 0,
//                        cornerType)))
//                .into(view)
//    }
//
//    /**
//     * 一般的加载,圆角,占位图
//     */
//    fun loadRoundeImg(context: Context, url: String, view: ImageView, radius: Int, cornerType: RoundedCornersTransformation.CornerType, placeholderId: Int) {
//        Glide.with(context).load(url)
//                .apply(bitmapTransform(RoundedCornersTransformation(radius, 0,
//                        cornerType)).placeholder(placeholderId))
//                .into(view)
//    }
//
//    /**
//     * 一般的加载,圆角,占位图
//     */
//    fun loadRoundeImgError(context: Context, url: String, view: ImageView, radius: Int, cornerType: RoundedCornersTransformation.CornerType, errorId: Int) {
//        Glide.with(context).load(url)
//                .apply(bitmapTransform(RoundedCornersTransformation(radius, 0,
//                        cornerType)).error(errorId))
//                .into(view)
//    }
//
//    /**
//     * 一般的加载,圆角,占位图
//     */
//    fun loadRoundeImg(context: Context, url: String, view: ImageView, radius: Int, cornerType: RoundedCornersTransformation.CornerType, errorId: Int, placeholderId: Int) {
//        Glide.with(context).load(url)
//                .apply(bitmapTransform(RoundedCornersTransformation(radius, 0,
//                        cornerType)).placeholder(placeholderId).error(errorId))
//                .into(view)
//    }
//
//
//    /**
//     * 简单加载本地图片
//     */
//    fun loadResource(context: Context, imageView: ImageView, resourceId: Int) {
//        Glide.with(context).load(resourceId).into(imageView)
//    }
//
//    /**
//     * 简单加载本地圆形图片
//     */
//    fun loadResourceCircle(context: Context, imageView: ImageView, resourceId: Int) {
//        val options = RequestOptions().circleCrop()
//        Glide.with(context).load(resourceId).apply(options).into(imageView)
//    }
//
//    /**
//     * 简单加载本地圆形图片,占位图
//     */
//    fun loadResourceCircle(context: Context, imageView: ImageView, resourceId: Int, placeholder: Int) {
//        val options = RequestOptions().placeholder(placeholder).circleCrop()
//        Glide.with(context).load(resourceId).apply(options).into(imageView)
//    }
//
//    /**
//     * 简单加载本地圆形图片,错误图
//     */
//    fun loadResourceCircleError(context: Context, imageView: ImageView, resourceId: Int, errorId: Int) {
//        val options = RequestOptions().error(errorId).circleCrop()
//        Glide.with(context).load(resourceId).apply(options).into(imageView)
//    }
//
//    /**
//     * 简单加载本地圆形图片,错误图,占位图
//     */
//    fun loadResourceCircle(context: Context, imageView: ImageView, resourceId: Int, errorId: Int, placeholderId: Int) {
//        val options = RequestOptions().placeholder(placeholderId).error(errorId).circleCrop()
//        Glide.with(context).load(resourceId).apply(options).into(imageView)
//    }
//
//    /**
//     * 简单加载本地图片,占位图
//     */
//    fun loadResource(context: Context, imageView: ImageView, resourceId: Int, placeholderId: Int) {
//        val requestOptions = RequestOptions().placeholder(placeholderId)
//        Glide.with(context).load(resourceId).apply(requestOptions).into(imageView)
//    }
//
//    /**
//     * 简单加载本地图片,错误图
//     */
//    fun loadResourceError(context: Context, imageView: ImageView, resourceId: Int, errorId: Int) {
//        val requestOptions = RequestOptions().error(errorId)
//        Glide.with(context).load(resourceId).apply(requestOptions).into(imageView)
//    }
//
//    /**
//     * 简单加载本地图片,占位图,错误图
//     */
//    fun loadResource(context: Context, imageView: ImageView,
//                     resourceId: Int, placeholderId: Int, errorId: Int) {
//        val requestOptions = RequestOptions()
//                .placeholder(placeholderId).error(errorId)
//        Glide.with(context).load(resourceId).apply(requestOptions).into(imageView)
//    }
//
//    /**
//     * 简单加载本地图片 ,圆角
//     */
//    fun loadImgResourceRound(context: Context, resource: Int, img: ImageView?,
//                             round: Int, cornerType: RoundedCornersTransformation.CornerType) {
//        if (img != null) {
//            val requestOptions = RequestOptions()
//                    .transform(RoundedCornersTransformation(round, 0,
//                            cornerType))
//            Glide.with(context).load(resource).apply(requestOptions).into(img)
//        }
//    }
//
//    /**
//     * 清理所有缓存,在子线程中执行
//     */
//    fun clear(context: Context) {
//        //理磁盘缓存 需要在子线程中执行
//        Glide.get(context).clearDiskCache()
//        //清理内存缓存  可以在UI主线程中进行
//        Glide.get(context).clearMemory()
//    }
//
//    /**清理磁盘缓存
//     */
//    fun GuideClearDiskCache(mContext: Context) {
//        //理磁盘缓存 需要在子线程中执行
//        Glide.get(mContext).clearDiskCache()
//    }
//
//    /**清理内存缓存
//     */
//    fun GuideClearMemory(mContext: Context) {
//        //清理内存缓存  可以在UI主线程中进行
//        Glide.get(mContext).clearMemory()
//    }

    companion object {
           fun  getInstance() = GlideUtilsSingle.GLIDE_UTILS
    }



}