package com.superfactory.library.Graphics.Badge

import android.graphics.PointF

/**
 * Created by chqiu on 2017/3/20.
 */

object MathUtil {
    val CIRCLE_RADIAN = 2 * Math.PI

    fun getTanRadian(atan: Double, quadrant: Int): Double {
        var atan = atan
        if (atan < 0) {
            atan += CIRCLE_RADIAN / 4
        }
        atan += CIRCLE_RADIAN / 4 * (quadrant - 1)
        return atan
    }

    fun radianToAngle(radian: Double): Double {
        return 360 * (radian / CIRCLE_RADIAN)
    }

    fun getQuadrant(p: PointF, center: PointF): Int {
        if (p.x > center.x) {
            if (p.y > center.y) {
                return 4
            } else if (p.y < center.y) {
                return 1
            }
        } else if (p.x < center.x) {
            if (p.y > center.y) {
                return 3
            } else if (p.y < center.y) {
                return 2
            }
        }
        return -1
    }

    fun getPointDistance(p1: PointF, p2: PointF): Float {
        return Math.sqrt(Math.pow((p1.x - p2.x).toDouble(), 2.0) + Math.pow((p1.y - p2.y).toDouble(), 2.0)).toFloat()
    }

    /**
     * this formula is designed by mabeijianxi
     * website : http://blog.csdn.net/mabeijianxi/article/details/50560361
     *
     * @param circleCenter The circle center point.
     * @param radius       The circle radius.
     * @param slopeLine    The slope of line which cross the pMiddle.
     */
    fun getInnertangentPoints(circleCenter: PointF, radius: Float, slopeLine: Double?, points: MutableList<PointF>) {
        val radian: Float
        val xOffset: Float
        val yOffset: Float
        if (slopeLine != null) {
            radian = Math.atan(slopeLine).toFloat()
            xOffset = (Math.cos(radian.toDouble()) * radius).toFloat()
            yOffset = (Math.sin(radian.toDouble()) * radius).toFloat()
        } else {
            xOffset = radius
            yOffset = 0f
        }
        points.add(PointF(circleCenter.x + xOffset, circleCenter.y + yOffset))
        points.add(PointF(circleCenter.x - xOffset, circleCenter.y - yOffset))
    }
}
