package com.example.eshop.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.eshop.api.ProductApiService

class ProductPagingSource (
    private val api: ProductApiService
): PagingSource<String, Product>(){
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Product> {
        return try {
            val url = params.key ?: "products_1.json"
            val response = api.getProducts(url)
            LoadResult.Page(
                data = response.products,
                prevKey = null,
                nextKey = response.nextPageUrl
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Product>): String? = null
}