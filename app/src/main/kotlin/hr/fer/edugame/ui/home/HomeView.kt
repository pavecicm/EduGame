package hr.fer.edugame.ui.home

import hr.fer.edugame.ui.shared.base.BaseView

interface HomeView: BaseView {
    fun logout()
    fun disableMultiplayer()
}