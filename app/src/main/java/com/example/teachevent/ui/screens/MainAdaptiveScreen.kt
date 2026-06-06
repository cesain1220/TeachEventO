//creado por CESAR ULISES BARILLA UMANA
package com.example.teachevent.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.teachevent.MainActivity

import com.example.teachevent.ui.viewmodel.UIState
import com.example.techevent.domain.model.Event
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainAdaptiveScreen(
    uiState: UIState,
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onEventClick: (String) -> Unit,
    onFavoriteToggle: (Event) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = LocalContext.current as MainActivity
    val windowSizeClass = calculateWindowSizeClass(activity)

    var selectedEventIdForTablet by remember { mutableStateOf<String?>(null) }

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Expanded, WindowWidthSizeClass.Medium -> {
            Row(modifier = modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(0.4f).fillMaxHeight()) {
                    CatalogScreen(
                        uiState = uiState,
                        isDarkMode = isDarkMode,
                        onThemeChange = onThemeChange,
                        onEventClick = { id ->
                            selectedEventIdForTablet = id
                        },
                        onFavoriteToggle = onFavoriteToggle,
                        onRetry = onRetry
                    )
                }

                VerticalDivider(thickness = 1.dp, modifier = Modifier.fillMaxHeight())

                Box(modifier = Modifier.weight(0.6f).fillMaxHeight()) {
                    val currentEvent = (uiState as? UIState.Success)?.events?.find {
                        it.id == selectedEventIdForTablet
                    } ?: (uiState as? UIState.Success)?.events?.firstOrNull()

                    DetailScreen(
                        event = currentEvent,
                        onBackClick = {
                            selectedEventIdForTablet = null
                        }
                    )
                }
            }
        }
        else -> {
            CatalogScreen(
                uiState = uiState,
                isDarkMode = isDarkMode,
                onThemeChange = onThemeChange,
                onEventClick = onEventClick,
                onFavoriteToggle = onFavoriteToggle,
                onRetry = onRetry,
                modifier = modifier
            )
        }
    }
}