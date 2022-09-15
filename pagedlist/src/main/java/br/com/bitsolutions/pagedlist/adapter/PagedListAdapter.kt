package br.com.bitsolutions.pagedlist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.recyclerview.widget.*
import br.com.bitsolutions.pagedlist.databinding.PagedListErrorViewHolderBinding
import br.com.bitsolutions.pagedlist.databinding.PagedListFooterViewHolderBinding
import br.com.bitsolutions.pagedlist.databinding.PagedListHeaderViewHolderBinding
import br.com.bitsolutions.pagedlist.databinding.PagedListLoadingViewHolderBinding
import br.com.bitsolutions.pagedlist.enums.PagedListState
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

@SuppressLint("NotifyDataSetChanged")
abstract class PagedListAdapter<T, VH : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected val helper: AsyncListDiffer<T> = AsyncListDiffer(
        AdapterListUpdateCallback(this),
        AsyncDifferConfig.Builder(diffCallback).build()
    )
    protected var state = PagedListState.LOADING
    protected val disposable = CompositeDisposable()
    protected var hasHeader = false
    var hasFooter = false
    var mHeaderCount = 0

    var loadEnable: Boolean = true
        set(value) {
            if (!value)
                state = PagedListState.FOOTER

            field = value
            notifyDataSetChanged()
        }

    var loadMoreListener: ILoadMoreListener? = null

    private var userScrolled = false

    private var scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            super.onScrollStateChanged(recyclerView, newState)

            // If scroll state is touch scroll then set userScrolled
            // true
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                userScrolled = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            recyclerView.layoutManager?.also {
                val firstVisibleItemPosition =
                    (it as LinearLayoutManager).findFirstVisibleItemPosition()

                if (
                    userScrolled &&
                    state != PagedListState.LOADING &&
                    loadEnable &&
                    ((it.childCount + firstVisibleItemPosition) >= (it.itemCount - 5))
                ) {
                    userScrolled = false
                    state = PagedListState.LOADING
                    recyclerView.post { loadMoreListener?.onLoadMore() }
                }
            }
        }
    }

    private val spanSizeManager = object : GridLayoutManager.SpanSizeLookup() {

        var spanCount: Int = 0

        override fun getSpanSize(position: Int): Int {
            return when {
                isFixedViewType(getItemViewType(position)) -> spanCount
                else -> 1
            }
        }
    }

    protected fun isFixedViewType(type: Int): Boolean {
        return type == ERROR_VIEW || type == FOOTER_VIEW || type == HEADER_VIEW || type == LOADING_VIEW || type == ITEM_VIEW
    }

    val notifyItemClick: PublishSubject<Pair<Int, T>> = PublishSubject.create()

    fun getNotifyItemClick(): Observable<Pair<Int, T>> {
        return notifyItemClick
    }

    abstract fun createItemView(parent: ViewGroup): VH

    abstract fun bindItemView(holder: VH, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            LOADING_VIEW -> createLoadingView(parent)
            ERROR_VIEW -> createErrorView(parent)
            FOOTER_VIEW -> createFooterView(parent)
            HEADER_VIEW -> createHeaderView(parent)
            else -> createItemView(parent)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is PagedListViewHolder<*>) {
            bindItemView(holder as VH, position)
        }
    }

    override fun getItemViewType(position: Int): Int {

        return position.takeIf { it - mHeaderCount > helper.currentList.size - 1 }?.let {
            when (state) {
                PagedListState.ERROR -> ERROR_VIEW
                PagedListState.FOOTER -> FOOTER_VIEW
                else -> LOADING_VIEW
            }
        } ?: run {

            if (hasHeader && position == 0) HEADER_VIEW
            else ITEM_VIEW
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {

        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(scrollListener)
        (recyclerView.layoutManager as? GridLayoutManager)?.also {
            this.spanSizeManager.spanCount = it.spanCount
            it.spanSizeLookup = this.spanSizeManager
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {

        disposable.clear()
        recyclerView.removeOnScrollListener(scrollListener)
        super.onDetachedFromRecyclerView(recyclerView)
    }

    fun submitList(list: List<T>?) {

        if (list.isNullOrEmpty()) {
            helper.submitList(list)
            return
        }

        state = PagedListState.DONE
        helper.submitList(list)

        notifyDataSetChanged()
    }

    fun showLoadMoreError() {

        notifyDataSetChanged()
        state = PagedListState.ERROR
    }

    fun showHeader() {
        hasHeader = true
        mHeaderCount = 1
    }

    fun getItem(position: Int): T {
        return helper.currentList[position - mHeaderCount]
    }

    override fun getItemCount(): Int {

        return helper.currentList.size.let {
            mHeaderCount + if ((loadEnable || state == PagedListState.FOOTER) && it > 0) it + 1 else it
        }
    }

    protected open fun createErrorView(parent: ViewGroup): RecyclerView.ViewHolder {

        val view = PagedListErrorViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        view.tryAgainButton.setOnClickListener {
            state = PagedListState.DONE
            notifyDataSetChanged()
            loadMoreListener?.onLoadMore()
        }

        return object : RecyclerView.ViewHolder(view.root) {}
    }

    protected open fun createLoadingView(parent: ViewGroup): RecyclerView.ViewHolder {

        val view = PagedListLoadingViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return object : RecyclerView.ViewHolder(view.root) {}
    }

    protected open fun createFooterView(parent: ViewGroup): RecyclerView.ViewHolder {

        val view = PagedListFooterViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        view.endListFooter.visibility = if (hasFooter) View.VISIBLE else View.GONE

        if (!hasFooter)
            view.endListFooter.layoutParams = RecyclerView.LayoutParams(0, 0)

        return object : RecyclerView.ViewHolder(view.root) {}
    }

    protected open fun createHeaderView(parent: ViewGroup): RecyclerView.ViewHolder {

        val view = PagedListHeaderViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return object : RecyclerView.ViewHolder(view.root) {}
    }

    @SuppressLint("unused")
    fun updateItem(position: Int, item: T) {
        val list = ArrayList(helper.currentList)
        list[position] = item
        helper.submitList(list)
        notifyItemChanged(position)
    }

    fun removeItem(position: Int) {
        val list = ArrayList(helper.currentList)
        list.removeAt(position)
        helper.submitList(list)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    interface ILoadMoreListener {
        fun onLoadMore()
    }

    companion object {
        const val ITEM_VIEW = 0
        const val LOADING_VIEW = 1
        const val ERROR_VIEW = 2
        const val FOOTER_VIEW = 3
        const val HEADER_VIEW = 4
    }
}