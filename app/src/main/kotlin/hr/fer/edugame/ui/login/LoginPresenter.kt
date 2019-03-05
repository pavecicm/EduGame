package hr.fer.edugame.ui.login

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import hr.fer.edugame.extensions.splitEmail
import hr.fer.edugame.ui.shared.base.BasePresenter
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    override val view: LoginView
) : BasePresenter(view) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    fun onStart() {
        auth.currentUser?.let {
            view.navigateToHome(it)
        }
    }

    fun loginToFirebase(activity: LoginActivity, email: String, password: String) {
        if(email.isNotEmpty() && password.isNotEmpty())
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    var currentUser = auth.currentUser
                    if (currentUser != null) {
                        myRef.child("Users").child(currentUser.email.toString().splitEmail()).child("Request").setValue(currentUser.uid)
                    }
                    currentUser?.let {
                        view.navigateToHome(it)
                    }
                } else {
                    view.showAuthFailed()
                }
            }
    }
}