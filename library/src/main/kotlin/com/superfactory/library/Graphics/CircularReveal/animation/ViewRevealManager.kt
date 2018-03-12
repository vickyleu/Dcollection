package com.superfactory.library.Graphics.CircularReveal.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.*
import android.os.Build
import android.util.Property
import android.view.View

/**
 * Created by vicky on 2018.03.08.
 *
 * @Author vicky
 * @Date 2018年03月08日  14:50:06
 * @ClassName 这里输入你的类名(或用途)
 */
class ViewRevealManager {
    companion object {
        internal val REVEAL = ClipRadiusProperty()

        class RevealValues(target: View, var centerX: Int, var centerY: Int, var startRadius: Float, var endRadius: Float) {
            companion object {
                private val debugPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            }

            init {
                debugPaint.color = Color.GREEN
                debugPaint.style = Paint.Style.FILL
                debugPaint.strokeWidth = 2f
            }

            // Flag that indicates whether view is clipping now, mutable
            var clipping: Boolean = false
            // Revealed radius
            var radius: Float = 0f
            // Animation target
            var target: View? = target
        }

        /**
         * Property animator. For performance improvements better to use
         * directly variable member (but it's little enhancement that always
         * caught as dangerous, let's see)
         */
        internal class ClipRadiusProperty() : Property<RevealValues, Float>(Float::class.java, "supportCircularReveal") {
            /**
             * Returns the current value that this property represents on the given `object`.
             */
            override fun get(`object`: RevealValues?): Float {
                return `object`?.radius ?: 0f
            }

            override fun set(`object`: RevealValues?, value: Float?) {
                `object`?.radius = value ?: 0f
                `object`?.target?.invalidate();
            }
        }

        /**
         * As class name cue's it changes layer type of {@link View} on animation createAnimator
         * in order to improve animation smooth & performance and returns original value
         * on animation end
         */
        class ChangeViewLayerTypeAdapter(viewData: RevealValues, layerType: Int) : AnimatorListenerAdapter() {
            private var viewData: RevealValues? = viewData
            private var featuredLayerType: Int = layerType
            private var originalLayerType: Int = 0

            override fun onAnimationStart(animation: Animator?) {
                viewData?.target?.setLayerType(featuredLayerType, null)
            }

            override fun onAnimationCancel(animation: Animator?) {
                viewData?.target?.setLayerType(originalLayerType, null)
            }

            override fun onAnimationEnd(animation: Animator?) {
                viewData?.target?.setLayerType(originalLayerType, null)
            }

            init {
                this.originalLayerType = viewData.target?.layerType ?: 0
            }
        }
    }

    private var viewTransformation: ViewTransformation? = null
    private val targets = HashMap<View?, RevealValues>()
    private val animators = HashMap<Animator, RevealValues>()

    private val animatorCallback = object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator?) {
            val values: RevealValues? = getValues(animation)
            values?.clipping = true
        }

        override fun onAnimationCancel(animation: Animator?) {
            endAnimation(animation);
        }

        override fun onAnimationEnd(animation: Animator?) {
            endAnimation(animation);
        }

        private fun endAnimation(animation: Animator?) {
            val values: RevealValues? = getValues(animation)
            values?.clipping = false

            // Clean up after animation is done
            targets.remove(values?.target)
            animators.remove(animation)
        }
    }

    constructor() : this(PathTransformation()) {
    }

    internal constructor(transformation: ViewTransformation) {
        this.viewTransformation = transformation
    }

    fun dispatchCreateAnimator(data: RevealValues): Animator {
        val animator: Animator = createAnimator(data)
        // Before animation is started keep them
        targets.put(data.target, data)
        animators.put(animator, data)
        return animator
    }

    /**
     * Create custom animator of circular reveal
     *
     * @param data RevealValues contains information of starting & ending points, animation target and
     * current animation values
     * @return Animator to manage reveal animation
     */
    protected fun createAnimator(data: RevealValues): Animator {
        val animator: ObjectAnimator =
                ObjectAnimator.ofFloat(data, REVEAL, data.startRadius, data.endRadius);

        animator.addListener(getAnimatorCallback());
        return animator;
    }

    protected final fun getAnimatorCallback(): AnimatorListenerAdapter {
        return animatorCallback;
    }

    /**
     * @return Retruns Animator
     */
    protected final fun getValues(animator: Animator?): RevealValues? {
        return animators[animator]
    }

    /**
     * @return Map of started animators
     */
    protected final fun getValues(view: View?): RevealValues? {
        return targets[view]
    }

    /**
     * @return True if you don't want use Android native reveal animator in order to use your own
     * custom one
     */
    fun overrideNativeAnimator(): Boolean {
        return false
    }

    /**
     * @return True if animation was started and it is still running, otherwise returns False
     */
    public fun isClipped(child: View): Boolean {
        val data: RevealValues? = getValues(child)
        return data != null && data.clipping
    }

    /**
     * Applies path clipping on a canvas before drawing child,
     * you should save canvas state before viewTransformation and
     * restore it afterwards
     *
     * @param canvas Canvas to apply clipping before drawing
     * @param child Reveal animation target
     * @return True if viewTransformation was successfully applied on referenced child, otherwise
     * child be not the target and therefore animation was skipped
     */
    public final fun transform(canvas: Canvas, child: View): Boolean {
        val revealData: RevealValues? = targets.get(child);

        // Target doesn't has animation values
        if (revealData == null) {
            return false;
        }
        // Check whether target consistency
        else if (revealData.target != child) {
            throw  IllegalStateException("Inconsistency detected, contains incorrect target view")
        }
        // View doesn't wants to be clipped therefore transformation is useless
        else if (!revealData.clipping) {
            return false;
        }

        return viewTransformation?.transform(canvas, child, revealData) ?: false
    }

    /**
     * Custom View viewTransformation extension used for applying different reveal
     * techniques
     */
    internal interface ViewTransformation {

        /**
         * Apply view viewTransformation
         *
         * @param canvas Main canvas
         * @param child Target to be clipped & revealed
         * @return True if viewTransformation is applied, otherwise return fAlse
         */
        fun transform(canvas: Canvas, child: View, values: RevealValues): Boolean
    }

    class PathTransformation : ViewTransformation {

        // Android Canvas is tricky, we cannot clip circles directly with Canvas API
        // but it is allowed using Path, therefore we use it :|
        private val path = Path()

        private var op: Region.Op = Region.Op.REPLACE

        /** @see Canvas.clipPath
         */
        fun op(): Region.Op {
            return op
        }

        /** @see Canvas.clipPath
         */
        fun op(op: Region.Op) {
            this.op = op
        }

        override fun transform(canvas: Canvas, child: View, values: RevealValues): Boolean {
            path.reset()
            // trick to applyTransformation animation, when even x & y translations are running
            path.addCircle(child.x + values.centerX, child.y + values.centerY, values.radius,
                    Path.Direction.CW)

            canvas.clipPath(path, op)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                child.invalidateOutline()
            }
            return false
        }
    }


}