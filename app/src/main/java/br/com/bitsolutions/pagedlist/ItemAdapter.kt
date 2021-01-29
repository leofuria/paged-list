package br.com.bitsolutions.pagedlist

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import br.com.bitsolutions.pagedlist.adapter.PagedListAdapter

class ItemAdapter : PagedListAdapter<Item, ItemViewHolder>(DiffCallback) {

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }
        }
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return when (viewType) {
//            HOTEL_VIEW_TYPE -> ListOrderHotelViewHolder.create(parent).apply {
//                getNotifyItemClickViewHolder().subscribe {
//                    notifyItemClick.onNext(Pair(bindingAdapterPosition, it))
//                }
//                getNotifyCancelationCardClickViewHolder().subscribe {
//                    notifyCancelationCardClick.onNext(it)
//                }
//            }
//            LOADING_VIEW -> createLoadingView(parent)
//            ERROR_VIEW -> createErrorView(parent)
//            FOOTER_VIEW -> createFooterView(parent)
//            else -> createItemView(parent)
//        }
//    }
//
//    override fun getItemViewType(position: Int): Int {
//
//        return position.takeIf { it > helper.currentList.size - 1 }?.let {
//            when (state) {
//                State.ERROR -> ERROR_VIEW
//                State.FOOTER -> FOOTER_VIEW
//                else -> LOADING_VIEW
//            }
//        } ?: run {
//            val productType = getItem(position).items?.firstOrNull()?.productType
//
//            if (productType == TYPE_ORDER_HOTEL) HOTEL_VIEW_TYPE
////            else if (productType == TYPE_ORDER_PACKAGE || productType == TYPE_ORDER_TICKET) PACKAGE_VIEW_TYPE
//            else ITEM_VIEW
//        }
//    }

    override fun bindItemView(holder: ItemViewHolder, position: Int) {

        holder.bindItem(getItem(position))
    }

    override fun createItemView(parent: ViewGroup): ItemViewHolder {

        // PACKAGE_VIEW_TYPE -> Fica como default. Caso o adapter só tenha um viewHolder o
        // onCreateViewHolder não é necessário e o
        // bindItemView só precisará de um elemento sem a necessidade da estrutura acima
        return ItemViewHolder.create(parent).apply {
            getNotifyItemClickViewHolder().subscribe {
                notifyItemClick.onNext(Pair(adapterPosition, it))
            }
        }
    }
}

