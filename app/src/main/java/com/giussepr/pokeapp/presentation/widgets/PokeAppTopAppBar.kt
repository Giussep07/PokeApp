@file:OptIn(ExperimentalMaterial3Api::class)

package com.giussepr.pokeapp.presentation.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giussepr.pokeapp.R

@Composable
@Preview
fun PokeAppTopAppBarPreview() {
    PokeAppTopAppBar(rememberNavController())
}

@Composable
fun PokeAppTopAppBar(
    navController: NavHostController,
    title: String = stringResource(id = R.string.app_name),
    onListTypeClicked: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
        actions = {
            if (navController.previousBackStackEntry == null) {
                IconButton(onClick = onListTypeClicked) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_grid_view),
                        contentDescription = null
                    )
                }
            }
        }
    )
}
