package com.superfactory.dcollection.ui.layout

import android.support.design.widget.CollapsingToolbarLayout
import android.text.InputType
import com.benny.library.kbinding.view.ViewBinderComponent
import com.superfactory.dcollection.R
import com.superfactory.dcollection.ui.base.TesrBase
import com.superfactory.dcollection.ui.controller.MainActivity
import com.superfactory.library.extension.collapseModePin
import com.superfactory.library.theme.colorPrimary
import com.superfactory.library.theme.colorPrimaryDark
import com.superfactory.library.theme.observe
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.*

/**
 * Created by vicky on 2018.03.08.
 *
 * @Author vicky
 * @Date 2018年03月08日  16:23:04
 * @ClassName 这里输入你的类名(或用途)
 */

class SampleLayoutUI() : ViewBinderComponent<TesrBase> {
    override fun builder(): AnkoContext<*>.() -> Unit =  {
        coordinatorLayout {
            themedAppBarLayout(R.style.AppTheme_AppBarOverlay){
                //observe(colorPrimary){backgroundColor = it}
                collapsingToolbarLayout{
//                    observe(colorPrimary){ setContentScrimColor(it) }
//                    observe(colorPrimaryDark){ setStatusBarScrimResource(it) }
                    toolbar {
                        collapseModePin()
                    }.lparams(matchParent,R.attr.actionBarSize)
                    fitsSystemWindows = true
                }.lparams(matchParent, matchParent){  }
                fitsSystemWindows = true
            }.lparams(matchParent,R.dimen.app_bar_height){  }


            //原来的include
            verticalLayout {
                textView {
                    padding = dip(5)
                    text = resources.getText(R.string.info_login)
                }.lparams(matchParent, wrapContent)
                textInputLayout {
                    editText {
                        inputType = InputType.TYPE_CLASS_TEXT
                        maxLines = 1
                        singleLine = true
                    }.lparams(matchParent, wrapContent)
                }.lparams(matchParent, wrapContent)
                textInputLayout {
                    editText {
                        hint = resources.getText(R.string.prompt_password)
                        maxLines = 1
                        singleLine = true

                    }.lparams(matchParent, wrapContent) {  }
                }.lparams(matchParent, wrapContent) {  }
                button{
                    padding = dip(12)
                    text = resources.getText(R.string.login)
                }.lparams(matchParent, wrapContent){topMargin = dip(10)}
                button {
                    padding = dip(12)
                    text = resources.getText(R.string.register)
                }.lparams(matchParent, wrapContent){topMargin = dip(5)}
            }.lparams(matchParent, matchParent)
        }.lparams(matchParent, matchParent)
    }
}