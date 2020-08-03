package com.android.apps.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.contains
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.ViewPager
import com.android.app.tikitest.R
import com.kenilt.loopingviewpager.scroller.AutoScroller
import com.kenilt.loopingviewpager.widget.LoopingViewPager
import kotlinx.android.synthetic.main.layout_banner.view.*
import java.text.DecimalFormat


fun Context.toast(message: String = "", messageId: Int = -1) {
    when {
        message.isNotBlank() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        messageId != -1 -> Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show()
        else -> throw IllegalArgumentException("You have not set any message.")
    }
}

fun Activity.transparentStatusBar() {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
    window.navigationBarColor = Color.TRANSPARENT
    window.statusBarColor = Color.TRANSPARENT
}

fun Activity.setWindowFlag(bits: Int, on: Boolean) {
    val win = window
    val winParams = win.attributes
    if (on) {
        winParams.flags = winParams.flags or bits
    } else {
        winParams.flags = winParams.flags and bits.inv()
    }
    win.attributes = winParams
}

fun View.waitForRendered(action : () -> Unit) {
    val vto = viewTreeObserver
    vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            when {
                vto.isAlive -> {
                    vto.removeOnGlobalLayoutListener(this)
                    action.invoke()
                }
                else -> viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        }
    })
}

fun runOnUIThread(action: ()-> Unit) {
    Handler(Looper.getMainLooper()).post(action)
}

fun <T> zip(list : List<List<T>>?) : List<T?> {
    if (list.isNullOrEmpty()) return emptyList()
    val max = list.map { it.size }.max() ?: return listOf()
    val result = mutableListOf<T?>()
    for (i in 0 until max) {
        result.addAll(list.map { it.getOrNull(i) })
    }
    return result
}

fun LoopingViewPager.setAutoScroll(lifecycle: Lifecycle?) {
    val autoScroller = AutoScroller(this, lifecycle, 3000)
    autoScroller.isAutoScroll = true
    addOnPageChangeListener(object : SimplePageChangeListener() {
        override fun onPageScrollStateChanged(state: Int) {
            when (state) {
                ViewPager.SCROLL_STATE_IDLE -> autoScroller.isAutoScroll = true
                ViewPager.SCROLL_STATE_DRAGGING -> autoScroller.isAutoScroll = false
            }
        }
    })
}

open class SimplePageChangeListener : ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {

    }
}

fun Context.parseMoneyStringVND(money: Long, unitStringId: Int = R.string.text_price_product): String {
    return getString(unitStringId, DecimalFormat("#,###").format(money.toInt())).replace(",",".")
}


val Int.toDp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun LinearLayout.addViewOrNot(view : View) {
    if (!children.contains(view)) {
        addView(view)
    }
}
