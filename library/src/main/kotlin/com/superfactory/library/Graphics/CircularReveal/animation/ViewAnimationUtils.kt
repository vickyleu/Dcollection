package com.superfactory.library.Graphics.CircularReveal.animation

import android.animation.Animator
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.view.View
import com.superfactory.library.BuildConfig

/**
 * Created by vicky on 2018.03.08.
 *
 * @Author vicky
 * @Date 2018年03月08日  14:49:40
 * @ClassName 这里输入你的类名(或用途)
 */
object ViewAnimationUtils {
    private val DEBUG = BuildConfig.DEBUG

    private val LOLLIPOP_PLUS = SDK_INT >= LOLLIPOP

    /**
     * Returns an Animator which can animate a clipping circle.
     *
     *
     * Any shadow cast by the View will respect the circular clip from this animator.
     *
     *
     * Only a single non-rectangular clip can be applied on a View at any time.
     * Views clipped by a circular reveal animation take priority over
     * [View Outline clipping][View.setClipToOutline].
     *
     *
     * Note that the animation returned here is a one-shot animation. It cannot
     * be re-used, and once started it cannot be paused or resumed.
     *
     * @param view The View will be clipped to the clip circle.
     * @param centerX The x coordinate of the center of the clip circle.
     * @param centerY The y coordinate of the center of the clip circle.
     * @param startRadius The starting radius of the clip circle.
     * @param endRadius The ending radius of the clip circle.
     * @param layerType View layer type [View.LAYER_TYPE_HARDWARE] or [ ][View.LAYER_TYPE_SOFTWARE]
     */
    @JvmOverloads
    fun createCircularReveal(view: View, centerX: Int, centerY: Int,
                             startRadius: Float, endRadius: Float, layerType: Int = View.LAYER_TYPE_SOFTWARE): Animator {

        if (view.parent !is RevealViewGroup) {
            throw IllegalArgumentException("Parent must be instance of RevealViewGroup")
        }

        val viewGroup = view.parent as RevealViewGroup
        val rm = viewGroup.viewRevealManager

        if (!rm.overrideNativeAnimator() && LOLLIPOP_PLUS) {
            return android.view.ViewAnimationUtils.createCircularReveal(view, centerX, centerY,
                    startRadius, endRadius)
        }

        val viewData = ViewRevealManager.Companion.RevealValues(view, centerX, centerY, startRadius, endRadius)
        val animator = rm.dispatchCreateAnimator(viewData)

        if (layerType != view.layerType) {
            animator.addListener(ViewRevealManager.Companion.ChangeViewLayerTypeAdapter(viewData, layerType))
        }

        return animator
    }
}
/**
 * Returns an Animator which can animate a clipping circle.
 *
 *
 * Any shadow cast by the View will respect the circular clip from this animator.
 *
 *
 * Only a single non-rectangular clip can be applied on a View at any time.
 * Views clipped by a circular reveal animation take priority over
 * [View Outline clipping][View.setClipToOutline].
 *
 *
 * Note that the animation returned here is a one-shot animation. It cannot
 * be re-used, and once started it cannot be paused or resumed.
 *
 * @param view The View will be clipped to the clip circle.
 * @param centerX The x coordinate of the center of the clip circle.
 * @param centerY The y coordinate of the center of the clip circle.
 * @param startRadius The starting radius of the clip circle.
 * @param endRadius The ending radius of the clip circle.
 */
