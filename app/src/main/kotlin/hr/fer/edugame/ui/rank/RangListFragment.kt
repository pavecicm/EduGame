package hr.fer.edugame.ui.rank

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import hr.fer.edugame.R
import hr.fer.edugame.data.models.User
import hr.fer.edugame.extensions.getSerializableArrayOrThrow
import hr.fer.edugame.extensions.getSerializableOrThrow
import hr.fer.edugame.ui.shared.adapters.RankListAdapter
import hr.fer.edugame.ui.shared.base.BaseFragment
import hr.fer.edugame.ui.shared.base.BasePresenter
import kotlinx.android.synthetic.main.fragment_rang_list.rangList

class RangListFragment : BaseFragment() {

    companion object {
        const val EXTRA_USERS = "EXTRA_USERS"
        const val EXTRA_TYPE = "EXTRA_TYPE"
        fun newInstance(users: List<User>, type: PointsType) = RangListFragment().apply {
            arguments = bundleOf(
                EXTRA_USERS to users,
                EXTRA_TYPE to type
            )
        }
    }

    override val layoutRes: Int = R.layout.fragment_rang_list
    override fun providePresenter(): BasePresenter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RankListAdapter(type = getSerializableOrThrow(EXTRA_TYPE))
        adapter.updateItems(getSerializableArrayOrThrow<User>(EXTRA_USERS).toMutableList())
        rangList.adapter = adapter
    }
}