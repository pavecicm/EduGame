package hr.fer.edugame.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.DimenRes
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.updatePadding
import hr.fer.edugame.ui.shared.listeners.ThrottlingOnClickListener

fun View.showKeyboard() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    clearFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

@SuppressLint("UnsafeClickListener")
fun View.setThrottlingClickListener(onClick: () -> Unit) {
    setOnClickListener(object : ThrottlingOnClickListener() {
        override fun onClick() {
            onClick()
        }
    })
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun View.setPaddingHorizontal(@DimenRes padding: Int) {
    val pixelPadding = resources.getDimensionPixelOffset(padding)
    updatePadding(left = pixelPadding, right = pixelPadding)
}

fun View.setMarginHorizontal(@DimenRes margin: Int) {
    resources.getDimension(margin)
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}