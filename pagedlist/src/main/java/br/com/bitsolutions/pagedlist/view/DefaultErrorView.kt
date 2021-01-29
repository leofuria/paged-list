package br.com.bitsolutions.pagedlist.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import br.com.bitsolutions.pagedlist.R

class DefaultErrorView : FrameLayout {

    private val errorImage by lazy { findViewById<ImageView>(R.id.errorImage) }
    private val errorTitleText by lazy { findViewById<TextView>(R.id.errorTitleText) }
    private val errorText by lazy { findViewById<TextView>(R.id.errorText) }
    private val refreshButton by lazy { findViewById<Button>(R.id.refreshButton) }

    constructor(context: Context) : super(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        inflate(context, R.layout.pagedlist_layout_error_default, this)
    }

    private fun setErrorImage(resourceId: Int) {
        errorImage.setImageResource(resourceId)
    }

    private fun setErrorTitle(title: String) {
        errorTitleText.text = title
    }

    private fun setErrorTitle(titleId: Int) {
        setErrorTitle(context.getString(titleId))
    }

    private fun setErrorText(text: String) {
        errorText.text = text
        errorText.visibility = if (text.isBlank()) View.GONE else View.VISIBLE
    }

    private fun setErrorText(textId: Int = 0) {
        setErrorText(textId.takeIf { it != 0 }?.let { context.getString(textId) } ?: "")
    }

    private fun setErrorButtonAction(action: (() -> Unit)? = null) {

        if (action != null) {
            refreshButton.setOnClickListener { action.invoke() }
            refreshButton.visibility = View.VISIBLE
        } else {
            refreshButton.setOnClickListener(null)
            refreshButton.visibility = View.GONE
        }
    }

    fun showFeedbackStatus(
        imageResource: Int = R.drawable.pagedlist_ic_baseline_signal_wifi_off_24,
        feedbackTitle: String,
        feedbackMessage: String = "",
        action: (() -> Unit)? = null
    ) {

        setErrorImage(imageResource)
        setErrorTitle(feedbackTitle)
        setErrorText(feedbackMessage)
        setErrorButtonAction(action)
        visibility = View.VISIBLE
    }

    fun showFeedbackStatus(
        imageResource: Int = R.drawable.pagedlist_ic_baseline_signal_wifi_off_24,
        feedbackTitle: Int,
        feedbackMessage: Int = 0,
        action: (() -> Unit)? = null
    ) {

        setErrorImage(imageResource)
        setErrorTitle(feedbackTitle)
        setErrorText(feedbackMessage)
        setErrorButtonAction(action)
        visibility = View.VISIBLE
    }

    fun hideFeedbackStatus() {
        setErrorButtonAction()
        visibility = View.GONE
    }
}
