package hr.fer.edugame.ui.settings

import android.os.Bundle
import android.view.View
import hr.fer.edugame.R
import hr.fer.edugame.ui.shared.base.BaseFragment
import kotlinx.android.synthetic.main.toolbar.toolbar
import javax.inject.Inject

class SettingsFragment : BaseFragment(), SettingsView {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override val layoutRes: Int = R.layout.fragment_settings
    override fun providePresenter(): SettingsPresenter = presenter

    @Inject
    lateinit var presenter: SettingsPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        toolbar.setTitle(R.string.settings)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_navigation)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
}