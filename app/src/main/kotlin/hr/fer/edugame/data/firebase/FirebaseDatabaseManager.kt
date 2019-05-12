package hr.fer.edugame.data.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import hr.fer.edugame.constants.KEY_USER
import hr.fer.edugame.data.models.User
import javax.inject.Inject



class FirebaseDatabaseManager @Inject constructor(
    private val database: FirebaseDatabase
) : FirebaseDatabaseInterface {

    override fun createUser(id: String, email: String) {
        val user = User(id, email)
        database
            .reference        // 1
            .child(KEY_USER)  // 2
            .child(id)        // 3
            .setValue(user)   // 4
    }

    override fun getUser(id: String) =
        database
            .reference
            .child(KEY_USER)
            .child(id)


    override fun referenceChild(key: String): DatabaseReference = database.reference.child(key)

    fun databaseReference() = database.reference
}