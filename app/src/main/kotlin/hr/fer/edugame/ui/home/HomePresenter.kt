package hr.fer.edugame.ui.home

import com.google.firebase.auth.FirebaseAuth
import hr.fer.edugame.data.storage.prefs.PreferenceStore
import hr.fer.edugame.ui.shared.base.BasePresenter
import javax.inject.Inject

class HomePresenter @Inject constructor(
    override val view: HomeView,
    private val auth: FirebaseAuth,
    private val preferenceStore: PreferenceStore
) : BasePresenter(view) {

    fun init() {
        if(!preferenceStore.hasInternet) {
            view.disableMultiplayer()
        }
        preferenceStore.gamePoints = 0
    }

    fun logout() {
        auth.signOut()
        preferenceStore.currentUserID = ""
        preferenceStore.isInitiator = false
        preferenceStore.opponentId = ""
        preferenceStore.email = ""
        view.logout()
    }

    fun startSinglePlayer() {
        preferenceStore.isSinglePlayerEnabled = true
    }

    fun startMultiplayer() {
        preferenceStore.isSinglePlayerEnabled = false
    }
}