package com.superfactory.dcollection.Contexts

import android.annotation.SuppressLint
import android.content.Context
import com.superfactory.dcollection.IConstants
import com.superfactory.dcollection.Models.User
import com.superfactory.dcollection.Utils.fromJson
import com.superfactory.dcollection.Utils.toJson
import org.jetbrains.anko.defaultSharedPreferences
import java.util.*
import kotlin.properties.Delegates.observable
import kotlin.reflect.KProperty

@SuppressLint("StaticFieldLeak")
/**
 * Created by vicky on 2018.03.02.
 *
 * @Author vicky
 * @Date 2018年03月02日  18:37:32
 * @ClassName 这里输入你的类名(或用途)
 */
object ApplicationContext {
    private lateinit var context: Context

    var user: User? by observable(null) { property: KProperty<*>, oldValue: User?, newValue: User? ->
        saveUser(newValue)
    }

    val userId: String?
        get() = user?.id ?: null

    val accessToken: String?
        get() = user?.accessToken ?: null

    val hasLogin: Boolean
        get() = user != null

    val version: String
        get() = context.packageManager.getPackageInfo(context.packageName, 0).versionName

    val deviceId: String
        get() {
            val deviceId: String = UUID.randomUUID().toString()
            context.defaultSharedPreferences.edit().putString(IConstants.PREF_DEVICE_UUID, deviceId).apply()
            return deviceId
        }

    fun init(context: Context) {
        ApplicationContext.context = context
        user = loadUser()
    }

    private fun loadUser(): User? {
        return fromJson(context.defaultSharedPreferences.getString(IConstants.PREF_USER, ""), User::class.java)
    }

    private fun saveUser(user: User?) {
        context.defaultSharedPreferences.edit().putString(IConstants.PREF_USER, if (user == null) "" else toJson(user)).apply()
    }
}