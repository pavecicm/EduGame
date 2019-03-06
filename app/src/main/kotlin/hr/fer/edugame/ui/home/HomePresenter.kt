package hr.fer.edugame.ui.home

import com.google.firebase.auth.FirebaseAuth
import hr.fer.edugame.data.firebase.FirebaseDatabaseManager
import hr.fer.edugame.ui.shared.base.BasePresenter
import javax.inject.Inject

class HomePresenter @Inject constructor(
    override val view: HomeView,
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabaseManager
) : BasePresenter(view) {

    fun logout() {
        auth.signOut()
        view.logout()
    }

}