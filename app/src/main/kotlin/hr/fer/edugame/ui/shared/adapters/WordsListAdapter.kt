package hr.fer.edugame.ui.shared.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import hr.fer.edugame.R
import hr.fer.edugame.extensions.inflate
import hr.fer.edugame.extensions.setThrottlingClickListener
import kotlinx.android.synthetic.main.list_item_word.view.newWordItem


class WordsListAdapter(
    var words: MutableList<String> = mutableListOf()
) : RecyclerView.Adapter<WordsListAdapter.WordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WordViewHolder(parent.inflate(R.layout.list_item_word))


    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = words[position]
        holder.bind(word)
    }

    override fun getItemCount() = words.size

    fun initNewLetters(words: List<String>) {
        this.words.clear()
        this.words.addAll(words)
        notifyDataSetChanged()
    }

    fun updateItems(words: List<String>) {
        this.words.addAll(words)
        notifyDataSetChanged()
    }

    fun updateItem(word: String) {
        this.words.add(word)
        notifyDataSetChanged()
    }

    fun destroyItem(word: String) {
        words.remove(word)
        notifyDataSetChanged()
    }

    fun resetAdapter() {
        words.clear()
        notifyDataSetChanged()
    }

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(word: String) {
            with(itemView) {
                newWordItem.text = word
                setThrottlingClickListener {
                    destroyItem(word)
                }
            }
        }
    }
}