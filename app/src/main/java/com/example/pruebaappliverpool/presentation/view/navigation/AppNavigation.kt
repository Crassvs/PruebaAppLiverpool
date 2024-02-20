package com.example.pruebaappliverpool.presentation.view.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pruebaappliverpool.data.navigation.Screens
import com.example.pruebaappliverpool.presentation.view.search.BusquedaActivity
import com.example.pruebaappliverpool.presentation.view.order.OrderProductActivity
import com.example.pruebaappliverpool.presentation.view.product.ProductActivity
import com.example.pruebaappliverpool.presentation.view.product.SearchView
import com.example.pruebaappliverpool.presentation.viewmodel.ProductViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.ProductMainView.route){
        composable(route = Screens.ProductMainView.route){
            val productViewModel = hiltViewModel<ProductViewModel>()
            Column {
                SearchView(navController)
                ProductActivity(navController, productViewModel)
            }
        }
        composable(route = Screens.SearchActivity.route + "/{search}",
            arguments = listOf(navArgument(name = "search"){
                type = NavType.StringType
            })){
            val productViewModel = hiltViewModel<ProductViewModel>()
            BusquedaActivity(navController, productViewModel,
                it.arguments?.getString("search")
            )
        }

        composable(route = Screens.OrderActivity.route + "/{order}",
            arguments = listOf(navArgument(name = "order"){
                type = NavType.StringType
            })){
            val productViewModel = hiltViewModel<ProductViewModel>()
            OrderProductActivity(navController, productViewModel,
                it.arguments?.getString("order")
            )
        }
    }
}