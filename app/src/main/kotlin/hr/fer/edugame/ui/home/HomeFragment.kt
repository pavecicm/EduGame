package hr.fer.edugame.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import hr.fer.edugame.R
import hr.fer.edugame.extensions.setThrottlingClickListener
import hr.fer.edugame.ui.search.SearchUserActivity
import hr.fer.edugame.ui.shared.base.BaseFragment
import hr.fer.edugame.ui.shared.base.BasePresenter
import hr.fer.edugame.ui.shared.listeners.HomeListener
import kotlinx.android.synthetic.main.fragment_home.findOpponent
import kotlinx.android.synthetic.main.fragment_home.infoButton
import kotlinx.android.synthetic.main.fragment_home.lettersButton
import kotlinx.android.synthetic.main.fragment_home.logoutBtn
import kotlinx.android.synthetic.main.fragment_home.numbersButton
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeView {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override val layoutRes: Int = R.layout.fragment_home
    override fun providePresenter(): BasePresenter? = presenter

    @Inject
    lateinit var presenter: HomePresenter
    private lateinit var homeListener: HomeListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is HomeListener) {
            homeListener = context
        } else {
            throw RuntimeException(activity?.localClassName + " must implement " + HomeListener::class.java.name)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        infoButton.setThrottlingClickListener {
            homeListener.onNavigateToInfo()
        }
        logoutBtn.setThrottlingClickListener {
            presenter.logout()
        }
        numbersButton.setThrottlingClickListener {
            homeListener.onNavigateToNumbers()
        }
        lettersButton.setThrottlingClickListener {
            homeListener.onNavigateToLetters()
        }
        findOpponent.setThrottlingClickListener {
            homeListener.onNavigateToSearch()
        }
    }

    override fun logout() {
        homeListener.onLogout()
    }


}