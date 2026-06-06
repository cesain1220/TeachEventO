//creado por CESAR ULISES BARILLA UMANA
package com.example.teachevent.ui.navigation

import kotlinx.serialization.Serializable

interface Routes {
    @Serializable
    object Catalog : Routes

    @Serializable
    data class Detail(val eventId: String) : Routes
}