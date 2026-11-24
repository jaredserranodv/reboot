package com.buap.reboot.presentacion.misPublicaciones

import com.buap.reboot.dominio.modelo.Articulo

data class EstadoMisPublicaciones(
    val cargando: Boolean = true,
    val publicaciones: List<Articulo> = emptyList(),
    val error: String? = null
)
