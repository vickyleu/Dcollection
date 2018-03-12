package com.superfactory.library.Graphics.LikeButton

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.util.Property
import android.view.View

/**
 * Created by vicky on 2018.03.07.
 *
 * @Author vicky
 * @Date 2018年03月07日  18:15:05
 * @ClassName 这里输入你的类名(或用途)
 */
class DotsView : View {
    private val DOTS_COUNT = 7;
    private val OUTER_DOTS_POSITION_ANGLE = 51;

    private var COLOR_1 = 0xFFFFC107.toInt()
    private var COLOR_2 = 0xFFFF9800.toInt()
    private var COLOR_3 = 0xFFFF5722.toInt()
    private var COLOR_4 = 0xFFF44336.toInt()

    internal var width = 0;
    internal var height = 0;

    private val circlePaints = arrayOfNulls<Paint>(4)

    private var centerX: Int = 0
    private var centerY: Int = 0

    private var maxOuterDotsRadius: Float = 0f
    private var maxInnerDotsRadius: Float = 0f
    private var maxDotSize: Float = 0f

    var currentProgress: Float = 0f
        set(value) {
        field=value
        updateInnerDotsPosition()
        updateOuterDotsPosition()
        updateDotsPaints()
        updateDotsAlpha()
        postInvalidate()
    }


    private var currentRadius1: Float = 0f
    private var currentDotSize1: Float = 0f
    private var currentDotSize2: Float = 0f
    private var currentRadius2: Float = 0f
    private var argbEvaluator = ArgbEvaluator()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


