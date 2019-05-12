package hr.fer.edugame.ui.rank

import hr.fer.edugame.data.firebase.interactors.RankInteractor
import hr.fer.edugame.data.models.User
import hr.fer.edugame.ui.shared.base.BasePresenter
import javax.inject.Inject

class RankPresenter @Inject constructor(
    override val view: RankView,
    private val rankInteractor: RankInteractor
) : BasePresenter(view) {

    private var rankList: List<User>? = null

    fun init() {
        view.showProgress()
        rankInteractor.getRankList(this)
    }

    fun displayRankList(users: List<User>) {
        view.hideProgress()
        rankList = users
        view.initViewPager(users)
    }
}