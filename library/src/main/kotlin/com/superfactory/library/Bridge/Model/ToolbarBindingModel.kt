package com.superfactory.library.Bridge.Model

import android.view.View
import com.superfactory.library.Bridge.Anko.BaseObservable
import com.superfactory.library.Bridge.Anko.ObservableFieldImpl
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Anko.observableNullable
import com.superfactory.library.Context.BaseApp
import org.jetbrains.anko.doAsync
import kotlin.reflect.full.memberProperties

/**
 * Created by vicky on 2018/1/19.
 */
abstract class ToolbarBindingModel : BaseObservable() {
    val displayNavigator = observable(false)
    val title = observable("")
    val leftIcon = observableNullable<Any?>(null)
    val backgroundColor = observable(0)
    val titleColor = observable(0)
    val titleSize = observable(0)
    val leftText = observable("")
    val leftTextSize = observable(0)
    val leftTextColor = observable(0)
    val leftPadding = observable(16)
    val rightPadding = observable(16)
    val rightIcon = observableNullable<Any?>(null)
    val rightText = observable("")
    val eraseRight = observable(false)
    val rightClickable: ObservableFieldImpl<((View?) -> Unit)?> = observableNullable<(View?) -> Unit>(null)
    val leftClickable: ObservableFieldImpl<((View?) -> Unit)?> = observableNullable<(View?) -> Unit>(null)
    val rightTextSize = observable(0)
    val rightTextColor = observable(0)
    val rightView = observableNullable<View?>(null)
    val leftView = observableNullable<View?>(null)

    init {
        doAsync {
            setToolbar(this@ToolbarBindingModel)
        }
    }

    abstract fun setToolbar(toolbarBindingModel: ToolbarBindingModel)

    companion object {
        fun <Value, Input> toModel(any: Value, input: Input, model: ToolbarBindingModel): ObservableFieldImpl<Value>? {
            var field: ObservableFieldImpl<Value>? = null
            val memberProperties = ToolbarBindingModel::class.memberProperties
            memberProperties.forEach {
                if (it.equals(input)) {
                    field = it.get(model) as ObservableFieldImpl<Value>
                    return field
                }
            }
            return field
        }

        fun getStaticsContextRef(): BaseApp {
            return BaseApp.appDelegate!!
        }
    }

}