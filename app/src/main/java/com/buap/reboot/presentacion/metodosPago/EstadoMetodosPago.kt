package com.buap.reboot.presentacion.metodosPago

import com.buap.reboot.dominio.modelo.MetodoPago

data class EstadoMetodosPago(
    val cargando: Boolean = true,
    val metodosPago: List<MetodoPago> = emptyList(),
    val error: String? = null
)
