package com.buap.reboot.dominio.modelo

data class Resena(
    val id: Long,
    val idTransaccion: Long,
    val idVendedor: Long,
    val idComprador: Long,
    val calificacion: Int,
    val comentario: String,
    val recomendado: Boolean,
    val fecha: String
)
