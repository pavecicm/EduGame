package hr.fer.edugame.ui.main

import hr.fer.edugame.data.firebase.interactors.RankInteractor
import hr.fer.edugame.data.firebase.interactors.SearchOpponentInteractor
import hr.fer.edugame.data.models.User
import hr.fer.edugame.data.storage.prefs.PreferenceStore
import hr.fer.edugame.ui.shared.base.BasePresenter
import hr.fer.edugame.ui.shared.helpers.getUser
import javax.inject.Inject

class MainPresenter @Inject constructor(
    override val view: MainView,
    private val interactor: SearchOpponentInteractor,
    private val rankInteractor: RankInteractor,
    private val preferenceStore: PreferenceStore
) : BasePresenter(view) {

    fun onFinish() {
        interactor.removeGameRoom(preferenceStore.currentUserID, preferenceStore.opponentId)
        preferenceStore.opponentId = ""
        preferenceStore.isInitiator = false
        preferenceStore.isSinglePlayerEnabled = false
    }

    fun savePoints() {
        if(preferenceStore.isSinglePlayerEnabled) {
            rankInteractor.savePoints(preferenceStore.getUser())
        }
    }
}