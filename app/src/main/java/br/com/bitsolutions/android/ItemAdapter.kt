package br.com.bitsolutions.android

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import br.com.bitsolutions.pagedlist.adapter.PagedListAdapter

/**
 * If has only one View Holder uses YourViewHolder otherwise RecyclerView.ViewHolder
 */
class ItemAdapter : PagedListAdapter<Item, ItemViewHolder>(DiffCallback) {

    companion object {
//        const val OTHER_VIEW_TYPE = 5
//        const val OTHER_TYPE = "otherType"

        val DiffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }
        }
    }

    /**
     * If has only one View Holder uses YourViewHolder and below structure
     */
    override fun bindItemView(holder: ItemViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    override fun createItemView(parent: ViewGroup): ItemViewHolder {
        return ItemViewHolder.create(parent).apply {
            getNotifyItemClickViewHolder().subscribe {
                notifyItemClick.onNext(Pair(adapterPosition, it))
            }
        }
    }


    /**
     * If has only more than one View Holder uses RecyclerView.ViewHolder and below structure
     */
    /*
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            OTHER_VIEW_TYPE -> OtherViewHolder.create(parent).apply {
                getNotifyItemClickViewHolder().subscribe {
                    notifyItemClick.onNext(Pair(bindingAdapterPosition, it))
                }
            }
            LOADING_VIEW -> createLoadingView(parent)
            ERROR_VIEW -> createErrorView(parent)
            FOOTER_VIEW -> createFooterView(parent)
            else -> createItemView(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {

        return position.takeIf { it > helper.currentList.size - 1 }?.let {
            when (state) {
                PagedListState.ERROR -> ERROR_VIEW
                PagedListState.FOOTER -> FOOTER_VIEW
                else -> LOADING_VIEW
            }
        } ?: run {
            val otherType = getItem(position).items?.firstOrNull()?.otherType

            if (hasHeader && position == 0) HEADER_VIEW
            else if (otherType == OTHER_TYPE) OTHER_VIEW_TYPE
            else ITEM_VIEW
        }
    }

    override fun bindItemView(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            OTHER_VIEW_TYPE -> (holder as OtherViewHolder).bindItem(getItem(position))
            else -> (holder as ItemViewHolder).bindItem(getItem(position))
        }
    }

    override fun createItemView(parent: ViewGroup): RecyclerView.ViewHolder {
        return ItemViewHolder.create(parent).apply {
            getNotifyItemClickViewHolder().subscribe {
                notifyItemClick.onNext(Pair(adapterPosition, it))
            }
        }
    }*/
}

