package com.namnp.androidjetpack.extensions

// 1 Value from EditText
// 2 Start Activity
// 3 Check Network

//4. Check Permission

//6. toEditable()

//7. Screen Size
//import android.content.Context
//import android.os.Build

//8. System Service Managers
//import android.content.Context
//import android.net.ConnectivityManager
//import android.view.WindowManager
//import androidx.core.content.ContextCompat

//9. Copy to clipboard
//import android.content.Context
//import androidx.core.content.ContextCompat

// 10
import android.app.Activity
import android.app.DownloadManager
import android.app.NotificationManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Insets
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.Editable
import android.util.DisplayMetrics
import android.util.Size
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

// ------------------------

// 1. Value from EditText
val EditText.value
    get() = text?.toString() ?: ""
// Usage: val name = edtName.value

// 2. Start Activity
fun Activity.startActivity(
    cls: Class<*>,
    finishCallingActivity: Boolean = true,
    block: (Intent.() -> Unit)? = null
) {
    val intent = Intent(this, cls)
    block?.invoke(intent)
    startActivity(intent)
    if (finishCallingActivity) finish()
}
// Usage
//startActivity(MainActivity::class.java) // Without Intent modification
//startActivity(MainActivity::class.java) {
//     access the intent object in this block
//    putExtra("key", "value")
//}

//3. Check Network
@RequiresApi(Build.VERSION_CODES.M)
fun Context.isNetworkAvailable(): Boolean {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
    return if (capabilities != null) {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    } else false
}

@RequiresApi(Build.VERSION_CODES.M)
fun Fragment.isNetworkAvailable() = requireContext().isNetworkAvailable()
//Usage:
//
//if (isNetworkAvailable()) {
//    // Called when network is available
//} else {
//    // Called when network not available
//}

//4. Check Permission
fun Context.isPermissionGranted(permission: String) = run {
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}
//Usage:
//if (isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
//    // Block runs if permission is granted
//} else {
//    // Ask for permission
//}

//5. Remove whitespaces
fun String.removeAllWhitespaces(): String {
    return this.replace("\\s+".toRegex(), "")
}

fun String.removeDuplicateWhitespaces(): String {
    return this.replace("\\s+".toRegex(), " ")
}
//Usage:
//"Hello,     world!!!".removeAllWhitespaces() // Output: Hello,world!!!
//"Hello,     world!!!".removeDuplicateWhitespaces() // Output: Hello, world!!!

//6. toEditable()
fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

//Usage:
//edtName.text = "First name".toEditable()

//7. Screen Size
val Context.screenSize: Size
    get() {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val size = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = windowManager.currentWindowMetrics
            val windowInsets = metrics.windowInsets
            val insets: Insets = windowInsets.getInsetsIgnoringVisibility(
                WindowInsets.Type.navigationBars()
                        or WindowInsets.Type.displayCutout()
            )

            val insetsWidth: Int = insets.right + insets.left
            val insetsHeight: Int = insets.top + insets.bottom
            val bounds: Rect = metrics.bounds
            Size(
                bounds.width() - insetsWidth,
                bounds.height() - insetsHeight
            )
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay?.getMetrics(displayMetrics)
            val height = displayMetrics.heightPixels
            val width = displayMetrics.widthPixels
            Size(width, height)
        }
        return size
    }

//Usage:
//val size = screenSize
//val deviceHeight = size.height
//val deviceWidth = size.width

//8. System Service Managers
val Context.windowManager
    get() = ContextCompat.getSystemService(this, WindowManager::class.java)

val Context.connectivityManager
    get() = ContextCompat.getSystemService(this, ConnectivityManager::class.java)

val Context.notificationManager
    get() = ContextCompat.getSystemService(this, NotificationManager::class.java)

val Context.downloadManager
    get() = ContextCompat.getSystemService(this, DownloadManager::class.java)

//Usage:
//val manager = downloadManager // In Activity
//val manager = requireContext().downloadManager// In Fragment

//9. Copy to clipboard
fun String.copyToClipboard(context: Context) {
    val clipboardManager = ContextCompat.getSystemService(context, ClipboardManager::class.java)
    val clip = ClipData.newPlainText("clipboard", this)
    clipboardManager?.setPrimaryClip(clip)
}

//Usage:
//"This is clipboard".copyToClipboard(context)

//10. Boolean Expressions
@OptIn(ExperimentalContracts::class)
fun Boolean?.isTrue(): Boolean {
    contract {
        returns(true) implies (this@isTrue != null)
    }
    return this == true
}

@OptIn(ExperimentalContracts::class)
fun Boolean?.isFalse(): Boolean {
    contract {
        returns(true) implies (this@isFalse != null)
    }
    return this == false
}

val Boolean?.orTrue: Boolean
    get() = this ?: true

val Boolean?.orFalse: Boolean
    get() = this ?: false

//Usage:
//lateinit var any: Boolean? // Assume that, this property is already assigned
//if (any.isTrue()) {
//    // Run when any is true only
//}
//if (any.isFalse()) {
//    // Run when any is false only
//}
//val any1: Boolean = any.orTrue // If any is null then any1 = true otherwise any1 = any
//val any2: Boolean = any.orFalse // If any is null then any1 = false otherwise any1 = any