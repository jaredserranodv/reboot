package com.buap.reboot.dominio.modelo

data class FiltrosBusqueda(
    val texto: String = "",
    val categoria: String? = null,
    val precioMinimo: Double? = null,
    val precioMaximo: Double? = null,
    val ciudad: String? = null,
    val estadoArticulo: EstadoArticulo? = null
)
