package hr.fer.edugame.ui.login

import com.google.firebase.auth.FirebaseAuth
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
            preferenceStore.hasInternet = true
            view.navigateToHome(it)
        }
    }

    fun continueAsAnonymous(username: String) {
        view.showProgress()
        auth
            .signInAnonymously()
            .addOnCompleteListener { task ->
                view.hideProgress()
                if (task.isSuccessful) {
                    preferenceStore.hasInternet = true
                    var currentUser = auth.currentUser
                    preferenceStore.currentUserID = currentUser!!.uid
                    preferenceStore.username = username
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