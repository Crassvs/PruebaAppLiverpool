package com.example.pruebaappliverpool.presentation.view.order

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pruebaappliverpool.data.constant.Constant
import com.example.pruebaappliverpool.presentation.view.product.ListProductsView
import com.example.pruebaappliverpool.presentation.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderProductActivity(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    order: String?
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Principal")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        ListProductsViewOrder(contentPadding, navController, productViewModel,order)
    }
}

@Composable
fun ListProductsViewOrder(
    contentPadding: PaddingValues,
    navController: NavHostController,
    productViewModel: ProductViewModel,
    order: String?
) {
    val products = productViewModel.getProduct().collectAsLazyPagingItems()
    Constant.order = order!!

    Column(modifier = Modifier.
    padding(contentPadding)
    ) {
        LazyColumn {
            items(products.itemCount) { product ->
                ListProductsView(modifier = Modifier.padding(10.dp), products[product])
            }

            products.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .padding(contentPadding)
                                        .align(Alignment.BottomCenter)
                                )
                            }
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .padding(contentPadding)
                                        .align(Alignment.BottomCenter)
                                )
                            }
                        }
                    }

                    loadState.prepend is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(contentPadding)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .align(Alignment.BottomCenter)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}