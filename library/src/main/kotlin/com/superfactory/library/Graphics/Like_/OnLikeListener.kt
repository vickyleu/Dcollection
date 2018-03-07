package com.superfactory.library.Graphics.Like_

/**
 * Created by vicky on 2018.03.07.
 *
 * @Author vicky
 * @Date 2018年03月07日  18:44:23
 * @ClassName 这里输入你的类名(或用途)
 */
interface OnLikeListener {
    fun liked(likeButton: LikeButton)
    fun unLiked(likeButton: LikeButton)
}