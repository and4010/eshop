package com.example.eshop.data


import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.eshop.api.ProductApiService
import javax.inject.Inject



class ProductRepository @Inject constructor(
    private val api: ProductApiService
) {

    /**
     * 取得頁面
     */
    fun getProductPager(): Pager<String, Product> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { ProductPagingSource(api) }
        )
    }

}