package hr.fer.edugame.ui.shared.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import hr.fer.edugame.R
import hr.fer.edugame.extensions.inflate
import hr.fer.edugame.extensions.setThrottlingClickListener
import kotlinx.android.synthetic.main.list_item_letter.view.letterButton


class LettersListAdapter(
    var letters: MutableList<String>,
    protected val onClickListener: View.OnClickListener
) : RecyclerView.Adapter<LettersListAdapter.LetterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LetterViewHolder(parent.inflate(R.layout.list_item_letter))


    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        val letter = letters[position]
        holder.bind(letter)
    }

    override fun getItemCount() = letters.size

    fun initNewLetters(letters: List<String>) {
        this.letters.clear()
        this.letters.addAll(letters)
        notifyDataSetChanged()
    }

    fun updateItems(letters: List<String>) {
        this.letters.addAll(letters)
        notifyDataSetChanged()
    }

    fun updateItem(letter: String) {
        this.letters.add(letter)
        notifyDataSetChanged()
    }

    fun destroyItem(letter: String) {
        letters.remove(letter)
        notifyDataSetChanged()
    }

    inner class LetterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(letter: String) {
            with(itemView) {
                letterButton.text = letter.toString()
                setThrottlingClickListener {
                    onClickListener.onClick(itemView)
                }
            }
        }
    }
}