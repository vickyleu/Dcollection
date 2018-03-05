package com.superfactory.library.Bridge.Anko.bindings

import com.superfactory.library.Bridge.Anko.BindingRegister
import com.superfactory.library.Bridge.Anko.ObservableField
import com.superfactory.library.Bridge.Anko.PropertyChangedCallback
import kotlin.reflect.KProperty


interface BindingConverter<Data, out Input> {

    val component: BindingRegister<Data>

    fun convertValue(data: Data?): Input?

    fun bind(binding: Binding) {}
    fun unbind(binding: Binding) {}

}

class ObservableBindingConverter<Data, Input>(val function: (Data) -> ObservableField<Input>,
                                              override val component: BindingRegister<Data>)
    : BindingConverter<Data, Input> {

    private var oneWayBinding: Binding? = null

    val observableField: ObservableField<Input>?
        get() {
            val viewModel = component.viewModel
            return if (viewModel != null) function(viewModel) else null
        }

    override fun convertValue(data: Data?) = observableField?.value

    override fun bind(binding: Binding) {
        this.oneWayBinding = binding
        observableField?.addOnPropertyChangedCallback(onPropertyChanged)
    }

    override fun unbind(binding: Binding) {
        observableField?.let { observableField ->
            observableField.removeOnPropertyChangedCallback(onPropertyChanged)
            observableField.unregisterFromBinding()
        }
        this.oneWayBinding = null
    }

    private val onPropertyChanged: PropertyChangedCallback = { _, _ -> oneWayBinding?.notifyValueChange() }
}

class InputExpressionBindingConverter<Data, out Input>(
        val expression: (Data) -> Input,
        override val property: KProperty<*>? = null,
        override val component: BindingRegister<Data>)
    : BindingConverter<Data, Input>, PropertyExpressionBindingConverter {
    override fun convertValue(data: Data?): Input? = if (data != null) expression(data) else null
}

class NullableInputExpressionBindingConverter<Data, out Input>(
        val expression: (Data?) -> Input,
        override val property: KProperty<*>? = null,
        override val component: BindingRegister<Data>)
    : BindingConverter<Data, Input>, PropertyExpressionBindingConverter {
    override fun convertValue(data: Data?) = expression(data)
}

internal interface PropertyExpressionBindingConverter {
    val property: KProperty<*>?
}
