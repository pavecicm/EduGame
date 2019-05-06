package hr.fer.edugame.data.storage.prefs

import android.content.Context
import android.content.SharedPreferences

private const val SHARED_PREFERENCES_NAME = "RBA_NATIVE_SHARED_PREFS"

class SharedPreferenceStore(context: Context) : PreferenceStore {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    override var currentUserID: String
        get() = sharedPreferences[PreferenceStore.Constants.KEY_USER] ?: ""
        set(value) {
            sharedPreferences[PreferenceStore.Constants.KEY_USER] = value
        }

    override var opponentId: String
        get() = sharedPreferences[PreferenceStore.Constants.KEY_OPPONENT] ?: ""
        set(value) {
            sharedPreferences[PreferenceStore.Constants.KEY_OPPONENT] = value
        }

    override var isInitiator: Boolean
        get() = sharedPreferences[PreferenceStore.Constants.KEY_INITIATOR] ?: false
        set(value) {
            sharedPreferences[PreferenceStore.Constants.KEY_INITIATOR] = value
        }

    override var email: String
        get() = sharedPreferences[PreferenceStore.Constants.KEY_EMAIL] ?: ""
        set(value) {
            sharedPreferences[PreferenceStore.Constants.KEY_EMAIL] = value
        }

    override var username: String
        get() = sharedPreferences[PreferenceStore.Constants.KEY_USERNAME] ?: ""
        set(value) {
            sharedPreferences[PreferenceStore.Constants.KEY_USERNAME] = value
        }

    override var gamePoints: Int
        get() = sharedPreferences[PreferenceStore.Constants.KEY_GAME_POINTS] ?: 0
        set(value) {
            sharedPreferences[PreferenceStore.Constants.KEY_GAME_POINTS] = value
        }

    override var isSinglePlayerEnabled: Boolean
        get() = sharedPreferences[PreferenceStore.Constants.KEY_SINGLE_PLAYER] ?: false
        set(value) {
            sharedPreferences[PreferenceStore.Constants.KEY_SINGLE_PLAYER] = value
        }

    override var singlePlayerPoints: Int
        get() = sharedPreferences[PreferenceStore.Constants.KEY_SINGLE_PLAYER_POINTS] ?: 0
        set(value) {
            sharedPreferences[PreferenceStore.Constants.KEY_SINGLE_PLAYER_POINTS] = value
        }

    override var hasInternet: Boolean
        get() = sharedPreferences[PreferenceStore.Constants.KEY_INTERNET] ?: true
        set(value) {
            sharedPreferences[PreferenceStore.Constants.KEY_INTERNET] = value
        }

    override fun contains(key: String): Boolean = sharedPreferences.contains(key)

    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    @Suppress("UnsafeCast")
    inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: 0) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }
}