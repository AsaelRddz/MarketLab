package com.marketlab.data.repository

import com.marketlab.data.model.Product

class ProductRepository {
    companion object {
        val products = listOf(
            Product(
                id = 1,
                title = "Product 1",
                description = "Description for Product 1",
                price = 10.99,
                imageUrl = "https://example.com/product1.jpg"
            ),
            Product(
                id = 2,
                title = "Product 2",
                description = "Description for Product 2",
                price = 19.99,
                imageUrl = "https://example.com/product2.jpg"
            ),
            Product(
                id = 3,
                title = "Product 3",
                description = "Description for Product 3",
                price = 5.99,
                imageUrl = "https://example.com/product3.jpg"
            ),
            Product(
                id = 4,
                title = "Product 4",
                description = "Description for Product 4",
                price = 24.99,
                imageUrl = "https://example.com/product4.jpg"
            ),
            Product(
                id = 5,
                title = "Product 5",
                description = "Description for Product 5",
                price = 8.99,
                imageUrl = "https://example.com/product5.jpg"
            )
        )
    }
}
