package hr.fer.edugame.data.firebase.interactors

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import hr.fer.edugame.constants.FIREBASE_GAMES_CHILD
import hr.fer.edugame.constants.FIREBASE_USERS_ONLINE_CHILD
import hr.fer.edugame.data.firebase.FirebaseDatabaseManager
import hr.fer.edugame.data.models.User
import hr.fer.edugame.ui.search.SearchUserPresenter
import javax.inject.Inject

class SearchOpponentInteractor @Inject constructor(
    private val firebaseManager: FirebaseDatabaseManager
) {

    private val usersOnlineChildRef: DatabaseReference = firebaseManager.databaseReference().child(FIREBASE_USERS_ONLINE_CHILD)
    private val gamesChildRef: DatabaseReference = firebaseManager.databaseReference().child(FIREBASE_GAMES_CHILD)
//    private var opponentFound: Boolean = false
    private var shouldWaitForCall: Boolean = true
    private var opponentGameRoomListener: ChildEventListener? = null
    private var incomingCallListener: ChildEventListener? = null

    fun addUser(user: User) {
        usersOnlineChildRef.child(user.id).setValue(user)
    }

    fun removeUser(uid: String) {
        shouldWaitForCall = false
        usersOnlineChildRef.child(uid).removeValue()
    }

    fun searchForOpponent(presenter: SearchUserPresenter, user: User) {
        usersOnlineChildRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                val opponentUid = dataSnapshot.key
                if (user.id != opponentUid) {
                    val opponent: User = dataSnapshot.getValue(User::class.java) ?: User("", "")
                    listenForIncomingCall(presenter, user.id, opponentUid ?: "")
                    presenter.addOpponentInList(opponent)

                }
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
//                removeGameRoom(user.id, p0.key ?: "")
                presenter.removeOpponent(p0.key ?: "")
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

    fun removeGameRoom(currentUid: String, opponentUid: String) {
        opponentGameRoomListener?.let {
            gamesChildRef.removeEventListener(it)
        }
        incomingCallListener?.let {
            gamesChildRef.removeEventListener(it)
        }
        val gameRoomChild = currentUid + "_" + opponentUid
        gamesChildRef.child(gameRoomChild).removeValue()
    }

    fun createCurrentGameRoom(presenter: SearchUserPresenter, currentUid: String, opponentUid: String) {
        val gameRoomChild = currentUid + "_" + opponentUid
        gamesChildRef.child(gameRoomChild).push().setValue(true)
//        listenForOpponentGameRoom(presenter, currentUid, opponentUid)
    }

    fun listenForOpponentGameRoom(presenter: SearchUserPresenter, currentUid: String, opponentUid: String) {
        val gameRoomChild = opponentUid + "_" + currentUid
        shouldWaitForCall = false
        opponentGameRoomListener = gamesChildRef.child(gameRoomChild)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                    if (dataSnapshot.value == true) {
                        removeUser(currentUid)
                        presenter.startGameAsInitiator()
                    }
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                    removeGameRoom(currentUid, opponentUid)
                    presenter.removeOpponent(opponentUid)
                }

                override fun onCancelled(p0: DatabaseError) {
                }
            })
    }

    fun listenForIncomingCall(presenter: SearchUserPresenter, currentUid: String, opponentUid: String) {
        val gameRoomChild = opponentUid + "_" + currentUid
        incomingCallListener = gamesChildRef.child(gameRoomChild)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                    if (dataSnapshot.value != null && shouldWaitForCall) {
                        presenter.showCall(opponentUid)
                    }
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                    removeGameRoom(currentUid, opponentUid)
                    presenter.removeOpponent(opponentUid)
                }

                override fun onCancelled(p0: DatabaseError) {
                }
            })
    }
}