package com.superfactory.dcollection.ui.controller

import android.os.Bundle
import com.benny.library.kbinding.view.setContentView
import com.superfactory.dcollection.ui.base.BaseActivity
import com.superfactory.dcollection.R
import com.superfactory.dcollection.ui.layout.SampleLayoutUI

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        SampleLayoutUI().setContentView(this).bindTo(this)

    }

}
