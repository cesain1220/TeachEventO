//creado por CESAR ULISES BARILLA UMANA
package com.example.teachevent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.teachevent.data.local.AppDatabase
import com.example.teachevent.data.local.ThemeDataStore
import com.example.teachevent.data.remote.RetrofitClient
import com.example.teachevent.data.repository.EventRepositoryImpl
import com.example.teachevent.ui.navigation.TechEventNavGraph
import com.example.teachevent.ui.theme.TeachEventTheme
import com.example.teachevent.ui.viewmodel.EventViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = AppDatabase.getInstance(applicationContext)

        val repository = EventRepositoryImpl(RetrofitClient.apiService, database.eventDao())

        val viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return EventViewModel(repository) as T
                }
                throw IllegalArgumentException("ViewModel desconocido")
            }
        }

        val viewModel = ViewModelProvider(this, viewModelFactory)[EventViewModel::class.java]

        val themeDataStore = ThemeDataStore(this)

        setContent {
            val isDarkMode by themeDataStore.isDarkModeFlow.collectAsState(initial = false)
            TeachEventTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        val navController = rememberNavController()
                        TechEventNavGraph(
                            navController = navController,
                            eventViewModel = viewModel,
                            isDarkMode = isDarkMode,
                            onThemeChange = { darkMode ->
                                lifecycleScope.launch {
                                    themeDataStore.saveThemePreference(darkMode)
                                }
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
