package br.com.hearthisat.utils.layout.animation

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation


class HeightAnimation(private var view: View, private val height: Int) : Animation() {
    private val startHeight: Int = view.height

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val newHeight = (startHeight + (height - startHeight) * interpolatedTime).toInt()
        view.layoutParams.height = newHeight
        view.requestLayout()
    }

    override fun willChangeBounds(): Boolean {
        return true
    }
}