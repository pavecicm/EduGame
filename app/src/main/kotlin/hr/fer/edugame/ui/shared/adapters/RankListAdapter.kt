package hr.fer.edugame.ui.shared.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import hr.fer.edugame.R
import hr.fer.edugame.data.models.User
import hr.fer.edugame.extensions.inflate
import kotlinx.android.synthetic.main.list_item_rank.view.rank
import kotlinx.android.synthetic.main.list_item_rank.view.username

class RankListAdapter(
    var users: MutableList<User> = mutableListOf()
) : RecyclerView.Adapter<RankListAdapter.UsersRankViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UsersRankViewHolder(parent.inflate(R.layout.list_item_rank))


    override fun onBindViewHolder(holder: UsersRankViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user, position)
    }

    override fun getItemCount() = users.size

    fun updateItemsMultiplayer(users: List<User>) {
        this.users.clear()
        users.sortedBy {
            it.multiplayerPoints
        }
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun updateItemsSinglePlayer(users: List<User>) {
        this.users.clear()
        users.sortedBy {
            it.singlePlayerPoints
        }
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun destroyItem(user: User) {
        users.remove(user)
        notifyDataSetChanged()
    }

    fun resetAdapter() {
        users.clear()
        notifyDataSetChanged()
    }

    inner class UsersRankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User, position: Int) {
            with(itemView) {
                username.text = user.username
                rank.text = (position + 1).toString()
            }
        }
    }
}