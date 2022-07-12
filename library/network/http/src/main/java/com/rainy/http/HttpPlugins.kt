package com.rainy.http

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.rainy.http.factory.Factory
import okhttp3.logging.HttpLoggingInterceptor


/**
 *
 * @Description:
 * @author : rainy
 * @date: 2022/7/3 00:31
 * @version: 1.0.0
 */
object HttpPlugins {
    fun setBaseUrl(url: String) = apply { HttpManager.config.baseUrl = url }

    fun setFactory(factory: Factory) = apply { HttpManager.factory = factory }

    fun setDebug(isDebug: Boolean) = apply { HttpManager.debug = isDebug }

    fun setErrorHandler(action: (Throwable) -> Unit) = apply { HttpManager.errorAction = action }

    fun setLogLevel(level: HttpLoggingInterceptor.Level) =
        apply { HttpManager.level = level }

    fun init(context: Context): Boolean {
        val factory = HttpManager.factory
        if (factory == null) {
            HttpManager.errorAction?.invoke(Throwable("factory is must set"))
            return false
        }
        if (!checkPermission(context)) {
            HttpManager.errorAction?.invoke(Throwable("missing INTERNET permission"))
            return false
        }
        factory.onCreate(context)
        return true
    }

    private fun checkPermission(
        context: Context,
        permission: String = Manifest.permission.INTERNET
    ): Boolean = ContextCompat.checkSelfPermission(context, permission) ==
            PackageManager.PERMISSION_GRANTED
}