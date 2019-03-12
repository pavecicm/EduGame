package hr.fer.edugame.ui.login

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import hr.fer.edugame.data.firebase.FirebaseDatabaseManager
import hr.fer.edugame.data.storage.prefs.PreferenceStore
import hr.fer.edugame.ui.shared.base.BasePresenter
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    override val view: LoginView,
    private val auth: FirebaseAuth,
    private val firebaseDatabaseManager: FirebaseDatabaseManager,
    private val preferenceStore: PreferenceStore
) : BasePresenter(view) {

    fun onStart() {
        preferenceStore.gamePoints = 0
        auth.currentUser?.let {
            view.navigateToHome(it)
        }
    }

    fun loginToFirebase(activity: LoginActivity, email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var currentUser = auth.currentUser
                        preferenceStore.email = currentUser?.email ?: ""
                        preferenceStore.currentUserID = currentUser!!.uid
                        currentUser?.let {
                            view.navigateToHome(it)
                        }
                    } else {
                        createUser(activity, email, password)
                    }
                }
        }
    }

    fun createUser(activity: LoginActivity, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    var currentUser = auth.currentUser
                    if (currentUser != null) {
                        preferenceStore.email = currentUser.email ?: ""
                        preferenceStore.currentUserID = currentUser.uid
                        firebaseDatabaseManager.createUser(currentUser.uid, currentUser.email ?: "")
                    }
                    currentUser?.let {
                        view.navigateToHome(it)
                    }
                } else {
                    view.showAuthFailed()
                }
            }
    }

    fun continueAsAnonymous() {
        auth
            .signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var currentUser = auth.currentUser
                    preferenceStore.currentUserID = currentUser!!.uid
//                    currentUser?.updateProfile(
//                        UserProfileChangeRequest
//                            .Builder()
//                            .setDisplayName("Test")
//                            .build()
//                    )
//                    if (currentUser != null) {
//                        myRef.child("Users").child(currentUser.email.toString().splitEmail()).child("Request").setValue(currentUser.uid)
//                    }
                    currentUser?.let {
                        view.navigateToHome(it)
                    }
                } else {
                    preferenceStore.hasInternet = false
                    view.navigateToHomeNoInternet()
                }
            }
    }
}