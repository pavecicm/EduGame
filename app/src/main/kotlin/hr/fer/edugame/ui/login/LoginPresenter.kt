package hr.fer.edugame.ui.login

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import hr.fer.edugame.data.storage.prefs.PreferenceStore
import hr.fer.edugame.ui.shared.base.BasePresenter
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    override val view: LoginView,
    private val auth: FirebaseAuth,
    private val preferenceStore: PreferenceStore
) : BasePresenter(view) {

    fun onStart() {
        preferenceStore.gamePoints = 0
        auth.currentUser?.let {
            preferenceStore.hasInternet = true
            view.navigateToHome()
        }
        if(preferenceStore.isUserLogedIn) {
            view.navigateToHome()
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
                    preferenceStore.isUserLogedIn = true
                    preferenceStore.singlePlayerPoints = 0
                    preferenceStore.multiplayerPoints = 0
                    var currentUser = auth.currentUser
                    preferenceStore.currentUserID = currentUser!!.uid
                    preferenceStore.username = username
                    view.navigateToHome()
                } else {
                    preferenceStore.hasInternet = false
                    view.navigateToHomeNoInternet()
                }
            }
    }

    fun signIn(username: String) {
        if (username == preferenceStore.username) {
            auth.signInAnonymously()
                .addOnCompleteListener {task ->
                    if (task.isSuccessful) {
                        preferenceStore.hasInternet = true
                        preferenceStore.isUserLogedIn = true
                        preferenceStore.username = username
                        view.navigateToHome()

                    } else {
                        preferenceStore.hasInternet = false
                        view.navigateToHomeNoInternet()
                    }
                }
        } else {
            continueAsAnonymous(username)
        }
    }
}