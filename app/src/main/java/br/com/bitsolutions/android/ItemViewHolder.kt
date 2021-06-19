package br.com.bitsolutions.android

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.bitsolutions.android.databinding.ItemViewHolderBinding
import br.com.bitsolutions.pagedlist.adapter.PagedListViewHolder

class ItemViewHolder(private val view: ItemViewHolderBinding) : PagedListViewHolder<Item>(view.root) {

    companion object {
        fun create(parent: ViewGroup): ItemViewHolder {
            return ItemViewHolder(
                ItemViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            view.tvItemLabel.text = String.format("Number ${it.id}")
            view.tvItemValue.text = String.format("${it.desc} 64cb1168 - 38299166")
        }

    }
}