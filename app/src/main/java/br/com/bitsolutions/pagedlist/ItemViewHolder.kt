package br.com.bitsolutions.pagedlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.bitsolutions.pagedlist.adapter.PagedListViewHolder

class ItemViewHolder(val view: View) : PagedListViewHolder<Item>(view) {

    val tvItemLabel = itemView.findViewById<TextView>(R.id.tvItemLabel)
    val tvItemValue = itemView.findViewById<TextView>(R.id.tvItemValue)

    companion object {
        fun create(parent: ViewGroup): ItemViewHolder {
            return ItemViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_view_holder, parent, false)
            )
        }
    }

    init {
        itemView.setOnClickListener {
            currentItem?.run {
                notifyItemClickViewHolder.onNext(this)
            }
        }
    }

    override fun bindItem(item: Item?) {
        currentItem = item
        currentItem?.let {
            tvItemLabel.text = "Número ${it.id}"
            tvItemValue.text = "${it.desc} 64cb1168 - 38299166"
        }

    }
}