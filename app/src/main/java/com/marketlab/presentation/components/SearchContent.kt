package com.marketlab.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContent(
    modifier: Modifier = Modifier,
    searchState: SearchBarState,
    textState: TextFieldState
) {
    SearchBar(
        state = searchState,
        inputField = {
            SearchBarDefaults.InputField(
                textFieldState = textState,
                searchBarState = searchState,
                onSearch = {},
                placeholder = { Text("Search") }
            )
        },
        modifier = modifier.fillMaxWidth()
    )
}

