package hr.fer.edugame.data.firebase.interactors

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import hr.fer.edugame.constants.FIREBASE_CALCULATED_NUMBER
import hr.fer.edugame.constants.FIREBASE_GAMES_CHILD
import hr.fer.edugame.constants.FIREBASE_NUMBERS_GAME
import hr.fer.edugame.constants.FIREBASE_REACHED_FIFTY_POINTS
import hr.fer.edugame.constants.FIREBASE_WANTED_NUMBER
import hr.fer.edugame.constants.NO_CALCULATED_NUMBER
import hr.fer.edugame.data.firebase.FirebaseDatabaseManager
import hr.fer.edugame.data.storage.prefs.PreferenceStore
import hr.fer.edugame.ui.numbers.NumbersPresenter
import javax.inject.Inject

class NumbersGameInteractor @Inject constructor(
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

    private var wantedNumberListener: ValueEventListener? = null
    private var numbersGameListener: ValueEventListener? = null
    private var opponentGameRoomListener: ChildEventListener? = null
    private var reachedFiftyListener: ValueEventListener? = null


    private fun getGameRoom() = preferenceStore.currentUserID + "_" + preferenceStore.opponentId

    private fun getOpponentGameRoom() = preferenceStore.opponentId + "_" + preferenceStore.currentUserID


    fun setRandomNumbers(presenter: NumbersPresenter, numbers: List<Int>, wantedNumber: Int) {
        gameRoom.child(FIREBASE_NUMBERS_GAME).setValue(numbers)
        gameRoom.child(FIREBASE_WANTED_NUMBER).setValue(wantedNumber)
        gameRoom.child(FIREBASE_CALCULATED_NUMBER).setValue(NO_CALCULATED_NUMBER)
        presenter.resetCache()
    }

    fun listenForNumbers(presenter: NumbersPresenter) {
        numbersGameListener = opponetGameRoom.child(FIREBASE_NUMBERS_GAME).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                if (data.value != null) {
                    val numbers: List<Int> = data.value as List<Int>
                    presenter.setGivenNumbers(numbers)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })

        wantedNumberListener = opponetGameRoom.child(FIREBASE_WANTED_NUMBER).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                if (data.value != null) {
                    val wantedNumber: Int = data.value.toString().toInt()
                    presenter.setWantedNumber(wantedNumber)
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    fun listenForOpponentResult(presenter: NumbersPresenter) {
        opponentGameRoomListener = opponetGameRoom.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(data: DataSnapshot, p1: String?) {
                if (data.key == FIREBASE_CALCULATED_NUMBER && data.value != null) {
                    val temp: Int = data.value.toString().toInt()
                    if (temp != NO_CALCULATED_NUMBER) {
                        presenter.saveOpponentResult(temp)
                    }
                }
            }

            override fun onChildChanged(data: DataSnapshot, p1: String?) {
                if (data.key == FIREBASE_CALCULATED_NUMBER && data.value != null) {
                    val temp: Int = data.value.toString().toInt()
                    if (temp != NO_CALCULATED_NUMBER) {
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

        reachedFiftyListener = opponetGameRoom.child(FIREBASE_REACHED_FIFTY_POINTS).addValueEventListener(object : ValueEventListener {
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

    fun finishRound(number: Int) {
        gameRoom.child(FIREBASE_CALCULATED_NUMBER).setValue(number)
    }

    fun resetCalculatedNumbers() {
        gameRoom.child(FIREBASE_CALCULATED_NUMBER).setValue(NO_CALCULATED_NUMBER)
        opponetGameRoom.child(FIREBASE_CALCULATED_NUMBER).setValue(NO_CALCULATED_NUMBER)
        gameRoom.child(FIREBASE_WANTED_NUMBER).removeValue()
        gameRoom.child(FIREBASE_NUMBERS_GAME).removeValue()
        gameRoom.child(FIREBASE_REACHED_FIFTY_POINTS).removeValue()
    }

    fun declareWin() {
        gameRoom.child(FIREBASE_REACHED_FIFTY_POINTS).setValue(true)
    }

    fun destroyListeners() {
        opponentGameRoomListener?.let {
            opponetGameRoom.removeEventListener(it)
        }
        numbersGameListener?.let {
            opponetGameRoom.child(FIREBASE_NUMBERS_GAME).removeEventListener(it)
        }
        wantedNumberListener?.let {
            opponetGameRoom.child(FIREBASE_WANTED_NUMBER).removeEventListener(it)
        }
        reachedFiftyListener?.let {
            opponetGameRoom.child(FIREBASE_REACHED_FIFTY_POINTS).removeEventListener(it)
        }
    }
}