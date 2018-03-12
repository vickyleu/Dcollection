package com.superfactory.library.Graphics.LikeButton


import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.*
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.util.Property
import android.view.View

/**
 * Created by vicky on 2018.03.07.
 *
 * @Author vicky
 * @Date 2018年03月07日  11:21:54
 * @ClassName 这里输入你的类名(或用途)
 */
/**
 * Created by Miroslaw Stanek on 21.12.2015.
 * Modified by Joel Dean
 */

class CircleView : View {
    private var START_COLOR: Int = 0xFFFF5722.toInt()
    private var END_COLOR: Int = 0xFFFFC107.toInt()

    private val argbEvaluator = ArgbEvaluator()

    private val circlePaint = Paint()
    private val maskPaint = Paint()

    private var tempBitmap: Bitmap? = null
    private var tempCanvas: Canvas? = null

    var outerCircleRadiusProgress = 0.0f
        set(value) {
            field = value
            updateCircleColor()
            postInvalidate()
        }

    var innerCircleRadiusProgress = 0.0f
        set(value) {
            field = value
            postInvalidate()
        }

    internal var width = 0
    internal var height = 0

    private var maxCircleSize = 0

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
        circlePaint.setStyle(Paint.Style.FILL)
        circlePaint.isAntiAlias = true
        maskPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        maskPaint.isAntiAlias = true
    }

    fun setSize(width: Int, height: Int) {
        this.width = width
        this.height = height
        invalidate()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (width != 0 && height != 0)
            setMeasuredDimension(width, height);
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        maxCircleSize = w / 2;
        tempBitmap = Bitmap.createBitmap(getWidth(), getWidth(), Bitmap.Config.ARGB_8888);
        tempCanvas = Canvas(tempBitmap);
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        tempCanvas?.drawColor(0xffffff, PorterDuff.Mode.CLEAR);
        tempCanvas?.drawCircle((getWidth() / 2).toFloat(), (getHeight() / 2).toFloat(), outerCircleRadiusProgress * maxCircleSize, circlePaint);
        tempCanvas?.drawCircle((getWidth() / 2).toFloat(), (getHeight() / 2).toFloat(), innerCircleRadiusProgress * maxCircleSize + 1, maskPaint);
        if (tempBitmap != null) {
            canvas?.drawBitmap(tempBitmap, 0f, 0f, null)
        }
    }


    private fun updateCircleColor() {
        var colorProgress = Utils.clamp(outerCircleRadiusProgress.toDouble(), 0.5, 1f.toDouble()).toFloat()
        colorProgress = Utils.mapValueFromRangeToRange(colorProgress.toDouble(), 0.5, 1f.toDouble(), 0f.toDouble(),
                1f.toDouble()).toFloat()
        this.circlePaint.color = argbEvaluator.evaluate(colorProgress, START_COLOR, END_COLOR).toString().toInt()
    }


    companion object {
        val INNER_CIRCLE_RADIUS_PROGRESS = object : Property<CircleView, Float>(Float::class.java, "innerCircleRadiusProgress") {
            override fun get(`object`: CircleView?): Float {
                return `object`?.innerCircleRadiusProgress ?: 0f
            }

            override fun set(`object`: CircleView?, value: Float?) {
                super.set(`object`, value)
                `object`?.innerCircleRadiusProgress = (value ?: 0f)
            }
        }
        val OUTER_CIRCLE_RADIUS_PROGRESS = object : Property<CircleView, Float>(Float::class.java, "outerCircleRadiusProgress") {
            override fun get(`object`: CircleView?): Float {
                return `object`?.outerCircleRadiusProgress ?: 0f
            }

            override fun set(`object`: CircleView?, value: Float?) {
                super.set(`object`, value)
                `object`?.outerCircleRadiusProgress = (value ?: 0f)
            }
        }

    }

    fun setStartColor(@ColorInt color: Int) {
        START_COLOR = color
        invalidate();
    }

    fun setEndColor(@ColorInt color: Int) {
        END_COLOR = color
        invalidate();
    }
}