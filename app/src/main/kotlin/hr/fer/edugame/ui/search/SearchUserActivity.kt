package hr.fer.edugame.ui.search

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import hr.fer.edugame.R
import hr.fer.edugame.data.models.User
import hr.fer.edugame.extensions.hide
import hr.fer.edugame.extensions.intentFor
import hr.fer.edugame.extensions.setThrottlingClickListener
import hr.fer.edugame.extensions.show
import hr.fer.edugame.ui.shared.adapters.OpponentsAdapter
import hr.fer.edugame.ui.shared.base.BaseActivity
import kotlinx.android.synthetic.main.activity_search_user.backBtn
import kotlinx.android.synthetic.main.activity_search_user.progressLayout
import kotlinx.android.synthetic.main.activity_search_user.usersRecycle
import javax.inject.Inject

class SearchUserActivity : BaseActivity(), SearchUserView {

    companion object {
        fun newInstance(context: Context) = context.intentFor<SearchUserActivity>()
    }

    override val layoutRes: Int = R.layout.activity_search_user
    override fun providePresenter(): SearchUserPresenter = presenter

    private var dialog: Dialog? = null


    @Inject
    lateinit var presenter: SearchUserPresenter
    private lateinit var adapter: OpponentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.init()
        initUI()
    }

    fun initUI() {
        backBtn.setThrottlingClickListener {
            onBackPressed()
        }
        adapter = OpponentsAdapter {
            presenter.callPlayer(it)
        }
        usersRecycle.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun navigateToGameActivity(opponent: User) {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun addOpponentFoundView(uid: String, email: String) {
        adapter.updateItem(uid)
    }

    override fun removeOpponnent(id: String) {
        adapter.removeItem(id)
    }

    override fun showCallRefused() {
        Toast.makeText(this, getString(R.string.call_refused), Toast.LENGTH_LONG)
    }

    override fun showGameRequestDialog(id: String) {
        try {
            dialog = AlertDialog.Builder(this)
                .setMessage(String.format(getString(R.string.invite_message), id))
                .setPositiveButton(R.string.ok) { _, _ -> presenter.acceptCall(id) }
                .setNegativeButton(String.format(getString(R.string.cancel), id)) { _, _ -> presenter.declineCall(id) }
                .create()
            dialog?.let {
                it.show()
            }
        } catch (e: Exception) {
        }
    }

    override fun showProgress() {
        progressLayout.show()
    }

    override fun hideProgress() {
        progressLayout.hide()
    }

    override fun finish() {
        dialog?.let {
            it.dismiss()
            it.cancel()
        }
        super.finish()
    }
}