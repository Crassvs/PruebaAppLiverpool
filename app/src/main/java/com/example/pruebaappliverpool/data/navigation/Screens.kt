package com.example.pruebaappliverpool.data.navigation

sealed class Screens(val route : String) {
    object ProductMainView : Screens("product_activity")
    object SearchActivity : Screens("search_activity")
    object OrderActivity : Screens("order_activity")

}

