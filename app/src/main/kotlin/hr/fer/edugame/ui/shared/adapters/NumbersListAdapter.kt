package hr.fer.edugame.ui.shared.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import hr.fer.edugame.R
import hr.fer.edugame.extensions.inflate
import hr.fer.edugame.extensions.setThrottlingClickListener
import kotlinx.android.synthetic.main.list_item_letter.view.letterButton

class NumbersListAdapter(
    var numbers: MutableList<Int>,
    protected val onClickListener: (String) -> Unit
) : RecyclerView.Adapter<NumbersListAdapter.NumberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NumberViewHolder(parent.inflate(R.layout.list_item_letter))


    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        val number = numbers[position]
        holder.bind(number)
    }

    override fun getItemCount() = numbers.size

    fun initNewItems(numbers: List<Int>) {
        this.numbers.clear()
        this.numbers.addAll(numbers)
        notifyDataSetChanged()
    }

    fun updateItems(numbers: List<Int>) {
        this.numbers.addAll(numbers)
        notifyDataSetChanged()
    }

    fun updateItem(number: Int) {
        this.numbers.add(number)
        notifyDataSetChanged()
    }

    fun destroyItem(number: Int) {
        numbers.remove(number)
        notifyDataSetChanged()
    }

    inner class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(number: Int) {
            with(itemView) {
                letterButton.text = number.toString()
                setThrottlingClickListener {
                    onClickListener(number.toString())
                }
            }
        }
    }
}