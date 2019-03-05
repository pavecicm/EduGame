package hr.fer.edugame.data.storage.prefs

interface PreferenceStore {

    fun contains(key: String): Boolean

    object Constants {
    }
}