    private fun init() {
        for (i in 0 until circlePaints.size) {
            circlePaints[i] = Paint()
            circlePaints[i]?.style = Paint.Style.FILL;
            circlePaints[i]?.isAntiAlias = true;
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2
        centerY = h / 2
        maxDotSize = 5f
        maxOuterDotsRadius = w / 2 - maxDotSize * 2
        maxInnerDotsRadius = 0.8f * maxOuterDotsRadius
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawOuterDotsFrame(canvas)
        drawInnerDotsFrame(canvas)
    }

    private fun drawOuterDotsFrame(canvas: Canvas?) {
        for (i in 0 until DOTS_COUNT) {
            val cX = (centerX + currentRadius1 * Math.cos(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180)).toFloat()
            val cY = (centerY + currentRadius1 * Math.sin(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180)).toFloat()
            canvas?.drawCircle(cX, cY, currentDotSize1, circlePaints[i % circlePaints.size])
        }
    }

    private fun drawInnerDotsFrame(canvas: Canvas?) {
        for (i in 0 until DOTS_COUNT) {
            val cX = (centerX + currentRadius2 * Math.cos((i * OUTER_DOTS_POSITION_ANGLE - 10) * Math.PI / 180)).toFloat()
            val cY = (centerY + currentRadius2 * Math.sin((i * OUTER_DOTS_POSITION_ANGLE - 10) * Math.PI / 180)).toFloat()
            canvas?.drawCircle(cX, cY, currentDotSize2, circlePaints[(i + 1) % circlePaints.size])
        }
    }



    private fun updateInnerDotsPosition() {
        if (currentProgress < 0.3f) {
            this.currentRadius2 = Utils.mapValueFromRangeToRange(currentProgress.toDouble(), 0.toDouble(), 0.3f.toDouble(),
                    0.toDouble(), maxInnerDotsRadius.toDouble()).toFloat()
        } else {
            this.currentRadius2 = maxInnerDotsRadius;
        }
        if (currentProgress.equals(0)) {
            this.currentDotSize2 = 0f
        } else if (currentProgress < 0.2) {
            this.currentDotSize2 = maxDotSize;
        } else if (currentProgress < 0.5) {
            this.currentDotSize2 = Utils.mapValueFromRangeToRange(currentProgress.toDouble(), 0.2f.toDouble(), 0.5f.toDouble(),
                    maxDotSize.toDouble(), 0.3 * maxDotSize).toFloat()
        } else {
            this.currentDotSize2 = Utils.mapValueFromRangeToRange(currentProgress.toDouble(), 0.5f.toDouble(),
                    1f.toDouble(), maxDotSize * 0.3f.toDouble(), 0.toDouble()).toFloat()
        }

    }

    private fun updateOuterDotsPosition() {
        if (currentProgress < 0.3f) {
            this.currentRadius1 = Utils.mapValueFromRangeToRange(currentProgress.toDouble(), 0.0f.toDouble(), 0.3f.toDouble(), 0.toDouble(), maxOuterDotsRadius * 0.8f.toDouble()).toFloat()
        } else {
            this.currentRadius1 = Utils.mapValueFromRangeToRange(currentProgress.toDouble(), 0.3f.toDouble(), 1f.toDouble(), (0.8f * maxOuterDotsRadius).toDouble(), maxOuterDotsRadius.toDouble()).toFloat()
        }
        if (currentProgress.equals(0)) {
            this.currentDotSize1 = 0f
        } else if (currentProgress < 0.7) {
            this.currentDotSize1 = maxDotSize
        } else {
            this.currentDotSize1 = Utils.mapValueFromRangeToRange(currentProgress.toDouble(), 0.7f.toDouble(), 1f.toDouble(),
                    maxDotSize.toDouble(), 0.toDouble()).toFloat()
        }
    }

    private fun updateDotsPaints() {
        if (currentProgress < 0.5f) {
            val progress = Utils.mapValueFromRangeToRange(currentProgress.toDouble(), 0f.toDouble(), 0.5f.toDouble(), 0.toDouble(), 1f.toDouble()).toFloat()
            circlePaints[0]?.color = argbEvaluator.evaluate(progress, COLOR_1, COLOR_2).toString().toInt()
            circlePaints[1]?.color = argbEvaluator.evaluate(progress, COLOR_2, COLOR_3).toString().toInt()
            circlePaints[2]?.color = argbEvaluator.evaluate(progress, COLOR_3, COLOR_4).toString().toInt()
            circlePaints[3]?.color = argbEvaluator.evaluate(progress, COLOR_4, COLOR_1).toString().toInt()
        } else {
            val progress = Utils.mapValueFromRangeToRange(currentProgress.toDouble(), 0.5f.toDouble(), 1f.toDouble(), 0.toDouble(), 1f.toDouble()).toFloat()
            circlePaints[0]?.color = argbEvaluator.evaluate(progress, COLOR_2, COLOR_3).toString().toInt()
            circlePaints[1]?.color = argbEvaluator.evaluate(progress, COLOR_3, COLOR_4).toString().toInt()
            circlePaints[2]?.color = argbEvaluator.evaluate(progress, COLOR_4, COLOR_1).toString().toInt()
            circlePaints[3]?.color = argbEvaluator.evaluate(progress, COLOR_1, COLOR_2).toString().toInt()
        }
    }

    fun setColors(@ColorInt primaryColor: Int, @ColorInt secondaryColor: Int) {
        COLOR_1 = primaryColor;
        COLOR_2 = secondaryColor;
        COLOR_3 = primaryColor;
        COLOR_4 = secondaryColor;
        invalidate();
    }

    private fun updateDotsAlpha() {
        val progress = Utils.clamp(currentProgress.toDouble(), 0.6f.toDouble(), 1f.toDouble()).toFloat()
        val alpha = Utils.mapValueFromRangeToRange(progress.toDouble(), 0.6f.toDouble(), 1f.toDouble(), 255.toDouble(), 0.toDouble()).toInt()
        circlePaints[0]?.alpha = alpha
        circlePaints[1]?.alpha = alpha
        circlePaints[2]?.alpha = alpha
        circlePaints[3]?.alpha = alpha
    }

    fun setSize(width: Int, height: Int) {
        this.width = width
        this.height = height
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (width != 0 && height != 0)
            setMeasuredDimension(width, height)
    }

    companion object {
        val DOTS_PROGRESS = object : Property<DotsView, Float>(Float::class.java, "dotsProgress") {
            override fun get(`object`: DotsView?): Float {
                return `object`?.getCurrentProgress() ?: 0f
            }

            override fun set(`object`: DotsView?, value: Float?) {
                super.set(`object`, value)
                `object`?.setCurrentProgress(value ?: 0f)
            }
        }

    }

}