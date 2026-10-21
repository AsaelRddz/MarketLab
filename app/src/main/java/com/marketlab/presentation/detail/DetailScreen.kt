package com.marketlab.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marketlab.presentation.catalog.CatalogViewModel

@Composable
fun DetailScreen(modifier: Modifier = Modifier, productId: Int?, viewModel: CatalogViewModel = hiltViewModel()) {
    val products by viewModel.products.collectAsStateWithLifecycle()

    Column(modifier
        .fillMaxSize()
        .padding(12.dp)) {

        val product = products.find { it.id == productId } ?: return@Column

        Text(
            text = "Detail",
            modifier = modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )

        Spacer(modifier = modifier.height(16.dp))

        Text(
            text = product.title,
            modifier = modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Text(
            text = "$${product.price}",
            modifier = modifier.fillMaxWidth(),
            fontSize = 16.sp
        )

        Text(
            text = product.description,
            modifier = modifier.fillMaxWidth(),
            fontSize = 16.sp
        )



    }
}

@Preview(showSystemUi = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(productId = 1)
}