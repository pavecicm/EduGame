package hr.fer.edugame.data.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable


class User(
    var id: String = "",
    var email: String? = "",
    var username: String? = "",
    var singlePlayerPoints: Int? = 0,
    var multiplayerPoints: Int? = 0
) : Serializable {


//    constructor(id: String = "", email: String? = "", username: String? = "", singlePlayerPoints: Int? = 0, multiplayerPoints: Int? = 0): this() {
//        this.id = id
//        this.email = email ?: ""
//        this.username = username ?: ""
//        this.singlePlayerPoints = singlePlayerPoints ?: 0
//        this.multiplayerPoints = multiplayerPoints ?: 0
//    }

    constructor(source: Parcel): this() {
        with(source) {
            id = readString() ?: ""
            email = readString() ?: ""
            username = readString() ?: ""
        }
    }

//    val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
//        override fun createFromParcel(source: Parcel): User = User(source)
//
//        override fun newArray(size: Int): Array<User> = emptyArray()
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    override fun writeToParcel(dest: Parcel, flags: Int) {
//        dest.writeString(users)
//        dest.writeString(email)
//    }
}