package hr.fer.edugame.data.firebase.interactors

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import hr.fer.edugame.constants.FIREBASE_RANK_LIST
import hr.fer.edugame.data.firebase.FirebaseDatabaseManager
import hr.fer.edugame.data.models.User
import hr.fer.edugame.data.storage.prefs.PreferenceStore
import hr.fer.edugame.ui.rank.RankListPresenter
import javax.inject.Inject

class RankInteractor @Inject constructor(
    private val firebaseManager: FirebaseDatabaseManager,
    private val preferenceStore: PreferenceStore
) {
    protected val rankList: DatabaseReference =
        firebaseManager.databaseReference()
            .child(FIREBASE_RANK_LIST)

    fun savePoints(user: User) {
        rankList.child(FIREBASE_RANK_LIST).setValue(user)
    }

    fun getRankList(presenter: RankListPresenter) {
        rankList.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                presenter.displayRankList(data.value as List<User>)
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }
}
