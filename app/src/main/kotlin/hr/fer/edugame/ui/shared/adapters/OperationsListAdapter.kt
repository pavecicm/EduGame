package hr.fer.edugame.ui.shared.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import hr.fer.edugame.R
import hr.fer.edugame.extensions.inflate
import kotlinx.android.synthetic.main.list_item_operation.view.newOperationItem

class OperationsListAdapter(
    var operations: MutableList<String> = mutableListOf()
) : RecyclerView.Adapter<OperationsListAdapter.OperationsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        OperationsViewHolder(parent.inflate(R.layout.list_item_operation))


    override fun onBindViewHolder(holder: OperationsViewHolder, position: Int) {
        val operation = operations[position]
        holder.bind(operation)
    }

    override fun getItemCount() = operations.size

    fun initNewLetters(operations: List<String>) {
        this.operations.clear()
        this.operations.addAll(operations)
        notifyDataSetChanged()
    }

    fun updateItems(operations: List<String>) {
        this.operations.addAll(operations)
        notifyDataSetChanged()
    }

    fun updateItem(operation: String) {
        this.operations.add(operation)
        notifyDataSetChanged()
    }

    fun destroyItem(operation: String) {
        operations.remove(operation)
        notifyDataSetChanged()
    }

    fun resetAdapter() {
        operations.clear()
        notifyDataSetChanged()
    }

    inner class OperationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(operation: String) {
            with(itemView) {
                newOperationItem.text = operation
            }
        }
    }
}