package br.com.hearthisat.extensions

import android.view.View
import android.view.ViewGroup
import java.time.Duration

fun View.setMargins(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
    val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return
    lp.setMargins(left, top, right, bottom)
    layoutParams = lp
}

fun View.animate(alpha: Float, duration: Long = 200) {
    this.animate().alpha(alpha).duration = duration
}