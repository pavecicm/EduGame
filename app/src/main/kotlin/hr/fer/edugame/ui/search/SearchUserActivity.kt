package hr.fer.edugame.ui.search

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import hr.fer.edugame.R
import hr.fer.edugame.data.models.User
import hr.fer.edugame.extensions.intentFor
import hr.fer.edugame.ui.shared.adapters.OpponentsAdapter
import hr.fer.edugame.ui.shared.base.BaseActivity
import kotlinx.android.synthetic.main.activity_search_user.usersRecycle
import javax.inject.Inject

class SearchUserActivity : BaseActivity(), SearchUserView {

    companion object {
        fun newInstance(context: Context) = context.intentFor<SearchUserActivity>()
    }

    override val layoutRes: Int = R.layout.activity_search_user
    override fun providePresenter(): SearchUserPresenter = presenter


    @Inject
    lateinit var presenter: SearchUserPresenter
    private lateinit var adapter: OpponentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.init()
        initUI()
    }

    fun initUI() {
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
        val request: RequestQueue = Volley.newRequestQueue(this)
        finish()
    }

    override fun addOpponentFoundView(uid: String, email: String) {
        adapter.updateItem(uid)
    }

    override fun removeOpponnent(id: String) {
        adapter.removeItem(id)
    }

    override fun showGameRequestDialog(id: String) {
        AlertDialog.Builder(this)
            .setMessage(R.string.invite_message)
            .setPositiveButton(R.string.ok, { _, _ -> presenter.acceptCall(id)})
            .setNegativeButton(String.format(getString(R.string.cancel), id), null)
            .show()
    }
}