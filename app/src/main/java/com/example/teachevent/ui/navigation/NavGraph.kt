//creado por CESAR ULISES BARILLA UMANA
package com.example.teachevent.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.teachevent.ui.screens.DetailScreen
import com.example.teachevent.ui.screens.MainAdaptiveScreen
import com.example.teachevent.ui.viewmodel.EventViewModel
import com.example.teachevent.ui.viewmodel.UIState

@Composable
fun TechEventNavGraph(
    navController: NavHostController,
    viewModel: EventViewModel,
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Routes.Catalog,
        modifier = modifier
    ) {

        composable<Routes.Catalog> {
            MainAdaptiveScreen(
                uiState = uiState,
                isDarkMode = isDarkMode,
                onThemeChange = onThemeChange,
                onEventClick = { id ->
                    navController.navigate(Routes.Detail(eventId = id))
                },
                onFavoriteToggle = { event ->
                    viewModel.onFavoriteClick(event)
                },
                onRetry = {
                    viewModel.loadEvents()
                }
            )
        }

        composable<Routes.Detail> { backStackEntry ->
            val detailRoute = backStackEntry.toRoute<Routes.Detail>()
            val selectedEvent = (uiState as? UIState.Success)?.events?.find {
                it.id == detailRoute.eventId
            }

            DetailScreen(
                event = selectedEvent,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}