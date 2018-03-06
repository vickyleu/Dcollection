package com.superfactory.dcollection.Contexts

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.benny.library.kbinding.bind.BindingDelegate
import com.benny.library.kbinding.bind.BindingDisposer
import com.benny.library.kbinding.view.BindingDisposerGenerator
import com.benny.library.kbinding.viewmodel.ViewModel
import org.jetbrains.anko.configuration

/**
 * Created by benny on 12/23/15.
 */
abstract class BaseActivity : AppCompatActivity(), BindingDisposerGenerator, BindingDelegate {
    override val bindingDisposer = BindingDisposer()
    override val viewModel = ViewModel()

    open val bindingStatus = false

    private val colorPrimaryDarkCallback = { color: Int -> window.statusBarColor = color }

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