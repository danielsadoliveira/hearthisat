package br.com.hearthisat.utils.general

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.afollestad.assent.Assent
import com.afollestad.assent.AssentCallback

/**
 * Created by danielsdo on 17/08/17.
 */

val PERMISSION_REQUEST_CODE: Int get() { return 221 }

fun Activity.askPermission(permission: String, granted: () -> Unit, notGranted: (() -> Unit)? = null) {
    askPermissionImpl(this, permission, PERMISSION_REQUEST_CODE, granted, notGranted)
}

fun Fragment.askPermission(permission: String, granted: () -> Unit, notGranted: (() -> Unit)? = null) {
    askPermissionImpl(context!!, permission, PERMISSION_REQUEST_CODE, granted, notGranted)
}

@SuppressLint("Range")
fun askPermissionImpl(context: Context, permission: String, requestCode: Int, granted: () -> Unit, notGranted: (() -> Unit)? = null) {
    require(requestCode in 0..255) { "RequestCode: $requestCode is not a valid requestCode, valid codes are from 0 to 255" }

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        granted.invoke()
    } else {
        if (context.isPermissionGranted(permission)) {
            granted()
        } else {
            Assent.requestPermissions(AssentCallback {
                if (it.allPermissionsGranted()) {
                    granted()
                } else {
                    notGranted?.invoke()
                }
            }, requestCode, permission)
        }
    }
}

fun Context.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Activity.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Fragment.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(context!!, permission) == PackageManager.PERMISSION_GRANTED
}