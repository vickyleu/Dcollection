package com.superfactory.library.Graphics.Like_.extentions

import android.view.ViewManager
import com.superfactory.library.Graphics.Like_.CircleView
import com.superfactory.library.Graphics.Like_.DotsView
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.custom.ankoView

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
