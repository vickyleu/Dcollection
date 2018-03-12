package com.superfactory.library.Graphics.CircularReveal.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.superfactory.library.Graphics.CircularReveal.animation.RevealViewGroup
import com.superfactory.library.Graphics.CircularReveal.animation.ViewRevealManager

/**
 * Created by vicky on 2018.03.08.
 *
 * @Author vicky
 * @Date 2018年03月08日  15:23:09
 * @ClassName 这里输入你的类名(或用途)
 */
class RevealLinearLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : LinearLayout(context, attrs), RevealViewGroup {
    private var manager: ViewRevealManager? = null

    override var viewRevealManager: ViewRevealManager?
        get() = manager
        set(manager) {
            if (manager == null) {
                throw NullPointerException("ViewRevealManager is null")
            }

            this.manager = manager
        }

    init {
        manager = ViewRevealManager()
    }

    override fun drawChild(canvas: Canvas, child: View, drawingTime: Long): Boolean {
        try {
            canvas.save()

            manager!!.transform(canvas, child)
            return super.drawChild(canvas, child, drawingTime)
        } finally {
            canvas.restore()
        }
    }
}
