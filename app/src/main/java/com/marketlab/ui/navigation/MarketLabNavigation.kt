package com.marketlab.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marketlab.ui.catalog.CatalogScreen
import com.marketlab.ui.detail.DetailScreen

@Composable
fun MarketLabNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Catalog, modifier = modifier) {
        composable<Catalog> {
            CatalogScreen(
                openItem = { productId ->
                    navController.navigate(Detail(productId))
                }
            )
        }

        composable<Detail> { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")
            DetailScreen(productId = productId)
        }
    }
}