package com.servicetitan.pricebooks.service.model

data class PriceBookResponse (
    var page: Int,
    var pageSize: Int,
    var totalCount: Int,
    var hasMore: Boolean,
    var data: List<PriceBookItem>
)

data class PriceBookItem (
    var id: Int,
    var name: String = "No Name",
    var price: Double
) {
    val thumbnailUrl: String
        get() = "https://picsum.photos/id/${id % 1000}/75/75"
}