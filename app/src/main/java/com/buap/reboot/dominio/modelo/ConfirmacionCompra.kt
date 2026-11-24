package com.buap.reboot.dominio.modelo

data class ConfirmacionCompra(
    val articulo: Articulo,
    val totalPagado: Double,
    val direccionEnvio: String
)
