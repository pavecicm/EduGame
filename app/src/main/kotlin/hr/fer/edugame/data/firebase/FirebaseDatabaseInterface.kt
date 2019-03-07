package hr.fer.edugame.data.firebase

import com.google.firebase.database.DatabaseReference

interface FirebaseDatabaseInterface {
    fun createUser(id: String, email: String)
    fun getUser(id: String): DatabaseReference
    fun referenceChild(key: String): DatabaseReference
}