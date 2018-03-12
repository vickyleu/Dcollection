package com.superfactory.dcollection.ui.base

import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.verticalLayout

/**
 * Created by vicky on 2018.03.08.
 *
 * @Author vicky
 * @Date 2018年03月08日  16:54:38
 * @ClassName 这里输入你的类名(或用途)
 */
class TestLayout : BaseLayout<TestBase,String>() {
    override fun builder(): AnkoContext<TestBase>.() -> Unit = {
        verticalLayout {
            owner.finish()
        }
    }
}
