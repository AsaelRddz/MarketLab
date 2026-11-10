package com.marketlab.presentation.features.catalog

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.marketlab.presentation.components.NoticeDialog

@Preview(showSystemUi = true)
@Composable
fun CatalogLoadingPreview() {
    CatalogScreen(CatalogUiState.Loading, onRetry = {}, openItem = {}, onOpenFavorites = {})
}

@Preview(showSystemUi = true)
@Composable
fun CatalogErrorPreview() {
    CatalogScreen(CatalogUiState.Error(errorMessage = "Error"), onRetry = {}, openItem = {}, onOpenFavorites = {})
}

@Preview(showSystemUi = true)
@Composable
fun NoticeDialogPreview() {
    NoticeDialog(message = "Error", onDismiss = {})
}