package com.marketlab.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Catalog

@Serializable
data class Detail(val productId: Int)