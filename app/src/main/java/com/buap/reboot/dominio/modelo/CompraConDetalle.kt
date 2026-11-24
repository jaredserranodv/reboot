package com.buap.reboot.dominio.modelo

data class CompraConDetalle(
    val transaccion: Transaccion,
    val articulo: Articulo,
    val tieneResena: Boolean
)
