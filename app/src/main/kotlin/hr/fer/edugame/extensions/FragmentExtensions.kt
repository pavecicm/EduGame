package hr.fer.edugame.extensions

import android.os.Parcelable
import android.support.v4.app.Fragment
import java.io.Serializable

@Suppress("UNCHECKED_CAST")
fun <T : Serializable> Fragment.getSerializableOrThrow(key: String): T {
    if (arguments == null) {
        throw IllegalStateException("Arguments are null when fetching value for key $key")
    }
    return (arguments?.getSerializable(key) as T?)
        ?: throw IllegalArgumentException("Key: $key missing. You have to use newIntent method to pass parameters to this fragment.")
}

@Suppress("UNCHECKED_CAST")
fun <T : Serializable> Fragment.getSerializableArrayOrThrow(key: String): List<T> {
    if (arguments == null) {
        throw IllegalStateException("Arguments are null when fetching value for key $key")
    }
    return (arguments?.getSerializable(key) as List<T>?)
        ?: throw IllegalArgumentException("Key: $key missing. You have to use newIntent method to pass parameters to this fragment.")
}

fun <T : Parcelable> Fragment.getParcelableOrThrow(key: String): T {
    if (arguments == null) {
        throw IllegalStateException("Arguments are null when fetching value for key $key")
    }
    return arguments?.getParcelable(key)
        ?: throw IllegalArgumentException("Key: $key missing. You have to use newIntent method to pass parameters to this fragment.")
}