package com.example.teachevent.ui.viewmodel


import com.example.techevent.domain.model.Event

sealed interface UIState {
    object Loading : UIState // Estado obligatorio: Pantalla de carga

    data class Success(
        val events: List<Event>,
        val isOfflineMode: Boolean = false // Requerimiento extra para el control de red offline
    ) : UIState // Estado obligatorio: Éxito con datos cargados

    data class Error(val message: String) : UIState // Estado obligatorio: Fallo o error
}