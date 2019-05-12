package hr.fer.edugame.ui.shared.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import hr.fer.edugame.R
import hr.fer.edugame.data.models.User
import hr.fer.edugame.extensions.inflate
import hr.fer.edugame.extensions.setThrottlingClickListener
import kotlinx.android.synthetic.main.list_item_word.view.newWordItem

class OpponentsAdapter(
    var opponents: MutableList<User> = mutableListOf(),
    var onClickListener: (String) -> Unit
) : RecyclerView.Adapter<OpponentsAdapter.OpponentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        OpponentsViewHolder(parent.inflate(R.layout.list_item_word))


    override fun onBindViewHolder(holder: OpponentsViewHolder, position: Int) {
        val user = opponents[position]
        holder.bind(user)
    }

    override fun getItemCount() = opponents.size

    fun initNewLetters(users: List<User>) {
        this.opponents.clear()
        this.opponents.addAll(users)
        notifyDataSetChanged()
    }

    fun updateItems(users: List<User>) {
        this.opponents.addAll(users)
        notifyDataSetChanged()
    }

    fun updateItem(user: User) {
        this.opponents.add(user)
        notifyDataSetChanged()
    }

    fun destroyItem(user: User) {
        opponents.remove(user)
        notifyDataSetChanged()
    }

    fun resetAdapter() {
        opponents.clear()
        notifyDataSetChanged()
    }

    fun removeItem(id: String) {
        this.opponents.remove(getOpponent(id))
        notifyDataSetChanged()
    }

    fun getOpponent(id: String): User? {
        return opponents.toList().firstOrNull {
            it.id == id
        }
    }

    inner class OpponentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                newWordItem.text = user.username
                newWordItem.setThrottlingClickListener {
                    onClickListener(user.id)
                }
            }
        }
    }
}