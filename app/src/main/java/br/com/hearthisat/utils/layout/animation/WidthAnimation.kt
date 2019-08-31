package br.com.hearthisat.utils.layout.animation

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation


class WidthAnimation(private var view: View, private val width: Int) : Animation() {
    private val startWidth: Int = view.width

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val newWidth = (startWidth + (width - startWidth) * interpolatedTime).toInt()
        view.layoutParams.width = newWidth
        view.requestLayout()
    }

    override fun willChangeBounds(): Boolean {
        return true
    }
}