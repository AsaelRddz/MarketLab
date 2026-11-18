package com.marketlab.presentation.features.catalog

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.marketlab.domain.model.Product
import com.marketlab.presentation.theme.MarketLabTheme
import org.junit.Rule
import org.junit.Test

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class CatalogScreenRobolectricTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun searchByTitle_showsOnlyMatchingProducts() {
        val products = listOf(
            Product(1, "Mochila", "Mochila urbana", 599.0, ""),
            Product(2, "Audífonos", "Audífonos inalámbricos", 899.0, "")
        )

        composeRule.setContent {
            MarketLabTheme {
                CatalogScreen(
                    uiState = CatalogUiState.Success(products),
                    onRetry = {},
                    openItem = {},
                    onOpenFavorites = {}
                )
            }
        }

        composeRule.onNodeWithText("Mochila").assertIsDisplayed()
        composeRule.onNodeWithText("Audífonos").assertIsDisplayed()

        val searchField = composeRule.onNode(hasSetTextAction())
        searchField.performClick()
        searchField.performTextInput("mochila")

        composeRule.onNodeWithText("Mochila").assertIsDisplayed()
        composeRule.onNodeWithText("Audífonos").assertDoesNotExist()
    }
}