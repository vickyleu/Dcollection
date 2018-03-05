package com.superfactory.dcollection

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.widget.Button
import com.benny.library.kbinding.bind.commandBinding
import com.benny.library.kbinding.common.bindings.defaultCommand
import com.benny.library.kbinding.common.bindings.postDefaultEvent
import com.benny.library.kbinding.dsl.bind
import com.benny.library.kbinding.dsl.inflate
import com.benny.library.kbinding.view.ViewBinderComponent
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    inner class AnimationActivityUI : ViewBinderComponent<AnimationActivity> {
        override fun builder(): AnkoContext<out AnimationActivity>.() -> Unit = {
            var box1: Button?
            var box2: Button? = null
            val box2Subject: PublishSubject<Unit> = PublishSubject.create()
            verticalLayout {
                appBarLayout {
                    toolBar = inflate(TitleToolBarView(ctx.resources.getString(R.string.animation)), this@appBarLayout) as Toolbar
                }
                frameLayout {
                    lparams(matchParent, matchParent) {
                        horizontalPadding = dip(16)
                        verticalPadding = dip(56)
                    }
                    box1 = button("box1") {
                        // Library will automatically create a and will only create a default subject for every view.
                        // this call will use the default subject.
                        // But this requires invoke SubjectCache#removeContext(activity) when the activity destroyed
                        setOnClickListener { owner.jump(box2!!) { postDefaultEvent() } }
                        bind { defaultCommand(k_box2Finish) }
                    }.lparams(gravity = Gravity.BOTTOM)
                    box2 = button("box2") {
                        //You can also create a subject, so that a more flexible
                        setOnClickListener { owner.jump(box1!!) { box2Subject.onNext(Unit) } }
//                        rx.Observable
                        Observable
                        bind { commandBinding(k_box1Finish,box2Subject) }
                    }.lparams(gravity = Gravity.END.xor(Gravity.BOTTOM))
                }
            }
        }

    }
}
