package hr.fer.edugame.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import hr.fer.edugame.R
import hr.fer.edugame.extensions.intentFor
import hr.fer.edugame.extensions.replaceFragment
import hr.fer.edugame.ui.home.HomeFragment
import hr.fer.edugame.ui.letters.LettersFragment
import hr.fer.edugame.ui.numbers.NumbersFragment
import hr.fer.edugame.ui.rank.RankFragment
import hr.fer.edugame.ui.search.SearchUserActivity
import hr.fer.edugame.ui.shared.base.BaseActivity
import hr.fer.edugame.ui.shared.listeners.HomeListener
import javax.inject.Inject

private const val BACK_STACK_ROOT_TAG = "root_fragment"

class MainActivity : BaseActivity(), HomeListener, MainView {

    companion object {
        const val REQUEST_CODE_SEARCH = 98

        fun newInstance(context: Context) = context.intentFor<MainActivity>()
    }

    override val layoutRes: Int = R.layout.activity_main
    override fun providePresenter(): MainPresenter = presenter

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(HomeFragment.newInstance(), R.id.fragmentContainer)
    }

    override fun onNavigateToLetters() {
        presenter.savePoints()
        replaceFragment(
            LettersFragment.newInstance(),
            R.id.fragmentContainer,
            addToBackStack = true,
            rootTag = BACK_STACK_ROOT_TAG,
            popBackStack = true
        )
    }

    override fun onNavigateToNumbers() {
        presenter.savePoints()
        replaceFragment(
            NumbersFragment.newInstance(),
            R.id.fragmentContainer,
            addToBackStack = true,
            rootTag = BACK_STACK_ROOT_TAG,
            popBackStack = true
        )
    }

    fun navigateToNumbers() {
        replaceFragment(
            NumbersFragment.newInstance(),
            R.id.fragmentContainer,
            addToBackStack = false,
            rootTag = BACK_STACK_ROOT_TAG
        )
    }

    override fun onLogout() {
        finish()
    }

    override fun onNavigateToRankList() {
        replaceFragment(
            RankFragment.newInstance(),
            R.id.fragmentContainer,
            addToBackStack = true,
            rootTag = BACK_STACK_ROOT_TAG,
            popBackStack = true
        )
    }

    override fun onNavigateToSearch() {
        startActivityForResult(SearchUserActivity.newInstance(this), REQUEST_CODE_SEARCH)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_SEARCH -> navigateToNumbers()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun finish() {
        presenter.onFinish()
        super.finish()
    }

    override fun goBack(goBack: Boolean) {
        this.goBack = goBack
    }
}
