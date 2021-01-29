package br.com.bitsolutions.pagedlist.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.bitsolutions.pagedlist.R

class PagedListLayout : SwipeRefreshLayout {

    var adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>?
        get() = recyclerView.adapter
        set(value) {
            recyclerView.adapter = value
        }

    var layoutManager: RecyclerView.LayoutManager?
        get() = recyclerView.layoutManager
        set(value) {
            recyclerView.layoutManager = value
        }

    private val feedbackView: DefaultErrorView by lazy { DefaultErrorView(context) }

    private val content: FrameLayout = FrameLayout(context)

    private val recyclerView: RecyclerView = RecyclerView(context)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        recyclerView.itemAnimator = null
        recyclerView.setHasFixedSize(true)
        recyclerView.takeIf { it.layoutManager == null }.apply { layoutManager = LinearLayoutManager(context) }
        feedbackView.visibility = View.GONE

        content.addView(recyclerView, layoutParams)
        content.addView(feedbackView, layoutParams)

        addView(content, layoutParams)
    }

    override fun setRefreshing(refreshing: Boolean) {

        if (isRefreshing != refreshing) {

            recyclerView.visibility = if (refreshing) {
                View.GONE
            } else {
                View.VISIBLE
            }
            hideFeedbackStatus()
        }
        super.setRefreshing(refreshing)
    }

    fun showFeedbackStatus(imageResource: Int = R.drawable.pagedlist_ic_baseline_signal_wifi_off_24,
                           feedbackTitle: String,
                           feedbackMessage: String,
                           action: (() -> Unit)? = null) {

        feedbackView.showFeedbackStatus(
            imageResource,
            feedbackTitle,
            feedbackMessage,
            action
        )
        recyclerView.visibility = View.GONE
    }

    fun showFeedbackStatus(imageResource: Int = R.drawable.pagedlist_ic_baseline_signal_wifi_off_24,
                           feedbackTitle: Int,
                           feedbackMessage: Int = 0,
                           action: (() -> Unit)? = null) {

        feedbackView.showFeedbackStatus(
            imageResource,
            feedbackTitle,
            feedbackMessage,
            action
        )
        recyclerView.visibility = View.GONE
    }

    private fun hideFeedbackStatus() {

        feedbackView.takeIf { visibility == View.VISIBLE }?.also {
            it.hideFeedbackStatus()
        }
    }

    fun getRecyclerView(): RecyclerView {
        return recyclerView
    }
}