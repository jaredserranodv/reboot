package com.buap.reboot.dominio.modelo

data class PerfilUsuario(
    val usuario: Usuario,
    val ratingPromedio: Float,
    val totalPublicaciones: Int,
    val totalCompras: Int
)
