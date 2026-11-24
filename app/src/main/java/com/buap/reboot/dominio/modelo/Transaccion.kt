package com.buap.reboot.dominio.modelo

data class Transaccion(
    val id: Long,
    val idArticulo: Long,
    val idComprador: Long,
    val totalPagado: Double,
    val estado: EstadoTransaccion,
    val fecha: String,
    val direccionEnvio: String
)
