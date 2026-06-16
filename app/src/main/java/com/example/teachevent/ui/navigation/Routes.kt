//creado por CESAR ULISES BARILLA UMANA
package com.example.teachevent.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object Login : Routes

    @Serializable
    data object Catalog : Routes

    @Serializable
    data class Detail(val eventId: String) : Routes
}
