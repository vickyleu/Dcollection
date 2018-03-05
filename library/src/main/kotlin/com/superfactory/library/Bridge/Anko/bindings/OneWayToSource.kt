package com.superfactory.library.Bridge.Anko.bindings

import android.view.View
import android.widget.*
import com.superfactory.library.Bridge.Anko.BindingRegister
import com.superfactory.library.Bridge.Anko.ObservableField
import com.superfactory.library.Debuger
import org.jetbrains.anko.doAsync
import java.util.*

/**
 * Created by vicky on 2018.01.17.
 *
 * @Author vicky
 * @Date 2018年01月17日  16:50:04
 * @ClassName 一步到胃
 */


/**
 * 函数映射 bindSelf(this:View).toObservable { onClickListener }
 * 将内部点击事件映射到传入的点击属性中,在传入的点击属性中判断触发view的id或类型做内部存取值,将view和model层解耦
 */

fun <Data> BindingRegister<Data>.bind(v: View, any: View.OnClickListener?) = bind(v, OnViewClickListener(any))

fun <Data> BindingRegister<Data>.bind(v: TextView) = bind(v, OnTextChangedRegister())
fun <Data> BindingRegister<Data>.bind(v: CompoundButton) = bind(v, OnCheckedChangeRegister())
fun <Data> BindingRegister<Data>.bind(v: DatePicker, initialValue: Calendar = Calendar.getInstance()) = bind(v, OnDateChangedRegister(initialValue))
fun <Data> BindingRegister<Data>.bind(v: TimePicker) = bind(v, OnTimeChangedRegister())
fun <Data> BindingRegister<Data>.bind(v: RatingBar) = bind(v, OnRatingBarChangedRegister())
fun <Data> BindingRegister<Data>.bind(v: SeekBar) = bind(v, OnSeekBarChangedRegister())

class ViewBinder<Data, V : View, Output>(val view: V,
                                         val viewRegister: ViewRegister<V, Output>,
                                         val component: BindingRegister<Data>)

fun <Data, V : View, Output, Input> ViewBinder<Data, V, Output>.onNullable(bindingExpression: BindingExpression<Output?, Input?>) = OneWayToSourceExpression(this, bindingExpression)

fun <Data, V : View, Output> ViewBinder<Data, V, Output>.onSelf() = onNullable { it }

class OneWayToSourceExpression<Data, Input, Output, V : View>
internal constructor(val viewBinder: ViewBinder<Data, V, Output>,
                     val bindingExpression: BindingExpression<Output?, Input?>) {

    fun to(propertySetter: (Data?, Input?, V) -> Unit) = OneWayToSource(this, propertySetter)
}

inline fun <Data, Input, Output, V : View>
        OneWayToSourceExpression<Data, Input, Output, V>.toObservable(
        crossinline function: (Data) -> ObservableField<Input>) =
        to { viewModel, input, _ ->
            if (viewModel != null) {
                val field = function(viewModel)
                field.value = input ?: field.defaultValue
            }
        }

inline fun <Data, Input : View.OnClickListener?, Output : View.OnClickListener?, V : View>
        OneWayToSourceExpression<Data, Input, Output, V>.notifyOnClickObservable(
        crossinline function: (Data) -> ObservableField<Input>) =
        to { viewModel, input, output ->
            if (viewModel != null) {
//                Debuger.printMsg(this, "注册监听器成功")
            }
        }


class OneWayToSource<Data, Input, Output, V : View>
internal constructor(
        val expression: OneWayToSourceExpression<Data, Input, Output, V>,
        val propertySetter: (Data?, Input?, V) -> Unit,
        val bindingExpression: BindingExpression<Output?, Input?> = expression.bindingExpression,
        val view: V = expression.viewBinder.view,
        val viewRegister: ViewRegister<V, Output> = expression.viewBinder.viewRegister) : Binding {

    private val component
        get() = expression.viewBinder.component

    init {
        component.registerBinding(this)
    }

    override fun bind() {
        viewRegister.register(view, { output ->
            doAsync { propertySetter(component.viewModel, bindingExpression(output), view) }
        })
        notifyValueChange()
    }

    override fun unbind() {
        unbindInternal()
        component.unregisterBinding(this)
    }

    internal fun unbindInternal() {
        viewRegister.deregister(view)
    }

    override fun notifyValueChange() {
        propertySetter(component.viewModel, bindingExpression(viewRegister.getValue(view)), view)
    }
}

