package com.marketlab.ui.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marketlab.R
import com.marketlab.data.model.ProductRepository

@Composable
fun CatalogScreen(modifier: Modifier = Modifier, openItem: (Int) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = "Catalog",
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(ProductRepository.products.size) { index ->
                ItemProduct(
                    modifier = modifier,
                    title = ProductRepository.products[index].title,
                    price = ProductRepository.products[index].price,
                    openItem = { (openItem(ProductRepository.products[index].id)) }
                )
            }
        }
    }
}

@Composable
fun ItemProduct(modifier: Modifier, title: String, price: Double, openItem: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(
                onClick = { openItem() }
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("$$price", fontSize = 16.sp)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CatalogScreenPreview(modifier: Modifier = Modifier) {
    CatalogScreen(
        openItem = {}
    )
}
