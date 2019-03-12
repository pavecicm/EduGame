package hr.fer.edugame.ui.main

import hr.fer.edugame.data.firebase.interactors.SearchOpponentInteractor
import hr.fer.edugame.data.storage.prefs.PreferenceStore
import hr.fer.edugame.ui.shared.base.BasePresenter
import javax.inject.Inject

class MainPresenter @Inject constructor(
    override val view: MainView,
    private val interactor: SearchOpponentInteractor,
    private val preferenceStore: PreferenceStore
) : BasePresenter(view) {

    fun onFinish() {
        interactor.removeGameRoom(preferenceStore.currentUserID, preferenceStore.opponentId)
        preferenceStore.opponentId = ""
        preferenceStore.isInitiator = false
        preferenceStore.isSinglePlayerEnabled = false
    }
}