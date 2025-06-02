package com.example.eshop.ui



import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil3.compose.AsyncImage
import com.example.eshop.data.Product
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.eshop.R
import com.example.eshop.data.DiscountPrice
import com.example.eshop.data.Price

@Composable
fun ProductScreen(viewModel: ProductViewModel = hiltViewModel()) {

//    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val pagingItems = viewModel.pager.collectAsLazyPagingItems()

    ProductContent(products = pagingItems )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductContent(products: LazyPagingItems<Product>) {

    val isRefreshing = products.loadState.refresh is LoadState.Loading

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { products.refresh() },
        modifier = Modifier.fillMaxSize()
    ) {


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )  {
            items(products.itemCount) { index ->
                products[index]?.let {
                    ProductItem(it)
                }
            }

            when (products.loadState.append) {
                is LoadState.Loading -> item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                }
                is LoadState.Error -> item {
                    Text("載入更多失敗", color = Color.Red, modifier = Modifier.padding(16.dp))
                }
                else -> {}
            }

        }

        when (val state = products.loadState.refresh) {
            is LoadState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("載入失敗: ${state.error.localizedMessage}", color = Color.Red)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { products.retry() }) {
                            Text("重試")
                        }
                    }
                }
            }
            else -> {}
        }
    }

}

@Composable
fun ProductItem(product: Product) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(product.image)
        .crossfade(true)
        .build()
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // 圖片區塊
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)
                   ,
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageRequest,
                    contentDescription = "照片",
                    placeholder = painterResource(R.drawable.baseline_error_24),
                    error = painterResource(R.drawable.baseline_error_24),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Log.d("ProductItem", "imageUrl = ${product.image}")


            Spacer(modifier = Modifier.height(12.dp))

            // 商品名稱
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 價格區塊
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "${product.price.currency} ${product.price.value}",
                    color = Color(0xFFD32F2F),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text =  "${product.price.value}", // 可改為原價欄位
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = TextDecoration.LineThrough
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 購買按鈕
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("即買", color = Color.Black)
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductItemPreView() {
    ProductItem(Product(12,"Product 45", price = Price(20.0,"HK"),discountPrice = DiscountPrice(10.0,"HK"), image = "https://picsum.photos/id/28/300/300"))
}

