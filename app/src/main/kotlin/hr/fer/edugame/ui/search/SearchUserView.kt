package hr.fer.edugame.ui.search

import hr.fer.edugame.data.models.User
import hr.fer.edugame.ui.shared.base.BaseView

interface SearchUserView : BaseView {
    fun addOpponentFoundView(user: User)
    fun navigateToGameActivity(opponent: User)
    fun showGameRequestDialog(id: String)
    fun removeOpponnent(id: String)
    fun showCallRefused()
}