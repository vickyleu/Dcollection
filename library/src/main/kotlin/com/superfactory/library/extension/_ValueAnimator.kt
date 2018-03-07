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

package com.rayfantasy.icode.extension

import com.nineoldandroids.animation.ArgbEvaluator
import com.nineoldandroids.animation.ValueAnimator
import com.superfactory.library.extension._AnimatorListenerNineOldAndroids

fun colorAnim(colorFrom: Int, colorTo: Int, duration: Long, callback: (Int) -> Unit,
              listener: (_AnimatorListenerNineOldAndroids.() -> Unit)? = null): ValueAnimator {
    val anim = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
    anim.duration = duration
    anim.addUpdateListener { callback(it.animatedValue as Int) }
    listener?.let { anim.addListener(listener) }
    anim.start()
    return anim
}