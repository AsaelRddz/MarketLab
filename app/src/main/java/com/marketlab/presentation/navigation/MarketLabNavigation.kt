package com.marketlab.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marketlab.presentation.features.catalog.CatalogRoute
import com.marketlab.presentation.features.detail.DetailRoute
import com.marketlab.presentation.features.favorites.FavoritesRoute

@Composable
fun MarketLabNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Catalog, modifier = modifier) {
        composable<Catalog> {
            CatalogRoute(
                openItem = { productId ->
                    navController.navigate(Detail(productId))
                },
                openFav = {
                    navController.navigate(Favorites)
                }
            )
        }

        composable<Detail> { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")
            DetailRoute(productId = productId)
        }

        composable<Favorites> {
            FavoritesRoute(
                openItem = { productId ->
                    navController.navigate(Detail(productId))
                }
            )
        }
    }
}