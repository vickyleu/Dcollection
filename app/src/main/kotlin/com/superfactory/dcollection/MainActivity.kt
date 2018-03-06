package com.superfactory.dcollection

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.benny.library.kbinding.view.ViewBinderComponent
import com.benny.library.kbinding.view.setContentView
import com.superfactory.dcollection.Contexts.BaseActivity
import com.superfactory.dcollection.Utils.generateViewId
import com.superfactory.dcollection.extension.viewPagerIndicator
import com.superfactory.dcollection.widget.ViewPagerIndicator
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.viewPager

class MainActivity : BaseActivity() {
    lateinit var toolBar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        MainActivityUI().setContentView(this).bindTo(this)

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar?.title = "";
    }

    inner class MainActivityUI : ViewBinderComponent<MainActivity> {
        override fun builder(): AnkoContext<out MainActivity>.() -> Unit = {
            verticalLayout {

                val pager = viewPager {
                    id = generateViewId()
                    clipToPadding = false
                    setPadding(40, 0, 40, 0)
                    pageMargin = 20

                }.lparams(matchParent, 0, 1f)

                viewPagerIndicator {
                    backgroundColor = Color.parseColor("#393a4c")
                    indicatorLinePosition = ViewPagerIndicator.TOP
                    indicatorLineMargin = dip(5)
                    indicatorLineHeight = dip(4)
                    viewPager = pager
                }.lparams(matchParent, wrapContent)
            }
        }
    }
}
