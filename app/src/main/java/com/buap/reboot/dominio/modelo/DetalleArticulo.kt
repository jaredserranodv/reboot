package com.buap.reboot.dominio.modelo

/** Combina el articulo con los datos publicos del vendedor para la pantalla de detalle. */
data class DetalleArticulo(
    val articulo: Articulo,
    val nombreVendedor: String,
    val ratingVendedor: Float
)
