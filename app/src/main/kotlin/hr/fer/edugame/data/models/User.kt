package hr.fer.edugame.data.models

import android.os.Parcel
import java.io.Serializable


class User(
    var id: String = "",
    var username: String? = "",
    var singlePlayerPoints: Int? = 0,
    var multiplayerPoints: Int? = 0
) : Serializable {

    constructor(source: Parcel) : this() {
        with(source) {
            id = readString() ?: ""
            username = readString() ?: ""
        }
    }
}