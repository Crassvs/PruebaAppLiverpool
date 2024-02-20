package com.example.pruebaappliverpool.presentation.view.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pruebaappliverpool.data.constant.Constant
import com.example.pruebaappliverpool.data.model.SortOption
import com.example.pruebaappliverpool.data.navigation.Screens
import com.example.pruebaappliverpool.presentation.view.product.ListProductsView
import com.example.pruebaappliverpool.presentation.viewmodel.ProductViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusquedaActivity(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    search: String?
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Resúltados Encontrados")
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
        ListProductsViewSearch(contentPadding, navController, productViewModel, search)
    }

}


@Composable
fun ListProductsViewSearch(
    contentPadding: PaddingValues,
    navController: NavHostController,
    productViewModel: ProductViewModel,
    search: String?
) {
    val products = productViewModel.getProduct().collectAsLazyPagingItems()

    Constant.busqueda = search!!

    Column(
        modifier = Modifier.
    padding(contentPadding)
    ) {
        Menu(navController = navController)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(
    navController: NavHostController,
) {
    val orderList = arrayListOf(
        SortOption("Relevancia","Relevancia|1"),
        SortOption("Lo Más Nuevo","newArrival|1"),
        SortOption("Menor Precio","sortPrice|1"),
        SortOption("Mayor Precio","sortPrice|1"),
        SortOption("Calificaciones","rating|1")
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(orderList[0].label) }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .clip(CircleShape)
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                orderList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.label) },
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            selectedText = item.label
                            expanded = false

                            navController.navigate(Screens.OrderActivity.route +"/"+item.sortBy)
                        }
                    )
                }
            }
        }
    }
}
