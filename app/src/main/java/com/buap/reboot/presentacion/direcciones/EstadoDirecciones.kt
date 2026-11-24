package com.buap.reboot.presentacion.direcciones

import com.buap.reboot.dominio.modelo.DireccionEnvio

data class EstadoDirecciones(
    val cargando: Boolean = true,
    val direcciones: List<DireccionEnvio> = emptyList(),
    val error: String? = null
)
