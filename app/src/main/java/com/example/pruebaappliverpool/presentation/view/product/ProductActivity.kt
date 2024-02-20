package com.example.pruebaappliverpool.presentation.view.product

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.pruebaappliverpool.data.model.Record
import com.example.pruebaappliverpool.data.navigation.Screens
import com.example.pruebaappliverpool.presentation.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(navController: NavHostController) {
    val productViewModel: ProductViewModel = viewModel()
    var isClicked by remember {
        mutableStateOf(false)
    }
    val ctx = LocalContext.current
    var query by remember {
        mutableStateOf("")
    }

    var active by remember {
        mutableStateOf(false)
    }

    SearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = {
            active = false
            isClicked = true
        },
        active = active,
        onActiveChange = { active = it },
        modifier = Modifier
            .wrapContentHeight()
            .padding(8.dp)
            .fillMaxWidth(),

        placeholder = { Text(text = "Nueva Búsqueda") },
        trailingIcon = {
            IconButton(onClick = { isClicked = true }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
        }

    ) {
        if (isClicked){
            isClicked = false
            navController.navigate(Screens.SearchActivity.route +"/"+query)
        }
    }
}

@Composable
fun ListProductsView(
    modifier: Modifier = Modifier,
    record: Record?
) {
    Column {
        CardView(modifier = modifier, record= record)
    }
}


@Composable
fun ProductActivity(navController: NavHostController, productViewModel: ProductViewModel) {

    val products = productViewModel.getProduct().collectAsLazyPagingItems()
    Column {
        //Menu(products,navController)
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
                                        .padding(12.dp)
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
                                        .padding(12.dp)
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
                                    .padding(16.dp)
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


@Composable
fun CardView(modifier: Modifier, record: Record?){
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth()

    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.width(400.dp)
            ) {

                AsyncImage(
                    model = record!!.smImage,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp)
                        .padding(4.dp),
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                ) {
                    Text(
                        text = record.productDisplayName,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 12.sp
                    )
                    if (record.listPrice == record.promoPrice){
                        Text(
                            text = record.promoPrice.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 10.dp, top = 17.dp),
                            fontSize = 10.sp,
                            color = Color.Red
                        )
                    }else{
                        Text(
                            text = record.listPrice.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 10.dp),
                            fontSize = 10.sp,
                            textDecoration = TextDecoration.LineThrough
                        )
                        Text(
                            text = record.promoPrice.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 10.dp, top = 17.dp),
                            fontSize = 10.sp,
                            color = Color.Red
                        )
                    }


                    LazyRow {
                        try {
                            items(record.variantsColor){ color ->
                                if (color.colorHex.isNotEmpty() && color.colorHex != "") {
                                    Box(
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .size(15.dp)
                                            .clip(CircleShape)
                                            .background(color = Color(color.colorHex.toColorInt()))
                                    )
                                }
                            }
                        }catch (e: Exception){
                            Log.e("error ", e.toString())
                        }
                    }
                }
            }
        }
    }
}


