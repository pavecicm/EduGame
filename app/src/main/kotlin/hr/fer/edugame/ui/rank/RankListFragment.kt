package hr.fer.edugame.ui.rank

import android.os.Bundle
import android.view.View
import hr.fer.edugame.R
import hr.fer.edugame.data.models.User
import hr.fer.edugame.ui.shared.adapters.RankListAdapter
import hr.fer.edugame.ui.shared.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_rank_list.rankList
import javax.inject.Inject

class RankListFragment : BaseFragment(), RankListView {

    companion object {
        fun newInstance() = RankListFragment()
    }

    override val layoutRes: Int = R.layout.fragment_rank_list
    override fun providePresenter(): RankListPresenter = presenter

    @Inject
    lateinit var presenter: RankListPresenter

    private val multiplayerAdapter = RankListAdapter()
    private val singlePlayerAdapter = RankListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rankList.adapter = multiplayerAdapter
        presenter.init()
    }

    override fun showMultiplayerRank(listData: List<User>) {
        multiplayerAdapter.updateItemsMultiplayer(listData)
    }

    override fun showSinglePlayerRank(listData: List<User>) {
        singlePlayerAdapter.updateItemsSinglePlayer(listData)
    }
}