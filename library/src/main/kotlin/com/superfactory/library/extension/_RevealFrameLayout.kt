/*
 * Copyright 2015-2016 RayFantasy Studio
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.superfactory.library.extension

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewManager
import com.superfactory.library.Graphics.CircularReveal.widget.RevealFrameLayout
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.wrapContent

inline fun ViewManager.revealFrameLayout(init: _RevealFrameLayout.() -> Unit) = ankoView(::_RevealFrameLayout, theme = 0) { init() }

class _RevealFrameLayout(context: Context) : RevealFrameLayout(context) {
    fun <T : View> T.lparams(
            c: android.content.Context?,
            attrs: android.util.AttributeSet?,
            init: LayoutParams.() -> Unit = defaultInit
    ): T {
        val layoutParams = LayoutParams(c!!, attrs!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    fun <T : View> T.lparams(
            width: Int = wrapContent,
            height: Int = wrapContent,
            init: LayoutParams.() -> Unit = defaultInit
    ): T {
        val layoutParams = android.widget.FrameLayout.LayoutParams(width, height)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    fun <T : View> T.lparams(
            width: Int = wrapContent,
            height: Int = wrapContent,
            gravity: Int,
            init: LayoutParams.() -> Unit = defaultInit
    ): T {
        val layoutParams = LayoutParams(width, height, gravity)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun <T : View> T.lparams(
            source: LayoutParams?,
            init: LayoutParams.() -> Unit = defaultInit
    ): T {
        val layoutParams = LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    fun <T : View> T.lparams(
            source: android.view.ViewGroup.MarginLayoutParams?,
            init: LayoutParams.() -> Unit = defaultInit
    ): T {
        val layoutParams = LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }
}