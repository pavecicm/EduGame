package hr.fer.edugame.data.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable


class User() : Serializable {

    var id: String = ""

    var email: String = ""

    constructor(id: String, email: String): this() {
        this.id = id
        this.email = email
    }

    constructor(source: Parcel): this() {
        source?.let {
            id = it.readString() ?: ""
            email = it.readString() ?: ""
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
//        dest.writeString(id)
//        dest.writeString(email)
//    }
}