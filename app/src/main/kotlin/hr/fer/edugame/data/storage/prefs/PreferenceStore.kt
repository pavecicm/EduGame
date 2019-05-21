package hr.fer.edugame.data.storage.prefs

interface PreferenceStore {

    var currentUserID: String
    var opponentId: String
    var isInitiator: Boolean
    var username: String
    var gamePoints: Int
    var isSinglePlayerEnabled: Boolean
    var singlePlayerPoints: Int
    var multiplayerPoints: Int
    var hasInternet: Boolean
    var isUserLogedIn: Boolean
    var customToken: String

    fun contains(key: String): Boolean

    object Constants {
        const val KEY_USER = "KEY_USER"
        const val KEY_OPPONENT = "KEY_OPPONENT"
        const val KEY_INITIATOR = "KEY_INITIATOR"
        const val KEY_USERNAME = "KEY_USERNAME"
        const val KEY_GAME_POINTS = "KEY_GAME_POINTS"
        const val KEY_SINGLE_PLAYER = "KEY_SINGLE_PLAYER"
        const val KEY_SINGLE_PLAYER_POINTS = "KEY_SINGLE_PLAYER_POINTS"
        const val KEY_MUPLTIPLAYER_POINTS = "KEY_MUPLTIPLAYER_POINTS"
        const val KEY_INTERNET = "KEY_INTERNET"
        const val KEY_USER_LOGGED_IN = "KEY_USER_LOGGED_IN"
        const val KEY_CUSTOM_TOKEN = "KEY_CUSTOM_TOKEN"
    }
}