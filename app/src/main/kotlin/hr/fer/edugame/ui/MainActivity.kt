package hr.fer.edugame.ui

import android.content.Context
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import hr.fer.edugame.R
import hr.fer.edugame.extensions.intentFor
import hr.fer.edugame.extensions.replaceFragment
import hr.fer.edugame.ui.home.HomeFragment
import hr.fer.edugame.ui.home.info.InfoFragment
import hr.fer.edugame.ui.letters.LettersFragment
import hr.fer.edugame.ui.numbers.NumbersFragment
import hr.fer.edugame.ui.settings.SettingsFragment
import hr.fer.edugame.ui.shared.base.BaseActivity
import hr.fer.edugame.ui.shared.base.BasePresenter
import hr.fer.edugame.ui.shared.listeners.HomeListener

private const val BACK_STACK_ROOT_TAG = "root_fragment"

class MainActivity : BaseActivity(), HomeListener {

    companion object {
        const val EXTRA_USER = "EXTRA_USER"

        fun newInstance(context: Context, currentUser: FirebaseUser) = context.intentFor<MainActivity>(
            EXTRA_USER to currentUser
        )
    }

    override val layoutRes: Int = R.layout.activity_main
    override fun providePresenter(): BasePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(HomeFragment.newInstance(), R.id.fragmentContainer)
    }

    override fun onNavigateToLetters() {
        replaceFragment(
            LettersFragment.newInstance(),
            R.id.fragmentContainer,
            addToBackStack = true,
            rootTag = BACK_STACK_ROOT_TAG,
            popBackStack = true
        )
    }

    override fun onNavigateToNumbers() {
        replaceFragment(
            NumbersFragment.newInstance(),
            R.id.fragmentContainer,
            addToBackStack = true,
            rootTag = BACK_STACK_ROOT_TAG,
            popBackStack = true
        )
    }

    override fun onLogout() {
        finish()
    }

    override fun onNavigateToInfo() {
        replaceFragment(
            InfoFragment.newInstance(),
            R.id.fragmentContainer,
            addToBackStack = true,
            rootTag = BACK_STACK_ROOT_TAG,
            popBackStack = true
        )
    }
}
