package hr.fer.edugame.data.firebase.interactors

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import hr.fer.edugame.constants.FIREBASE_FINAL_WORD
import hr.fer.edugame.constants.FIREBASE_GAMES_CHILD
import hr.fer.edugame.constants.FIREBASE_GENERATED_LETTERS
import hr.fer.edugame.constants.FIREBASE_REACHED_FIFTY_POINTS
import hr.fer.edugame.constants.FIREBASE_WORDS_GAME
import hr.fer.edugame.data.firebase.FirebaseDatabaseManager
import hr.fer.edugame.data.storage.prefs.PreferenceStore
import hr.fer.edugame.ui.letters.LettersPresenter
import hr.fer.edugame.ui.letters.NO_FINAL_WORD
import javax.inject.Inject

class LettersGameInteractor @Inject constructor(
    private val firebaseManager: FirebaseDatabaseManager,
    private val preferenceStore: PreferenceStore
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

    private var lettersListeners: ValueEventListener? = null
    private var opponentGameRoomListener: ChildEventListener? = null
    private var reachedFiftyListener: ValueEventListener? = null

    private fun getGameRoom() = preferenceStore.currentUserID + "_" + preferenceStore.opponentId

    private fun getOpponentGameRoom() = preferenceStore.opponentId + "_" + preferenceStore.currentUserID

    fun setRandomLetters(presenter: LettersPresenter, letters: List<String>) {
        gameRoom.child(FIREBASE_GENERATED_LETTERS).setValue(letters)
        gameRoom.child(FIREBASE_FINAL_WORD).setValue("")
        presenter.resetCache()
    }

    fun listenForLetters(presenter: LettersPresenter) {
        lettersListeners = opponetGameRoom.child(FIREBASE_GENERATED_LETTERS).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                if (data.value != null) {
                    val letters: List<String> = data.value as List<String>
                    presenter.setLetters(letters)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

    fun listenForOpponentResult(presenter: LettersPresenter) {
        opponentGameRoomListener = opponetGameRoom.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(data: DataSnapshot, p1: String?) {
                if (data.key == FIREBASE_FINAL_WORD && data.value != null) {
                    val temp = data.value.toString()
                    if (temp != NO_FINAL_WORD) {
                        presenter.saveOpponentResult(temp)
                    }
                }
            }

            override fun onChildChanged(data: DataSnapshot, p1: String?) {
                if (data.key == FIREBASE_FINAL_WORD && data.value != null) {
                    val temp = data.value.toString()
                    if (temp != NO_FINAL_WORD) {
                        presenter.saveOpponentResult(temp)
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }
        })

        opponetGameRoom.child(FIREBASE_REACHED_FIFTY_POINTS).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                if (data.value == true) {
                    presenter.handleOpponentWin()
                    gameRoom.removeValue()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

    fun removeGameRoom() {
        gameRoom.removeValue()
    }

    fun finishRound(word: String) {
        gameRoom.child(FIREBASE_FINAL_WORD).setValue(word)
    }

    fun resetCalculatedNumbers() {
        gameRoom.child(FIREBASE_FINAL_WORD).setValue("")
        gameRoom.child(FIREBASE_WORDS_GAME).removeValue()
        gameRoom.child(FIREBASE_REACHED_FIFTY_POINTS).removeValue()
    }

    fun declareWin() {
        gameRoom.child(FIREBASE_REACHED_FIFTY_POINTS).setValue(true)
    }

    fun destroyListeners() {
        lettersListeners?.let {
            opponetGameRoom.child(FIREBASE_GENERATED_LETTERS).removeEventListener(it)
        }
        opponentGameRoomListener?.let {
            opponetGameRoom.removeEventListener(it)
        }
        reachedFiftyListener?.let {
            opponetGameRoom.child(FIREBASE_REACHED_FIFTY_POINTS).removeEventListener(it)
        }
    }
}