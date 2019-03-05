package hr.fer.edugame.ui.shared.listeners

import android.view.View

private const val THROTTLING_DURATION = 500

abstract class ThrottlingOnClickListener : View.OnClickListener {

    private var lastClickedTime = 0L

    abstract fun onClick()

    override fun onClick(v: View?) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickedTime > THROTTLING_DURATION) {
            onClick()
        }
        lastClickedTime = currentTime
    }
}
