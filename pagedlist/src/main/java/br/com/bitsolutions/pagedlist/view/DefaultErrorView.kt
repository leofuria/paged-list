package br.com.bitsolutions.pagedlist.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import br.com.bitsolutions.pagedlist.R
import br.com.bitsolutions.pagedlist.databinding.PagedListLayoutErrorDefaultBinding

class DefaultErrorView : FrameLayout {

    constructor(context: Context) : super(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    private var binding: PagedListLayoutErrorDefaultBinding = PagedListLayoutErrorDefaultBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    private fun setErrorImage(resourceId: Int) {
        binding.errorImage.setImageResource(resourceId)
    }

    private fun setErrorTitle(title: String) {
        binding.errorTitleText.text = title
    }

    private fun setErrorTitle(titleId: Int) {
        setErrorTitle(context.getString(titleId))
    }

    private fun setErrorText(text: String) {
        binding.errorText.text = text
        binding.errorText.visibility = if (text.isBlank()) View.GONE else View.VISIBLE
    }

    private fun setErrorText(textId: Int = 0) {
        setErrorText(textId.takeIf { it != 0 }?.let { context.getString(textId) } ?: "")
    }

    private fun setErrorButtonAction(action: (() -> Unit)? = null) {

        if (action != null) {
            binding.refreshButton.setOnClickListener { action.invoke() }
            binding.refreshButton.visibility = View.VISIBLE
        } else {
            binding.refreshButton.setOnClickListener(null)
            binding.refreshButton.visibility = View.GONE
        }
    }

    fun showFeedbackStatus(
        imageResource: Int = R.drawable.paged_list_ic_baseline_signal_wifi_off_24,
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
        imageResource: Int = R.drawable.paged_list_ic_baseline_signal_wifi_off_24,
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
