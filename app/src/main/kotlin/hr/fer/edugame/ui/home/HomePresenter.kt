package hr.fer.edugame.ui.home

import hr.fer.edugame.ui.shared.base.BasePresenter
import javax.inject.Inject

class HomePresenter @Inject constructor(
    override val view: HomeView
) : BasePresenter(view) {

}