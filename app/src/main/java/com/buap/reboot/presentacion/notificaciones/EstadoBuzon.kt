package com.buap.reboot.presentacion.notificaciones

import com.buap.reboot.dominio.modelo.Notificacion

data class EstadoBuzon(
    val cargando: Boolean = true,
    val notificaciones: List<Notificacion> = emptyList(),
    val error: String? = null
)
