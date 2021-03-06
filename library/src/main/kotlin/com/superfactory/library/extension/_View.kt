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

import android.support.design.widget.Snackbar
import android.view.View
import android.view.ViewGroup

fun View.snackBar(resId: Int, duration: Int = Snackbar.LENGTH_SHORT) = Snackbar.make(this, resId, duration).show()
fun View.snackBar(text: CharSequence, duration: Int = Snackbar.LENGTH_SHORT) = Snackbar.make(this, text, duration).show()

fun View.getMarginBottom(): Int {
    var marginBottom = 0
    val layoutParams = layoutParams
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        marginBottom = layoutParams.bottomMargin;
    }
    return marginBottom
}