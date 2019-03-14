package hr.fer.edugame.ui.login

import io.reactivex.Observable
import com.google.firebase.auth.FirebaseAuth
import hr.fer.edugame.data.firebase.FirebaseDatabaseManager
import hr.fer.edugame.data.rx.RxSchedulers
import hr.fer.edugame.data.rx.applySchedulers
import hr.fer.edugame.data.rx.subscribe
import hr.fer.edugame.data.storage.prefs.PreferenceStore
import hr.fer.edugame.ui.shared.base.BasePresenter
import java.util.concurrent.TimeUnit
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
            preferenceStore.hasInternet = true
            view.navigateToHome(it)
        }
    }

    fun loginToFirebase(activity: LoginActivity, email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            view.showProgress()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    view.hideProgress()
                    if (task.isSuccessful) {
                        var currentUser = auth.currentUser
                        preferenceStore.email = currentUser?.email ?: ""
                        preferenceStore.currentUserID = currentUser!!.uid
                        currentUser?.let {
                            preferenceStore.hasInternet = true
                            view.navigateToHome(it)
                        }
                    } else {
                        createUser(activity, email, password)
                    }
                }
        }
    }

    fun createUser(activity: LoginActivity, email: String, password: String) {
        view.showProgress()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                view.hideProgress()
                if (task.isSuccessful) {
                    var currentUser = auth.currentUser
                    if (currentUser != null) {
                        preferenceStore.hasInternet = true
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
        view.showProgress()
        auth
            .signInAnonymously()
            .addOnCompleteListener { task ->
                view.hideProgress()
                if (task.isSuccessful) {
                    preferenceStore.hasInternet = true
                    var currentUser = auth.currentUser
                    preferenceStore.currentUserID = currentUser!!.uid
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