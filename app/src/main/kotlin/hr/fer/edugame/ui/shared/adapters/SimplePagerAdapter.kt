package hr.fer.edugame.ui.shared.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

open class SimplePagerAdapter(
    supportFragmentManager: FragmentManager
) : FragmentPagerAdapter(supportFragmentManager) {

    protected val fragments: MutableList<Fragment> = mutableListOf()
    private val titles: MutableList<String> = mutableListOf()

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        titles.add(title)
    }

    fun getFragment(position: Int) = fragments[position]

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int) = titles[position]
}