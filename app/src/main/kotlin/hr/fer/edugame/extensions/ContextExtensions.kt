package hr.fer.edugame.extensions

import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseUser

inline fun <reified T: Any> Context.intentFor(params: Pair<String, FirebaseUser>): Intent {
    return Intent(this, T::class.java).apply {
        putExtra(params.first, params.second)
    }
}

inline fun <reified T: Any> Context.intentFor(): Intent {
    return Intent(this, T::class.java)
}