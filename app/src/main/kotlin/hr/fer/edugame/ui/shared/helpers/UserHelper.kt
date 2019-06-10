package hr.fer.edugame.ui.shared.helpers

import hr.fer.edugame.data.models.User
import hr.fer.edugame.data.storage.prefs.PreferenceStore

fun PreferenceStore.getUser() = User(
    id = currentUserID,
    username = username,
    singlePlayerPoints = singlePlayerPoints,
    multiplayerPoints = multiplayerPoints
)