package com.marketlab

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.marketlab.domain.model.Product
import com.marketlab.presentation.features.catalog.CatalogScreen
import com.marketlab.presentation.features.catalog.CatalogUiState
import com.marketlab.presentation.theme.MarketLabTheme
import org.junit.Rule
import org.junit.Test

class CatalogScreenTest {
    // Expone la regla a JUnit mediante el getter de la propiedad.
    @get:Rule
    // Inicializa el entorno de Compose para montar la pantalla e interactuar con ella.
    val composeRule = createComposeRule()


    // Registra este método como un caso de prueba.
    @Test
    // Comprueba que la búsqueda por título conserve solo las coincidencias.
    fun searchByTitle_showsOnlyMatchingProducts() {
        // Prepara una lista conocida con una coincidencia y un producto que debe desaparecer.
        val products = listOf(
            Product(1, "Mochila", "Mochila urbana", 599.0, ""),
            Product(2, "Audífonos", "Audífonos inalámbricos", 899.0, "")
        )

        // Monta el contenido de Compose en la actividad de prueba.
        composeRule.setContent {
            MarketLabTheme {
                CatalogScreen(
                    // Entrega los datos como si el catálogo ya hubiera terminado de cargar.
                    uiState = CatalogUiState.Success(products),
                    onRetry = {},
                    openItem = {},
                    onOpenFavorites = {}
                )
            }
        }

        // Confirma que el producto que coincide está visible antes de buscar.
        composeRule.onNodeWithText("Mochila").assertIsDisplayed()
        // Confirma que el producto que luego se filtrará también estaba visible.
        composeRule.onNodeWithText("Audífonos").assertIsDisplayed()

        // Selecciona el campo de búsqueda mediante su acción semántica de edición.
        val searchField = composeRule.onNode(hasSetTextAction())
        // Toca el campo para darle el foco.
        searchField.performClick()
        // Escribe en minúsculas para comprobar que el filtro ignora las mayúsculas.
        searchField.performTextInput("mochila")

        // Verifica que la coincidencia sigue visible después de buscar.
        composeRule.onNodeWithText("Mochila").assertIsDisplayed()
        // Verifica que Audífonos ya no existe en el árbol de semántica.
        composeRule.onNodeWithText("Audífonos").assertDoesNotExist()
    }
}