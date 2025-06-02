package com.example.eshop.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.eshop.data.Product
import com.example.eshop.data.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

//    private val _uiState = MutableStateFlow(ProductUiState())
//    val uiState = _uiState.onStart {
//        repository.getProductPager()
//    }.stateIn(
//        scope = viewModelScope,
//        started =  SharingStarted.WhileSubscribed(5000L),
//        initialValue = _uiState
//    )

    val pager = repository.getProductPager().flow.cachedIn(viewModelScope)
}


data class ProductUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null
)