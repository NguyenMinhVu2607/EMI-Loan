package com.example.emiloan.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import com.example.emiloan.R
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.round

@SuppressLint("NewApi")
fun Activity.changeStatusBarColor(color: Int, lightStatusBar: Boolean = false) {
    this.window?.statusBarColor = resources.getColor(color, theme)
    if (lightStatusBar)
        this.lightStatusBar()

}

fun Activity.lightStatusBar() {
    val decorView: View? = this.window?.decorView
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val wic = decorView?.windowInsetsController
        wic?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    } else
        decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}



fun View.getLocationOnScreen(): Point {
    val location = IntArray(2)
    this.getLocationOnScreen(location)
    return Point(location[0], location[1])
}
fun View.getYearNow(): Int {
    var time = System.currentTimeMillis()
    val currentDate = Date(time)
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = currentDate
    val year: Int = calendar.get(Calendar.YEAR)
    return year
}

fun View.getViewCenterLocationOnScreen(): Point {
    val location = getLocationOnScreen()
    val centerX = location.x + measuredWidth / 2
    val centerY = location.y + measuredHeight / 2
    return Point(centerX, centerY)
}


fun Context.openWebPage(url: String) {
    val webpage: Uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, webpage)
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}

fun ViewGroup.resizeChildren(newWidth: Int, newHeight: Int) {
    // size is in pixels so make sure you have taken device display density into account
    val childCount = childCount
    for (i in 0 until childCount) {
        val v: View = getChildAt(i)
        if (v is ViewGroup) {
            v.layoutParams = ViewGroup.LayoutParams(newWidth, newHeight)
        }
    }
}
fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.inVisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}
fun Activity.showCenterToast(
    message: String,
    duration: Long = 2000L // 2 giây
) {
    // 1. Lấy root view của Activity hiện tại
    val rootView = this.findViewById<ViewGroup>(android.R.id.content) ?: return

    // 2. Inflate layout custom của bạn
    val inflater = LayoutInflater.from(this)
    val toastView = inflater.inflate(R.layout.layout_custom_toast, rootView, false)

    // 3. Set nội dung
    val tvToast = toastView.findViewById<TextView>(R.id.tvToast)
    tvToast.text = message

    // 4. Cấu hình vị trí ra giữa màn hình
    val params = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.WRAP_CONTENT,
        FrameLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        gravity = Gravity.CENTER
        // Có thể thêm margin nếu cần
        // bottomMargin = 100
    }
    toastView.layoutParams = params

    // [Tùy chọn] Set elevation để nó nổi lên trên các view khác
    ViewCompat.setElevation(toastView, 10f)

    // 5. Thêm View vào màn hình (Sẽ hiện ngay lập tức ở giữa, không bị giật)
    rootView.addView(toastView)

    // [Tùy chọn] Animation hiện ra (Fade In)
    toastView.alpha = 0f
    toastView.animate().alpha(1f).setDuration(200).start()

    // 6. Xóa View sau khoảng thời gian duration
    Handler(Looper.getMainLooper()).postDelayed({
        // Animation ẩn đi (Fade Out)
        toastView.animate()
            .alpha(0f)
            .setDuration(200)
            .withEndAction {
                // Xóa view khỏi layout sau khi ẩn xong
                if (toastView.parent != null) {
                    rootView.removeView(toastView)
                }
            }
            .start()
    }, duration)
}
fun View.setOnSingleClickListener(interval: Long = 500L, onSingleClick: (View) -> Unit) {
    var lastClickTime: Long = 0
    setOnClickListener { v ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime >= interval) {
            lastClickTime = currentTime
            onSingleClick(v)
        }
    }
}
fun Long.formatDuration(): String {
    val hours = TimeUnit.MILLISECONDS.toHours(this)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this) % 60

    return if (hours > 0) {
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%02d:%02d", minutes, seconds)
    }
}
 fun createLetterAvatar(context: Context, name: String): Drawable {
    val size = 120
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint()
    val firstLetter = if (name.isNotBlank()) name.first().toString().uppercase() else "?"
    val colors = listOf("#212121")
    val colorIndex = abs(name.hashCode()) % colors.size
    paint.color = Color.parseColor(colors[colorIndex])
    paint.style = Paint.Style.FILL
    paint.isAntiAlias = true
    val center = size / 2f
    canvas.drawCircle(center, center, center, paint)
    paint.color = Color.WHITE
    paint.textSize = size * 0.5f
    paint.typeface = Typeface.DEFAULT_BOLD
    paint.textAlign = Paint.Align.CENTER
    val yPos = (canvas.height / 2) - ((paint.descent() + paint.ascent()) / 2)
    canvas.drawText(firstLetter, center, yPos, paint)
    return BitmapDrawable(context.resources, bitmap)
}
@SuppressLint("HardwareIds")
fun Context.getAndroidID(): String {
    val deviceId = try {
        Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )
    } catch (e: Exception) {
        // Fallback nếu không lấy được ANDROID_ID
        "${Build.BRAND}_${Build.DEVICE}_${Build.ID}"
    }
    return deviceId
}