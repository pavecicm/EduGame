package hr.fer.edugame.ui.search

import android.os.Handler
import android.support.v4.os.HandlerCompat
import hr.fer.edugame.data.firebase.interactors.SearchOpponentInteractor
import hr.fer.edugame.data.models.User
import hr.fer.edugame.data.storage.prefs.PreferenceStore
import hr.fer.edugame.ui.shared.base.BasePresenter
import javax.inject.Inject

class SearchUserPresenter @Inject constructor(
    override val view: SearchUserView,
    private val searchOpponentInteractor: SearchOpponentInteractor,
    private val preferenceStore: PreferenceStore
) : BasePresenter(view) {

    private lateinit var opponent: User
    private lateinit var currentUser: User

    fun init() {
        currentUser = User(preferenceStore.currentUserID, preferenceStore.username)
    }

    fun addUserToOnlineUser(user: User) = searchOpponentInteractor.addUser(user)

    fun removeUserFromOnlineUser(uid: String) = searchOpponentInteractor.removeUser(uid)

    fun searchForOpponent(user: User) = searchOpponentInteractor.searchForOpponent(this, user)

    fun addOpponentInList(opponent: User) {
        this.opponent = opponent
        view.addOpponentFoundView(opponent)
    }

    fun removeOpponent(id: String) {
        view.removeOpponnent(id)
    }

    fun startGameAsInitiator() {
        preferenceStore.isInitiator = true
        startGame()
    }

    fun startGame() {
        preferenceStore.opponentId = opponent.id
        view.navigateToGameActivity(opponent)
    }

    fun onStart() {
        if (currentUser.id.isNotEmpty()) {
            addUserToOnlineUser(currentUser)
            searchForOpponent(currentUser)
        }
    }

    fun onDestroy() {
        if (currentUser.id.isNotEmpty()) {
            removeUserFromOnlineUser(currentUser.id)
        }
    }

    fun callPlayer(id: String) {
        view.showProgress()
        searchOpponentInteractor.createCurrentGameRoom(currentUid = currentUser.id, opponentUid = id)
        searchOpponentInteractor.listenForOpponentGameRoom(this, currentUid = currentUser.id, opponentUid = id)
    }

    fun showCall(id: String) {
        view.showGameRequestDialog(id)
    }

    fun handleCallRefused() {
        view.hideProgress()
        view.showCallRefused()
    }

    fun acceptCall(id: String) {
        view.showProgress()
        searchOpponentInteractor.createCurrentGameRoom(currentUid = currentUser.id, opponentUid = id)
        searchOpponentInteractor.removeUser(id)
        searchOpponentInteractor.removeListeners()
        preferenceStore.isInitiator = false
        Handler().postDelayed({
            startGame()
        }, 1000)

    }

    fun declineCall(id: String) {
        searchOpponentInteractor.removeGameRoom(preferenceStore.currentUserID, id)
        searchOpponentInteractor.removeOpponentGameRoom(preferenceStore.currentUserID, id)
    }
}