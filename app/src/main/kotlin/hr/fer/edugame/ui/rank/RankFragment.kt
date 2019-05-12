package hr.fer.edugame.ui.rank

import android.os.Bundle
import android.view.View
import hr.fer.edugame.R
import hr.fer.edugame.data.models.User
import hr.fer.edugame.ui.shared.adapters.SimplePagerAdapter
import hr.fer.edugame.ui.shared.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_rang.rangListViewPager
import kotlinx.android.synthetic.main.fragment_rang.tabLayout
import javax.inject.Inject

class RankFragment : BaseFragment(), RankView {

    companion object {
        fun newInstance() = RankFragment()
    }

    override val layoutRes: Int = R.layout.fragment_rang
    override fun providePresenter(): RankPresenter = presenter

    @Inject
    lateinit var presenter: RankPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        presenter.init()
    }

    fun initUI() {
        setupToolbar(R.string.rang_list)
    }

    override fun initViewPager(listData: List<User>) {
        with(rangListViewPager) {
            this.adapter = SimplePagerAdapter(childFragmentManager).apply {
                addFragment(RangListFragment.newInstance(listData, PointsType.MULTIPLAYER), getString(R.string.multiplayer))
                addFragment(RangListFragment.newInstance(listData, PointsType.SINGLEPLAYER), getString(R.string.singlePlayer))
            }
            tabLayout.setupWithViewPager(this)
        }
    }
}