//creado por CESAR ULISES BARILLA UMANA
package com.example.teachevent.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.teachevent.ui.screens.DetailScreen
import com.example.teachevent.ui.screens.LoginScreen
import com.example.teachevent.ui.screens.MainAdaptiveScreen
import com.example.teachevent.ui.viewmodel.EventViewModel
import com.example.teachevent.ui.viewmodel.LoginUiState
import androidx.compose.runtime.LaunchedEffect
import com.example.teachevent.ui.viewmodel.LoginViewModel
import com.example.teachevent.ui.viewmodel.UIState

@Composable
fun TechEventNavGraph(
    navController: NavHostController,
    eventViewModel: EventViewModel,
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by eventViewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Routes.Login,
        modifier = modifier
    ) {
        composable<Routes.Login> {
            val loginViewModel: LoginViewModel = viewModel()
            val loginUiState by loginViewModel.uiState.collectAsState()

            LaunchedEffect(loginUiState) {
                if (loginUiState is LoginUiState.Success) {
                    navController.navigate(Routes.Catalog) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
                }
            }

            LoginScreen(
                uiState = loginUiState,
                onLoginClick = { email, password ->
                    loginViewModel.login(email, password)
                },
                onForgotPasswordClick = { /* Navegar a recuperar contraseña */ },
                onSignUpClick = { /* Navegar a registro */ }
            )
        }

        composable<Routes.Catalog> {
            MainAdaptiveScreen(
                uiState = uiState,
                isDarkMode = isDarkMode,
                onThemeChange = onThemeChange,
                onEventClick = { id ->
                    navController.navigate(Routes.Detail(eventId = id))
                },
                onFavoriteToggle = { event ->
                    eventViewModel.onFavoriteClick(event)
                },
                onRetry = {
                    eventViewModel.loadEvents()
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
