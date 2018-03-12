package com.superfactory.library.Graphics.CircularReveal.animation
import android.view.ViewGroup
/**
 * Created by vicky on 2018.03.08.
 *
 * @Author vicky
 * @Date 2018年03月08日  14:49:13
 * @ClassName 这里输入你的类名(或用途)
 */

/**
 * Indicator for internal API that [ViewGroup] support
 * Circular Reveal animation
 */
interface RevealViewGroup {

    /**
     * @return Bridge between view and circular reveal animation
     */
    /**
     *
     * @param manager
     */
    var viewRevealManager: ViewRevealManager?
}