package hr.fer.edugame.ui.rank

import hr.fer.edugame.data.firebase.interactors.RankInteractor
import hr.fer.edugame.data.models.User
import hr.fer.edugame.ui.shared.base.BasePresenter
import javax.inject.Inject

class RankListPresenter @Inject constructor(
    override val view: RankListView,
    private val rankInteractor: RankInteractor
) : BasePresenter(view) {

    private var rankList: List<User>? = null

    fun init() {
        rankInteractor.getRankList(this)
    }

    fun displayRankList(users: List<User>) {
        rankList = users
        handleShowMulptiplayerRank()
    }

    fun handleShowSinglePlayerRank() {
        rankList?.let {
            view.showSinglePlayerRank(it)
        }
    }

    fun handleShowMulptiplayerRank() {
        rankList?.let {
            view.showMultiplayerRank(it)
        }
    }
}