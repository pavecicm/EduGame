package hr.fer.edugame.ui.shared.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import hr.fer.edugame.R
import hr.fer.edugame.extensions.inflate
import hr.fer.edugame.extensions.setThrottlingClickListener
import hr.fer.edugame.ui.letters.Letter
import kotlinx.android.synthetic.main.list_item_letter.view.letterButton


class LettersListAdapter(
    var letters: MutableList<Letter> = mutableListOf(),
    protected val onClickListener: (Letter) -> Unit
) : RecyclerView.Adapter<LettersListAdapter.LetterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LetterViewHolder(parent.inflate(R.layout.list_item_letter))


    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        val letter = letters[position]
        holder.bind(letter)
    }

    override fun getItemCount() = letters.size

    fun initNewLetters(letters: List<Letter>) {
        this.letters = letters.sortedBy { it.index }.toMutableList()
        notifyDataSetChanged()
    }

    fun updateItems(letters: List<Letter>) {
        this.letters.clear()
        this.letters.addAll(letters)
        notifyDataSetChanged()
    }

    fun updateItem(letter: Letter) {
        this.letters[letter.index] = letter
        notifyItemChanged(letter.index)
    }

    fun setItemClickable(letter: Letter) {
        this.letters[letter.index] = letter.copy(isClickable = true)
        updateItem(this.letters[letter.index])
    }

    fun destroyItem(letter: Letter) {
        letters.remove(letter)
        notifyDataSetChanged()
    }

    inner class LetterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(letter: Letter) {
            with(itemView) {
                letterButton.text = letter.letter
                setThrottlingClickListener {
                    if (letter.isClickable) {
                        onClickListener(letter)
                    }
                }
                alpha = if (letter.isClickable.not()) 0.3f else 1.0f
            }
        }
    }
}