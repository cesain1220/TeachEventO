package com.example.teachevent.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teachevent.domain.repository.EventRepository
import com.example.techevent.domain.model.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class EventViewModel(
    private val repository: EventRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    // Flujos temporales para retener los eventos crudos y el estado offline
    private val _rawEvents = MutableStateFlow<List<Event>>(emptyList())
    private val _isOffline = MutableStateFlow(false)

    init {
        // 1. Iniciar la observación reactiva de favoritos en Room
        observeUiState()
        // 2. Disparar la petición de datos
        loadEvents()
    }

    private fun observeUiState() {
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                _rawEvents,
                repository.getFavoriteIds(),
                _isOffline
            ) { events, favIds, isOffline ->
                if (events.isEmpty() && _uiState.value is UIState.Loading) {
                    UIState.Loading
                } else {
                    val updatedEvents = events.map { event ->
                        event.copy(isFavorite = favIds.contains(event.id))
                    }
                    UIState.Success(updatedEvents, isOfflineMode = isOffline)
                }
            }.collectLatest { combinedState ->
                _uiState.value = combinedState
            }
        }
    }

    fun loadEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UIState.Loading
            try {
                val (events, isOffline) = repository.getEvents()
                _isOffline.value = isOffline
                _rawEvents.value = events
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.localizedMessage ?: "Error desconocido en el servidor.")
            }
        }
    }

    fun onFavoriteClick(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.toggleFavorite(event, !event.isFavorite)
        }
    }
}