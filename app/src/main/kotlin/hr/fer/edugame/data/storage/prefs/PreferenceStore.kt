package hr.fer.edugame.data.storage.prefs

interface PreferenceStore {

    var currentUserID: String
    var email: String

    fun contains(key: String): Boolean

    object Constants {
        const val KEY_USER = "KEY_USER"
        const val KEY_EMAIL = "KEY_EMAIL"
    }
}