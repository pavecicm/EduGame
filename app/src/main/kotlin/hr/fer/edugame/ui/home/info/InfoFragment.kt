package hr.fer.edugame.ui.home.info

import android.os.Bundle
import android.view.View
import hr.fer.edugame.R
import hr.fer.edugame.ui.shared.base.BaseFragment
import hr.fer.edugame.ui.shared.base.BasePresenter
import kotlinx.android.synthetic.main.toolbar.toolbar

class InfoFragment : BaseFragment() {

    companion object {
        fun newInstance() = InfoFragment()
    }

    override val layoutRes: Int = R.layout.fragment_info
    override fun providePresenter(): BasePresenter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    fun initUI() {
        toolbar.setTitle(R.string.about_title)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_navigation)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
}