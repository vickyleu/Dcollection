package com.superfactory.dcollection.Utils

/**
 * Created by vicky on 2018.03.02.
 *
 * @Author vicky
 * @Date 2018年03月02日  18:39:44
 * @ClassName 这里输入你的类名(或用途)
 */
import android.os.Build
import android.view.View
import com.google.gson.GsonBuilder
import com.superfactory.dcollection.IConstants
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by benny on 12/17/15.
 */
fun toJson(`object`: Any): String {
    return GsonBuilder().setDateFormat(IConstants.DATE_FORMAT).create().toJson(`object`)
}

fun <T> fromJson(`object`: String, clazz: Class<T>): T? {
    return if (`object`.isNullOrEmpty()) null else GsonBuilder().setDateFormat(IConstants.DATE_FORMAT).create().fromJson(`object`, clazz)
}

val sNextGeneratedId: AtomicInteger = AtomicInteger(1);
fun generateViewId(): Int {
    if (Build.VERSION.SDK_INT < 17) {
        while (true) {
            val result: Int = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            var newValue: Int = result + 1;
            if (newValue > 0x00FFFFFF)
                newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    } else {
        return View.generateViewId();
    }
}