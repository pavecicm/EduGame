package hr.fer.edugame.ui.search

import hr.fer.edugame.data.firebase.FirebaseDatabaseManager
import hr.fer.edugame.data.firebase.interactors.SearchOpponentInteractor
import hr.fer.edugame.data.models.User
import hr.fer.edugame.data.storage.prefs.PreferenceStore
import hr.fer.edugame.ui.shared.base.BasePresenter
import java.lang.ref.WeakReference
import javax.inject.Inject

class SearchUserPresenter @Inject constructor(
    override val view: SearchUserView,
    private val searchOpponentInteractor: SearchOpponentInteractor,
    private val preferenceStore: PreferenceStore
): BasePresenter(view) {

    private lateinit var weakView: WeakReference<SearchUserView>
    private lateinit var opponent: User
    private lateinit var currentUser: User

    fun init() {
        currentUser = User(preferenceStore.currentUserID, preferenceStore.email)
    }

    fun addUserToOnlineUser(user: User) = searchOpponentInteractor.addUser(user)

    fun removeUserFromOnlineUser(uid: String) = searchOpponentInteractor.removeUser(uid)

    fun searchForOpponent(user: User) = searchOpponentInteractor.searchForOpponent(this, user)

    fun addOpponentInList(opponent: User)  {
        this.opponent = opponent
        view.addOpponentFoundView(opponent.id, opponent.email)
    }

    fun removeOpponent(id: String) {
        view.removeOpponnent(id)
    }

    fun startGame() {
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
        searchOpponentInteractor.createCurrentGameRoom(this, currentUid = currentUser.id, opponentUid = id)
        searchOpponentInteractor.listenForOpponentGameRoom(this, currentUid = currentUser.id, opponentUid = id)
    }

    fun showCall(id: String){
        view.showGameRequestDialog(id)
    }

    fun acceptCall(id: String) {
        searchOpponentInteractor.createCurrentGameRoom(this, currentUid = currentUser.id, opponentUid = id)
        searchOpponentInteractor.removeUser(id)
        startGame()
    }
}