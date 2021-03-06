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

package com.superfactory.library.extension.kbinding

import android.widget.TextView
import com.benny.library.kbinding.bind.BindingMode
import com.benny.library.kbinding.bind.OneWay
import com.benny.library.kbinding.bind.oneWayPropertyBinding
import com.benny.library.kbinding.converter.EmptyOneWayConverter1
import com.benny.library.kbinding.converter.OneWayConverter
import com.rayfantasy.icode.syntaxhighlighter.SyntaxHighlighter
import java.util.function.Consumer

fun TextView.codeBlock(vararg paths: String, mode: OneWay = BindingMode.OneWay,
                       converter: OneWayConverter<*, CodeGood.Block> = EmptyOneWayConverter1()) = oneWayPropertyBinding(paths, codeBlock(this), false, converter)

private fun codeBlock(tv: TextView) = Consumer<CodeGood.Block> {
    tv.text = it.content
    val syntaxGroup = SyntaxHighlighter.Companion.LANGS[it.extra]
    syntaxGroup?.highlight(tv)
}

fun TextView.time(vararg paths: String, mode: OneWay = BindingMode.OneWay,
                  converter: OneWayConverter<*, Long> = EmptyOneWayConverter1()) = oneWayPropertyBinding(paths, time(this), false, converter)

private fun time(tv: TextView) = Consumer<Long> {
    tv.text = ms2RelativeDate(tv.context, it)
}