package hr.fer.edugame.extensions

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager

fun FragmentActivity.replaceFragment(
    fragment: Fragment,
    @IdRes container: Int,
    tag: String? = null,
    addToBackStack: Boolean = false,
    rootTag: String? = null,
    popBackStack: Boolean = false
) {
    if (popBackStack) {
        supportFragmentManager.popBackStack(rootTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
    val transaction = supportFragmentManager.beginTransaction()
    with(transaction) {
        replace(container, fragment, tag)
        if (addToBackStack) addToBackStack(rootTag)
        commitAllowingStateLoss()
    }
}