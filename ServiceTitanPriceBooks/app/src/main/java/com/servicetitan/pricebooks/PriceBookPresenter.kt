package com.servicetitan.pricebooks

import com.servicetitan.pricebooks.service.model.BookService
import com.servicetitan.pricebooks.service.model.PriceBookItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers.io
import timber.log.Timber

class PriceBookPresenter {

    private lateinit var view: ViewContract
    private lateinit var service: BookService
    private var currentPage = 1
    private var pageSize = 20
    var hasMoreData = true

    private var disposable = CompositeDisposable()

    fun setView(view: ViewContract) {
        this.view = view
    }

    fun onTargetReady() {
        service = BookService.create()
        loadMoreData()
    }

    fun loadMoreData() {
        view.showLoading(isLoading = true)
        val query = mutableMapOf<String, String>()
        query["filter.page"] = currentPage.toString()
        query["filter.pageSize"] = pageSize.toString()
        disposable.add(service.getPriceBookList(queries = query)
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response ->
                hasMoreData = response.hasMore
                currentPage += response.page
                view.showLoading(isLoading = false)
                view.showBookList(bookList = response.data)
            }, {
                view.showLoading(isLoading = false)
                Timber.d("Error")
            })
        )
    }

    fun onDestroyTarget() {
        if(!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    interface ViewContract {
        fun showBookList(bookList: List<PriceBookItem>)
        fun showLoading(isLoading: Boolean)
    }
}