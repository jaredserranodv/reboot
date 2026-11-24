package com.buap.reboot.presentacion.pago

import com.buap.reboot.dominio.modelo.Articulo

data class EstadoPago(
    val cargando: Boolean = false,
    val articulo: Articulo? = null,
    val direccionPredeterminada: String? = null,
    val error: String? = null,
    val pagoConfirmado: Boolean = false,
    val idTransaccionConfirmada: Long = 0L
)
