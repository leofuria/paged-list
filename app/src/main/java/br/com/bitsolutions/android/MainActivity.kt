package br.com.bitsolutions.android

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.bitsolutions.android.databinding.ActivityMainBinding
import br.com.bitsolutions.pagedlist.adapter.PagedListAdapter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ItemAdapter

    private lateinit var binding: ActivityMainBinding

    private var errorEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ItemAdapter()
        binding.pagedListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.pagedListRecyclerView.background =
            ContextCompat.getDrawable(this, android.R.color.white)
        binding.pagedListRecyclerView.getRecyclerView().setHasFixedSize(true)
        binding.pagedListRecyclerView.adapter = this.adapter

        adapter.loadMoreListener = object : PagedListAdapter.ILoadMoreListener {
            override fun onLoadMore() {
                if (errorEnabled) {
                    errorEnabled = false
                    checkError()
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({ getData2() }, 3000)
                }
            }
        }

        binding.pagedListRecyclerView.setOnRefreshListener {
            binding.pagedListRecyclerView.isRefreshing = true
            getData()
        }

        adapter.apply {
            getNotifyItemClick().subscribe {
                val message = "${it.second.id} - ${it.second.desc}"
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
            }.addTo(compositeDisposable = CompositeDisposable())
        }
        adapter.hasFooter = false

        if (errorEnabled) {
            errorEnabled = false
            checkError()
        } else {
            Handler(Looper.getMainLooper()).postDelayed({ getData() }, 3000)
        }
    }

    private fun getData() {
        adapter.submitList(
            listOf(
                Item("1", "DESC"),
                Item("2", "DESC"),
                Item("3", "DESC"),
                Item("4", "DESC"),
                Item("5", "DESC"),
                Item("6", "DESC"),
                Item("7", "DESC"),
                Item("8", "DESC"),
                Item("9", "DESC"),
                Item("10", "DESC"),
                Item("11", "DESC"),
                Item("12", "DESC"),
                Item("13", "DESC"),
                Item("14", "DESC"),
                Item("15", "DESC"),
                Item("16", "DESC"),
                Item("17", "DESC"),
                Item("18", "DESC"),
                Item("19", "DESC"),
                Item("20", "DESC")
            )
        )
        binding.pagedListRecyclerView.isRefreshing = false
        adapter.loadEnable = true
        errorEnabled = true
    }

    private fun getData2() {
        adapter.submitList(
            listOf(
                Item("1", "DESC"),
                Item("2", "DESC"),
                Item("3", "DESC"),
                Item("4", "DESC"),
                Item("5", "DESC"),
                Item("6", "DESC"),
                Item("7", "DESC"),
                Item("8", "DESC"),
                Item("9", "DESC"),
                Item("10", "DESC"),
                Item("11", "DESC"),
                Item("12", "DESC"),
                Item("13", "DESC"),
                Item("14", "DESC"),
                Item("15", "DESC"),
                Item("16", "DESC"),
                Item("17", "DESC"),
                Item("18", "DESC"),
                Item("19", "DESC"),
                Item("20", "DESC"),
                Item("21", "DESC"),
                Item("22", "DESC"),
                Item("23", "DESC"),
                Item("24", "DESC"),
                Item("25", "DESC"),
                Item("26", "DESC"),
                Item("27", "DESC"),
                Item("28", "DESC"),
                Item("29", "DESC"),
                Item("30", "DESC"),
                Item("31", "DESC"),
                Item("32", "DESC"),
                Item("33", "DESC"),
                Item("34", "DESC"),
                Item("35", "DESC"),
                Item("36", "DESC"),
                Item("37", "DESC"),
                Item("38", "DESC"),
                Item("39", "DESC"),
                Item("40", "DESC")
            )
        )
        binding.pagedListRecyclerView.isRefreshing = false
        adapter.loadEnable = false
    }

    private fun checkError() {
        if (adapter.itemCount <= 0) {
            binding.pagedListRecyclerView.showFeedbackStatus(
                feedbackTitle = R.string.paged_list_generic_error,
                feedbackMessage = R.string.paged_list_verify_connection_label,
                action = {
                    binding.pagedListRecyclerView.takeUnless { it.isRefreshing }?.isRefreshing = true
                    getData()
                }
            )
        } else {
            adapter.showLoadMoreError()
        }
    }
}