package hr.fer.edugame.ui.rank

import hr.fer.edugame.data.models.User
import hr.fer.edugame.ui.shared.base.BaseView

interface RankView : BaseView {
//    fun showSinglePlayerRank(listData: List<User>)
//    fun showMultiplayerRank(listData: List<User>)
    fun initViewPager(listData: List<User>)
}