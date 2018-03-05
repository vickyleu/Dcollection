package com.superfactory.dcollection.Contexts

import android.app.Application

/**
 * Created by vicky on 2018.03.05.
 *
 * @Author vicky
 * @Date 2018年03月05日  10:46:50
 * @ClassName 这里输入你的类名(或用途)
 */
class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationContext.init(this)
    }
}