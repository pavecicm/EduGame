package hr.fer.edugame.data.firebase.interactors

import com.google.firebase.database.DatabaseReference
import hr.fer.edugame.constants.FIREBASE_GAMES_CHILD
import hr.fer.edugame.data.firebase.FirebaseDatabaseManager
import hr.fer.edugame.data.storage.prefs.PreferenceStore

abstract class GameInteractor(
    open val firebaseManager: FirebaseDatabaseManager,
    open val preferenceStore: PreferenceStore
) {
    protected val gameRoom: DatabaseReference =
        firebaseManager.databaseReference()
            .child(FIREBASE_GAMES_CHILD)
            .child(getGameRoom())
    protected val opponetGameRoom: DatabaseReference =
        firebaseManager
            .databaseReference()
            .child(FIREBASE_GAMES_CHILD)
            .child(getOpponentGameRoom())

    private fun getGameRoom() = preferenceStore.currentUserID + "_" + preferenceStore.opponentId

    private fun getOpponentGameRoom() = preferenceStore.opponentId + "_" + preferenceStore.currentUserID
}