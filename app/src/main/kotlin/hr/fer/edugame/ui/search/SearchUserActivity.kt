package hr.fer.edugame.ui.search

import android.os.Bundle
import hr.fer.edugame.R
import hr.fer.edugame.ui.shared.base.BaseActivity
import javax.inject.Inject

class SearchUserActivity : BaseActivity(), SearchUserView {
    override val layoutRes: Int = R.layout.activity_search_user
    override fun providePresenter(): SearchUserPresenter = presenter

    @Inject
    lateinit var presenter: SearchUserPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}