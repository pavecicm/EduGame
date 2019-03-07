package hr.fer.edugame.ui.shared.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import hr.fer.edugame.R
import hr.fer.edugame.extensions.inflate
import hr.fer.edugame.extensions.setThrottlingClickListener
import kotlinx.android.synthetic.main.list_item_word.view.newWordItem

class OpponentsAdapter(
    var id: MutableList<String> = mutableListOf(),
    var onClickListener: (String) -> Unit
) : RecyclerView.Adapter<OpponentsAdapter.OpponentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        OpponentsViewHolder(parent.inflate(R.layout.list_item_word))


    override fun onBindViewHolder(holder: OpponentsViewHolder, position: Int) {
        val word = id[position]
        holder.bind(word)
    }

    override fun getItemCount() = id.size

    fun initNewLetters(words: List<String>) {
        this.id.clear()
        this.id.addAll(words)
        notifyDataSetChanged()
    }

    fun updateItems(words: List<String>) {
        this.id.addAll(words)
        notifyDataSetChanged()
    }

    fun updateItem(word: String) {
        this.id.add(word)
        notifyDataSetChanged()
    }

    fun destroyItem(word: String) {
        id.remove(word)
        notifyDataSetChanged()
    }

    fun resetAdapter() {
        id.clear()
        notifyDataSetChanged()
    }

    fun removeItem(id: String) {
        this.id.remove(id)
        notifyDataSetChanged()
    }

    inner class OpponentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(id: String) {
            with(itemView) {
                newWordItem.text = id
                newWordItem.setThrottlingClickListener {
                    onClickListener(id)
                }
            }
        }
    }
}