package com.superfactory.library.Graphics.LikeButton

import android.support.annotation.DrawableRes

/**
 * Created by vicky on 2018.03.07.
 *
 * @Author vicky
 * @Date 2018年03月07日  18:41:54
 * @ClassName 这里输入你的类名(或用途)
 */

data class Icon(@param:DrawableRes var onIconResourceId: Int, @param:DrawableRes var offIconResourceId: Int, var iconType: IconType?)
