package com.superfactory.dcollection

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import com.benny.library.kbinding.view.ViewBinderComponent
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.themedToolbar

/**
 * Created by benny on 1/15/16.
 */

class TitleToolBarView(val title: String) : ViewBinderComponent<Activity> {
    override fun builder(): AnkoContext<*>.() -> Unit = {
        themedToolbar(R.style.ToolbarTheme) {
            backgroundColor = Color.parseColor("#393a4c")
            textView {
                text = this@TitleToolBarView.title
                textSize = 16f
                textColor = Color.WHITE
            }.lparams(wrapContent, wrapContent) { gravity = Gravity.CENTER }
        }
    }
}