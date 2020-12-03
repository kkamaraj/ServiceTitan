package com.servicetitan.pricebooks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.servicetitan.pricebooks.R
import com.servicetitan.pricebooks.service.model.PriceBookItem
import com.squareup.picasso.Picasso

class PriceBookListAdapter(
    private var priceBookData: MutableList<PriceBookItem> = mutableListOf(),
    private var curSize: Int = 0
): RecyclerView.Adapter<PriceBookItemHolder>() {

    fun setBookData(bookList: List<PriceBookItem>) {
        priceBookData.addAll(bookList)
        notifyItemRangeInserted(curSize, bookList.size)
        curSize += priceBookData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceBookItemHolder {
        val listItemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.price_book_item, parent, false)
        return PriceBookItemHolder(listItemView)
    }

    override fun onBindViewHolder(holder: PriceBookItemHolder, position: Int) {
        val bookItem = priceBookData[position]
        Picasso.get()
            .load(bookItem.thumbnailUrl)
            .into(holder.bookImage)
        holder.bookName.text = bookItem.name
        holder.bookPrice.text = bookItem.price.toString()
    }

    override fun getItemCount() = priceBookData.size
}

class PriceBookItemHolder(view: View): RecyclerView.ViewHolder(view) {
    val bookImage: ImageView = view.findViewById(R.id.book_image)
    val bookName: TextView = view.findViewById(R.id.book_name)
    val bookPrice: TextView = view.findViewById(R.id.book_price)
}
