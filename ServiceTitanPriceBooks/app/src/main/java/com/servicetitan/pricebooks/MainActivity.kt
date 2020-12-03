package com.servicetitan.pricebooks

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.servicetitan.pricebooks.adapter.PriceBookListAdapter
import com.servicetitan.pricebooks.service.model.PriceBookItem


class MainActivity : AppCompatActivity(), PriceBookPresenter.ViewContract {
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var listView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: PriceBookPresenter
    private lateinit var adapter: PriceBookListAdapter
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.book_price_list)
        progressBar = findViewById(R.id.progress_bar)

        adapter = PriceBookListAdapter(mutableListOf())
        presenter = PriceBookPresenter()
        layoutManager = LinearLayoutManager(this);

        listView.adapter = adapter
        listView.layoutManager = layoutManager

         //For Pagination
        listView.addOnScrollListener(recyclerViewOnScrollListener)
        presenter.setView(this)
        presenter.onTargetReady()
    }

    override fun showBookList(bookList: List<PriceBookItem>) {
        adapter.setBookData(bookList)
    }

    override fun showLoading(isLoading: Boolean) {
        this.isLoading = isLoading
        progressBar.isVisible = this.isLoading
    }

    override fun onStop() {
        super.onStop()
        presenter.onDestroyTarget()
    }

    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
                if (!isLoading && presenter.hasMoreData) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount &&
                        firstVisibleItemPosition >= 0) {
                        presenter.loadMoreData()
                    }
                }
            }
        }
}