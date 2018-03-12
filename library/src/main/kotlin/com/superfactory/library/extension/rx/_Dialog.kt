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

package com.superfactory.library.extension.rx

import android.content.Context
import com.superfactory.library.extension.KAlertDialogBuilder
import com.superfactory.library.extension.alert
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

fun <T> Context.alertObservable(
        message: String,
        title: String? = null,
        init: (KAlertDialogBuilder.(ObservableEmitter<in T>) -> Unit)? = null) =
        Observable.create<T> {
            alert(message, title) {
                init?.invoke(this, it)
            }
        }
//observable<T> { alert(message, title) { init?.invoke(this, it) } }