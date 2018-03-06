package com.superfactory.dcollection.extension

import android.content.Context
import android.view.View
import android.view.ViewManager
import com.superfactory.dcollection.widget.ViewPagerIndicator
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.custom.ankoView

var View.backgroundColorResource: Int
    get() = throw UnsupportedOperationException()
    set(value) {
        this.backgroundColor = context.resources.getColor(value)
    }


fun ViewManager.viewPagerIndicator(theme: Int = 0, init: ViewPagerIndicator.() -> Unit): ViewPagerIndicator {
    return ankoView({ ctx: Context -> ViewPagerIndicator(ctx) }, theme) { init() }
}
