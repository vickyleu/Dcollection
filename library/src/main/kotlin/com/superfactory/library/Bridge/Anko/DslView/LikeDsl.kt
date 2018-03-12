package com.superfactory.library.Bridge.Anko.DslView

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import com.superfactory.library.Graphics.LikeButton.CircleView
import com.superfactory.library.Graphics.LikeButton.DotsView
import com.superfactory.library.Graphics.LikeButton.LikeButton
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.wrapContent

/**
 * Created by vicky on 2018.03.07.
 *
 * @Author vicky
 * @Date 2018年03月07日  18:54:31
 * @ClassName 这里输入你的类名(或用途)
 */
inline fun ViewManager.dotsView(): DotsView = dotsView() {}

inline fun ViewManager.dotsView(init: (@AnkoViewDslMarker DotsView).() -> Unit): DotsView {
    return ankoView(::DotsView, theme = 0) { init() }
}

inline fun ViewManager.themedDotsView(theme: Int = 0): DotsView = themedDotsView(theme) {}
inline fun ViewManager.themedDotsView(theme: Int = 0, init: (@AnkoViewDslMarker DotsView).() -> Unit): DotsView {
    return ankoView(::DotsView, theme) { init() }
}

inline fun ViewManager.circleView(): CircleView = circleView() {}

inline fun ViewManager.circleView(init: (@AnkoViewDslMarker CircleView).() -> Unit): CircleView {
    return ankoView(::CircleView, theme = 0) { init() }
}

inline fun ViewManager.themedCircleView(theme: Int = 0): CircleView = themedCircleView(theme) {}
inline fun ViewManager.themedCircleView(theme: Int = 0, init: (@AnkoViewDslMarker CircleView).() -> Unit): CircleView {
    return ankoView(::CircleView, theme) { init() }
}


open class _LikeButton(ctx: Context) : LikeButton(ctx) {

    inline fun <T : View> T.lparams(
            c: Context?,
            attrs: AttributeSet?,
            init: LayoutParams.() -> Unit
    ): T {
        val layoutParams = LayoutParams(c!!, attrs!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            c: Context?,
            attrs: AttributeSet?
    ): T {
        val layoutParams = LayoutParams(c!!, attrs!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            width: Int = wrapContent,
            height: Int = wrapContent,
            init: LayoutParams.() -> Unit
    ): T {
        val layoutParams = LayoutParams(width, height)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            width: Int = wrapContent,
            height: Int = wrapContent
    ): T {
        val layoutParams = LayoutParams(width, height)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            width: Int = wrapContent,
            height: Int = wrapContent,
            gravity: Int,
            init: LayoutParams.() -> Unit
    ): T {
        val layoutParams = LayoutParams(width, height, gravity)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            width: Int = wrapContent,
            height: Int = wrapContent,
            gravity: Int
    ): T {
        val layoutParams = LayoutParams(width, height, gravity)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            source: ViewGroup.LayoutParams?,
            init: LayoutParams.() -> Unit
    ): T {
        val layoutParams = LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            source: ViewGroup.LayoutParams?
    ): T {
        val layoutParams = LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            source: ViewGroup.MarginLayoutParams?,
            init: LayoutParams.() -> Unit
    ): T {
        val layoutParams = LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
            source: ViewGroup.MarginLayoutParams?
    ): T {
        val layoutParams = LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    inline fun <T : View> T.lparams(
            source: LayoutParams?,
            init: LayoutParams.() -> Unit
    ): T {
        val layoutParams = LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    inline fun <T : View> T.lparams(
            source: LayoutParams?
    ): T {
        val layoutParams = LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

}