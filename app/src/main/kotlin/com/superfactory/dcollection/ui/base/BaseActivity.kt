package com.superfactory.dcollection.ui.base

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.benny.library.kbinding.bind.BindingDelegate
import com.benny.library.kbinding.bind.BindingDisposer
import com.benny.library.kbinding.view.BindingDisposerGenerator
import com.benny.library.kbinding.view.ViewBinderComponent
import com.benny.library.kbinding.view.setContentView
import com.benny.library.kbinding.viewmodel.ViewModel
import com.superfactory.dcollection.R
import com.superfactory.library.theme.colorPrimaryDark
import org.jetbrains.anko.configuration

/**
 * Created by benny on 12/23/15.
 */


abstract class BaseActivity<
        T2 : BaseLayout<*, BaseActivity<T2,T3>>,
        T3> :
        BaseActivityDelegate<*,T2, T3>()

abstract class BaseLayout<
        T1,
        in T2 :
        <in T1, BaseLayout<T1, T2>>
        > :
        BaseLayoutDelegate<BaseLayout<T1, T2>,T2, T1>()


abstract class BaseLayoutDelegate<
         Self : BaseLayoutDelegate<Self, T, D>,
        T : BaseActivityDelegate<T,Self, D>, D
        > :
        ViewBinderComponent<T>


abstract class BaseActivityDelegate<
       Self : BaseActivityDelegate<Self, T, D>,
        T : BaseLayoutDelegate<T, in Self, D>,
        D
        > :
        AppCompatActivity(), BindingDisposerGenerator, BindingDelegate {
    override val bindingDisposer = BindingDisposer()
    override val viewModel = ViewModel()

    open val bindingStatus = false

    private val colorPrimaryDarkCallback = { color: Int ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = color
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        layoutUi().setContentView(this as Activity).bindTo(this)
        layoutUi().setContentView(this).bindTo(this)
    }

    abstract fun layoutUi(): T

    override fun onDestroy() {
        super.onDestroy()
        bindingDisposer.unbind()
    }

    override fun setContentView(layoutResID: Int) {
        delegate.setContentView(layoutResID)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> false
    }

    override fun onResume() {
        super.onResume()
        configuration(fromSdk = Build.VERSION_CODES.LOLLIPOP) {
            if (bindingStatus) {
                colorPrimaryDarkCallback(colorPrimaryDark.value)
                colorPrimaryDark.listeners.add(colorPrimaryDarkCallback)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        configuration(fromSdk = Build.VERSION_CODES.LOLLIPOP) {
            if (bindingStatus)
                colorPrimaryDark.listeners.remove(colorPrimaryDarkCallback)
        }
    }

}