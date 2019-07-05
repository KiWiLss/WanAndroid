/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: LoginActivity
 * Author:   kiwilss
 * Date:     2019-06-25 23:13
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.login

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.LogUtils
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mvpretrofit.base.BaseActivity
import com.kiwilss.lxkj.mvpretrofit.config.*
import com.kiwilss.lxkj.mylibrary.bus.LiveDataBus
import com.kiwilss.lxkj.mylibrary.core.click
import com.kiwilss.lxkj.mylibrary.core.edit
import com.kiwilss.lxkj.mylibrary.core.sp
import kotlinx.android.synthetic.main.activity_login.*

/**
 *@FileName: LoginActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-25
 * @desc   : {DESCRIPTION}
 */
class LoginActivity: BaseActivity<LoginPresenter>() {
    override fun getErrorKey(): String {
        return KEY_LOGIN_ERROR
    }

    lateinit var mAccount: String
    lateinit var mPwd: String

    override fun initPresenter(): LoginPresenter = LoginPresenter()

    override fun initOnClick() {
        stv_login_login.click {
            //检查输入
            if (checkInput()) {
                showLoadingDiloag(LOADING_HINT)
                mPresenter.login(mAccount,mPwd)
            }
        }

        stv_login_register.click {
            //检查输入
            if (checkInput()) {
                showLoadingDiloag(LOADING_HINT)
                mPresenter.register(mAccount,mPwd)
            }
        }
    }

    private fun checkInput() : Boolean{
        mAccount = til_login_account.editText?.text.toString()
        if (mAccount.isEmpty()){
            til_login_account.error = getString(R.string.please_input_account)
            return false
        }
        mPwd = til_login_pwd.editText?.text.toString()
        if (mPwd.isEmpty()){
            til_login_pwd.error = getString(R.string.please_input_pwd)
            return false
        }
            return true
    }

    override fun initInterface(savedInstanceState: Bundle?) {
        //登录结果
        LiveDataBus.with<LoginInfo>(KEY_LOGIN_LOGIN).observe(this, Observer {
            //登录成功,刷新首页
            LogUtils.e(JSON.toJSONString(it))
            //sp().putBoolean("isLogin",true)
            handlerResult(it)
        })

        //注册结果
        LiveDataBus.with<LoginInfo>(KEY_LOGIN_REGISTER).observe(this, Observer {
            //注册成功,刷新首页
            handlerResult(it)
            //sp().putBoolean("isLogin",true)
        })


    }

    private fun handlerResult(it: LoginInfo?) {
        LogUtils.e(JSON.toJSONString(it))
        dismissLoadingDiloag()
        sp().edit {
            putBoolean(Constant.SP_IS_LOGIN,true)
            putString(Constant.SP_USERNAME,it?.username)
        }
        LiveDataBus.with<Boolean>(KEY_HOME_IS_LOGIN).setValue(true)
        finish()
    }

    override fun getLayoutId(): Int = R.layout.activity_login
}