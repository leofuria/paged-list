package br.com.bitsolutions.pagedlist.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

open class PagedListViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {

    var currentItem: T? = null

    val notifyItemClickViewHolder: PublishSubject<T> = PublishSubject.create()

    open fun getNotifyItemClickViewHolder(): Observable<T> {
        return notifyItemClickViewHolder
    }

    open fun bindItem(item: T?) {}
}