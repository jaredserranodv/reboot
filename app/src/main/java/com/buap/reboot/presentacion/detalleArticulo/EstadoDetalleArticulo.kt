package com.buap.reboot.presentacion.detalleArticulo

import com.buap.reboot.dominio.modelo.DetalleArticulo

data class EstadoDetalleArticulo(
    val cargando: Boolean = true,
    val detalle: DetalleArticulo? = null,
    val error: String? = null
)
