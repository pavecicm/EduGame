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
            override fun onChildAdded(data: DataSnapshot, p1: String?) {
                val opponentUid = data.key
                if (user.id != opponentUid) {
                    val opponent: User = data.getValue(User::class.java) ?: User("", "")
                    listenForIncomingCall(presenter, user.id, opponentUid ?: "")
                    presenter.addOpponentInList(opponent)
                }
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
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

    fun removeListeners() {
        opponentGameRoomListener?.let {
            gamesChildRef.removeEventListener(it)
        }
        incomingCallListener?.let {
            gamesChildRef.removeEventListener(it)
        }
    }

    fun removeOpponentGameRoom(currentUid: String, opponentUid: String) {
        val gameRoomChild = opponentUid + "_" + currentUid
        gamesChildRef.child(gameRoomChild).removeValue()
    }

    fun createCurrentGameRoom(currentUid: String, opponentUid: String) {
        val gameRoomChild = currentUid + "_" + opponentUid
        gamesChildRef.child(gameRoomChild).setValue(true)
    }

    fun listenForOpponentGameRoom(presenter: SearchUserPresenter, currentUid: String, opponentUid: String) {
        val gameRoomChild = opponentUid + "_" + currentUid
        shouldWaitForCall = false
        gamesChildRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(data: DataSnapshot, s: String?) {
                if (data.key == gameRoomChild) {
                    presenter.startGameAsInitiator()
                }
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(data: DataSnapshot) {
                if (data.key == gameRoomChild) {
                    shouldWaitForCall = true
                    opponentGameRoomListener?.let {
                        gamesChildRef.removeEventListener(it)
                    }
                    presenter.handleCallRefused()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
//        opponentGameRoomListener = gamesChildRef.child(gameRoomChild)
//            .addChildEventListener(object : ChildEventListener {
//                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
//                    if (dataSnapshot.value == true) {
////                        removeUser(currentUid)
//                        presenter.startGameAsInitiator()
//                    }
//                }
//
//                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//                }
//
//                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//                }
//
//                override fun onChildRemoved(p0: DataSnapshot) {
////                    shouldWaitForCall = true
////                    opponentGameRoomListener?.let {
////                        gamesChildRef.removeEventListener(it)
////                    }
////                    presenter.handleCallRefused()
//                }
//
//                override fun onCancelled(p0: DatabaseError) {
//                }
//            })
    }

    fun listenForIncomingCall(presenter: SearchUserPresenter, currentUid: String, opponentUid: String) {
        val gameRoomChild = opponentUid + "_" + currentUid
        gamesChildRef
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                    if (dataSnapshot.key == gameRoomChild && dataSnapshot.value != null && shouldWaitForCall) {
                        presenter.showCall(opponentUid)
                    }
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                }

                override fun onChildRemoved(data: DataSnapshot) {
                    if (data.key == gameRoomChild) {
                        removeGameRoom(currentUid, opponentUid)
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                }
            })

//            .child(gameRoomChild)
//            .addChildEventListener(object : ChildEventListener {
//                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
//                    if (dataSnapshot.value != null && shouldWaitForCall) {
//                        presenter.showCall(opponentUid)
//                    }
//                }
//
//                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//
//                }
//
//                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//                }
//
//                override fun onChildRemoved(p0: DataSnapshot) {
//                    removeGameRoom(currentUid, opponentUid)
//                }
//
//                override fun onCancelled(p0: DatabaseError) {
//                }
//            })
    }
}